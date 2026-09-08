package com.example.ui

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val db = ExpenseDatabase.getDatabase(application)
    private val repository = ExpenseRepository(db)

    // Current Month Selection: Format "YYYY-MM" (e.g. "2026-06")
    private val _selectedMonth = MutableStateFlow("")
    val selectedMonth = _selectedMonth.asStateFlow()

    // Search and Filters
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedFilterCategory = MutableStateFlow<String?>(null)
    val selectedFilterCategory = _selectedFilterCategory.asStateFlow()

    private val _sortType = MutableStateFlow("date_desc") // "date_desc", "date_asc", "amount_desc", "amount_asc"
    val sortType = _sortType.asStateFlow()

    // Core Data Streams
    val profile = repository.profileFlow.stateIn(
        viewModelScope, SharingStarted.Eagerly, null
    )

    val budgets = repository.budgetsFlow.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val rawExpenses = repository.expensesFlow.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val members = repository.membersFlow.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val groupTransactions = repository.groupTransactionsFlow.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    init {
        // Initialize active month to current actual month
        val sdf = SimpleDateFormat("yyyy-MM", Locale.US)
        val curMonth = sdf.format(Date())
        _selectedMonth.value = curMonth

        // Ensure database profile exists
        viewModelScope.launch {
            val prof = repository.getProfileDirect()
            if (prof == null) {
                repository.saveProfile(
                    UserProfile(id = 1, name = "My Name", photoUri = null, selectedLanguage = "en", isDarkMode = false)
                )
            }
            // Auto initialize budget for current month
            selectMonth(curMonth)
        }
    }

    // Active Language Selector Helper
    val activeLanguage = profile.map { it?.selectedLanguage ?: "en" }.stateIn(
        viewModelScope, SharingStarted.Eagerly, "en"
    )

    // Active Theme Selector Helper
    val isDarkMode = profile.map { it?.isDarkMode ?: false }.stateIn(
        viewModelScope, SharingStarted.Eagerly, false
    )

    // Reactive list of all distinct months with expenses to display in selector
    val availableMonths = combine(rawExpenses, groupTransactions) { pList, gList ->
        val months = (pList.map { it.date.take(7) } + gList.map { it.date.take(7) }).toMutableSet()
        val current = SimpleDateFormat("yyyy-MM", Locale.US).format(Date())
        months.add(current)
        months.toList().sortedDescending()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf(SimpleDateFormat("yyyy-MM", Locale.US).format(Date())))

    // Active Budget for Selected Month
    val activeMonthBudgetAmount = combine(budgets, _selectedMonth) { list, month ->
        list.find { it.monthKey == month }?.amount ?: 20000.0 // Default budget 20,000 NPR
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 20000.0)

    // Active Monthly Expenses (used strictly for calculated monthly summary/view, such as monthly budget & reports)
    val monthlyExpenses = combine(
        rawExpenses,
        _selectedMonth
    ) { list, month ->
        list.filter { it.date.startsWith(month) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Personal Filtered Expenses (Transaction History - permanently retains all previous months and years)
    val filteredExpenses = combine(
        rawExpenses,
        _searchQuery,
        _selectedFilterCategory,
        _sortType
    ) { list, query, category, sort ->
        var res = list

        if (query.isNotEmpty()) {
            res = res.filter {
                it.title.contains(query, ignoreCase = true) ||
                (it.description?.contains(query, ignoreCase = true) == true) ||
                (it.notes?.contains(query, ignoreCase = true) == true)
            }
        }

        if (category != null) {
            res = res.filter { it.category == category }
        }

        when (sort) {
            "date_asc" -> res.sortedWith(compareBy<PersonalExpense> { it.date }.thenBy { it.time })
            "amount_desc" -> res.sortedByDescending { it.amount }
            "amount_asc" -> res.sortedBy { it.amount }
            else -> res.sortedWith(compareByDescending<PersonalExpense> { it.date }.thenByDescending { it.time }) // date_desc
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Group Filtered Transactions (Transaction History - permanently retains all previous months and years)
    private val _groupSearchQuery = MutableStateFlow("")
    val groupSearchQuery = _groupSearchQuery.asStateFlow()

    val filteredGroupTransactions = combine(
        groupTransactions,
        _groupSearchQuery
    ) { list, query ->
        var res = list
        if (query.isNotEmpty()) {
            res = res.filter {
                it.name.contains(query, ignoreCase = true) ||
                (it.notes?.contains(query, ignoreCase = true) == true)
            }
        }
        res.sortedWith(compareByDescending<GroupTransaction> { it.date }.thenByDescending { it.time })
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Settlement Summary values
    val currentSettlements = combine(members, groupTransactions) { ms, txs ->
        SettlementCalculator.calculateSettlements(ms, txs)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val currentBalancesMap = combine(members, groupTransactions) { ms, txs ->
        SettlementCalculator.calculateBalances(ms, txs)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    // Month Selector logic (with auto-create budget fallback)
    fun selectMonth(monthKey: String) {
        _selectedMonth.value = monthKey
        viewModelScope.launch {
            val budget = repository.getBudgetForMonth(monthKey)
            if (budget == 0.0) {
                val prevKey = getPreviousMonthKey(monthKey)
                val lastBudget = repository.getBudgetForMonth(prevKey)
                val defaultAmt = if (lastBudget > 0.0) lastBudget else 20000.0
                repository.saveBudget(monthKey, defaultAmt)
            }
        }
    }

    private fun getPreviousMonthKey(monthKey: String): String {
        return try {
            val parts = monthKey.split("-")
            var year = parts[0].toInt()
            var month = parts[1].toInt()
            month -= 1
            if (month == 0) {
                month = 12
                year -= 1
            }
            String.format(Locale.US, "%04d-%02d", year, month)
        } catch (e: Exception) {
            "2026-05"
        }
    }

    // Settings actions
    fun setQuery(q: String) {
        _searchQuery.value = q
    }

    fun setGroupQuery(q: String) {
        _groupSearchQuery.value = q
    }

    fun setFilterCategory(cat: String?) {
        _selectedFilterCategory.value = cat
    }

    fun setSortType(sort: String) {
        _sortType.value = sort
    }

    // Mutator Handlers
    fun updateLanguage(lang: String) = viewModelScope.launch {
        profile.value?.let {
            repository.saveProfile(it.copy(selectedLanguage = lang))
        }
    }

    fun updateTheme(dark: Boolean) = viewModelScope.launch {
        profile.value?.let {
            repository.saveProfile(it.copy(isDarkMode = dark))
        }
    }

    fun updateProfile(name: String, photoUri: String?) = viewModelScope.launch {
        profile.value?.let {
            repository.saveProfile(it.copy(name = name, photoUri = photoUri))
        }
    }

    fun updateMonthBudget(amount: Double) = viewModelScope.launch {
        repository.saveBudget(_selectedMonth.value, amount)
    }

    fun addPersonalExpense(title: String, amount: Double, category: String, desc: String?, notes: String?, date: String, time: String) = viewModelScope.launch {
        repository.addPersonalExpense(
            PersonalExpense(
                title = title,
                amount = amount,
                category = category,
                description = desc,
                notes = notes,
                date = date,
                time = time
            )
        )
    }

    fun updatePersonalExpense(expense: PersonalExpense) = viewModelScope.launch {
        repository.updatePersonalExpense(expense)
    }

    fun deletePersonalExpense(expense: PersonalExpense) = viewModelScope.launch {
        repository.deletePersonalExpense(expense)
    }

    fun addMember(name: String, phone: String?, notes: String?, photoUri: String?) = viewModelScope.launch {
        repository.addMember(
            GroupMember(name = name, phone = phone, notes = notes, photoUri = photoUri)
        )
    }

    fun updateMember(member: GroupMember) = viewModelScope.launch {
        repository.updateMember(member)
    }

    fun deleteMember(member: GroupMember) = viewModelScope.launch {
        repository.deleteMember(member)
    }

    fun addGroupTransaction(name: String, amount: Double, paidById: Int, splitWithIds: List<Int>, notes: String?, date: String, time: String) = viewModelScope.launch {
        repository.addGroupTransaction(
            GroupTransaction(
                name = name,
                amount = amount,
                paidById = paidById,
                splitWithIds = splitWithIds.joinToString(","),
                notes = notes,
                date = date,
                time = time
            )
        )
    }

    fun updateGroupTransaction(tx: GroupTransaction) = viewModelScope.launch {
        repository.updateGroupTransaction(tx)
    }

    fun deleteGroupTransaction(tx: GroupTransaction) = viewModelScope.launch {
        repository.deleteGroupTransaction(tx)
    }

    // Direct Database actions
    fun resetAllData(context: Context) = viewModelScope.launch {
        repository.resetAllData()
        Toast.makeText(context, L10n.resetSuccess(activeLanguage.value), Toast.LENGTH_SHORT).show()
    }

    // PDF sharing helper
    private fun sharePdf(context: Context, file: File, title: String) {
        try {
            val authority = "com.aistudio.arthamantralaya.expns.fileprovider"
            val uri = FileProvider.getUriForFile(context, authority, file)
            
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_SUBJECT, title)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            val chooser = Intent.createChooser(intent, title).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(chooser)
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    // Expose PDF Export Trigger Functions
    fun exportIndividualPersonalBill(context: Context, expense: PersonalExpense) {
        val userProf = profile.value ?: UserProfile()
        val lang = activeLanguage.value
        viewModelScope.launch {
            try {
                val file = PdfGenerator.generateIndividualPersonalBill(context, expense, userProf, lang)
                sharePdf(context, file, L10n.individualBill(lang))
            } catch (e: Exception) {
                Toast.makeText(context, "PDF Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun exportMonthlyPersonalBill(context: Context) {
        val userProf = profile.value ?: UserProfile()
        val lang = activeLanguage.value
        val budget = activeMonthBudgetAmount.value
        val monthStr = _selectedMonth.value
        val list = rawExpenses.value.filter { it.date.startsWith(monthStr) }
        viewModelScope.launch {
            try {
                val file = PdfGenerator.generateMonthlyPersonalReport(context, monthStr, budget, list, userProf, lang)
                sharePdf(context, file, L10n.monthlyPersonalBill(lang))
            } catch (e: Exception) {
                Toast.makeText(context, "PDF Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun exportIndividualGroupBill(context: Context, tx: GroupTransaction) {
        val lang = activeLanguage.value
        val membersList = members.value
        viewModelScope.launch {
            try {
                val payerName = membersList.find { it.id == tx.paidById }?.name ?: "Member ${tx.paidById}"
                val splitIds = tx.splitWithIds.split(",").filter { it.isNotEmpty() }.mapNotNull { it.toIntOrNull() }
                val splitNames = splitIds.map { id -> membersList.find { it.id == id }?.name ?: "Member $id" }
                val shareVal = tx.amount / maxOf(1, splitIds.size)
                val shareList = splitIds.map { shareVal }

                val file = PdfGenerator.generateIndividualGroupBill(context, tx, payerName, splitNames, shareList, lang)
                sharePdf(context, file, "Group_Bill_Share")
            } catch (e: Exception) {
                Toast.makeText(context, "PDF Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun exportMonthlyGroupBill(context: Context) {
        val lang = activeLanguage.value
        val monthStr = _selectedMonth.value
        val membersList = members.value
        val txsList = groupTransactions.value.filter { it.date.startsWith(monthStr) }
        val sSummary = currentSettlements.value
        viewModelScope.launch {
            try {
                val file = PdfGenerator.generateMonthlyGroupReport(context, monthStr, membersList, txsList, sSummary, lang)
                sharePdf(context, file, L10n.monthlyGroupBill(lang))
            } catch (e: Exception) {
                Toast.makeText(context, "PDF Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun shareReportFile(context: Context, file: File, title: String, mimeType: String) {
        try {
            val authority = "com.aistudio.arthamantralaya.expns.fileprovider"
            val uri = FileProvider.getUriForFile(context, authority, file)
            
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = mimeType
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_SUBJECT, title)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            val chooser = Intent.createChooser(intent, title).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(chooser)
        } catch (e: Exception) {
            Toast.makeText(context, "Error sharing report: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun exportFilteredGroupReport(
        context: Context,
        groupName: String,
        periodDesc: String,
        filterType: String,
        monthKey: String,
        yearKey: String,
        startDate: String,
        endDate: String,
        format: String // "PDF", "EXCEL", "CSV"
    ) {
        viewModelScope.launch {
            try {
                val ms = members.value
                val allTxs = groupTransactions.value
                
                val d = ReportGenerator.prepareReportData(
                    groupName = groupName,
                    periodDesc = periodDesc,
                    members = ms,
                    allTransactions = allTxs,
                    filterType = filterType,
                    monthKey = monthKey,
                    yearKey = yearKey,
                    startDate = startDate,
                    endDate = endDate
                )
                
                val file = when (format) {
                    "PDF" -> ReportGenerator.exportToPdf(context, d)
                    "EXCEL" -> ReportGenerator.exportToExcel(context, d)
                    else -> ReportGenerator.exportToCsv(context, d)
                }
                
                val mimeType = when (format) {
                    "PDF" -> "application/pdf"
                    "EXCEL" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                    else -> "text/csv"
                }
                
                shareReportFile(context, file, "Group_Report_${periodDesc.replace(" ", "_")}", mimeType)
            } catch (e: Exception) {
                Toast.makeText(context, "Export Error: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }

    // JSON Offline Backup & Restore
    fun exportBackupToJson(context: Context) {
        viewModelScope.launch {
            try {
                val payload = BackupPayload(
                    profile = profile.value ?: UserProfile(),
                    budgets = budgets.value,
                    personalExpenses = rawExpenses.value,
                    groupMembers = members.value,
                    groupTransactions = groupTransactions.value
                )
                val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
                val adapter = moshi.adapter(BackupPayload::class.java)
                val jsonString = adapter.toJson(payload)

                // Try saving directly to Public Downloads folder
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (!downloadsDir.exists()) downloadsDir.mkdirs()
                val backupFile = File(downloadsDir, "artha_mantralaya_backup.json")
                FileOutputStream(backupFile).use { out ->
                    out.write(jsonString.toByteArray())
                }

                Toast.makeText(
                    context, 
                    "${L10n.backupSuccess(activeLanguage.value)}\nPath: ${backupFile.name}", 
                    Toast.LENGTH_LONG
                ).show()

                // Failsafe: Share string code options
                val textShareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, jsonString)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(Intent.createChooser(textShareIntent, "Share JSON Backup Safe"))
            } catch (e: Exception) {
                Toast.makeText(context, "Backup Error: ${e.message}", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    fun restoreBackupFromJsonString(context: Context, jsonStr: String): Boolean {
        return try {
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val adapter = moshi.adapter(BackupPayload::class.java)
            val payload = adapter.fromJson(jsonStr) ?: return false

            viewModelScope.launch {
                // Clear and restored
                db.userProfileDao().insertProfile(payload.profile)
                
                // insert lists
                payload.budgets.forEach { db.monthlyBudgetDao().insertBudget(it) }
                db.personalExpenseDao().deleteAllExpenses()
                payload.personalExpenses.forEach { db.personalExpenseDao().insertExpense(it) }
                
                db.groupMemberDao().deleteAllMembers()
                payload.groupMembers.forEach { db.groupMemberDao().insertMember(it) }
                
                db.groupTransactionDao().deleteAllTransactions()
                payload.groupTransactions.forEach { db.groupTransactionDao().insertTransaction(it) }
            }
            Toast.makeText(context, L10n.restoreSuccess(activeLanguage.value), Toast.LENGTH_SHORT).show()
            true
        } catch (e: Exception) {
            Toast.makeText(context, L10n.restoreError(activeLanguage.value), Toast.LENGTH_SHORT).show()
            e.printStackTrace()
            false
        }
    }
}

class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpenseViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
