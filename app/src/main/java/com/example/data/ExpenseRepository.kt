package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class ExpenseRepository(private val db: ExpenseDatabase) {
    val profileFlow: Flow<UserProfile?> = db.userProfileDao().getProfileFlow()
    val budgetsFlow: Flow<List<MonthlyBudget>> = db.monthlyBudgetDao().getAllBudgetsFlow()
    val expensesFlow: Flow<List<PersonalExpense>> = db.personalExpenseDao().getAllExpensesFlow()
    val membersFlow: Flow<List<GroupMember>> = db.groupMemberDao().getAllMembersFlow()
    val groupTransactionsFlow: Flow<List<GroupTransaction>> = db.groupTransactionDao().getAllTransactionsFlow()

    suspend fun getProfileDirect(): UserProfile? {
        return db.userProfileDao().getProfileDirect()
    }

    suspend fun saveProfile(profile: UserProfile) {
        db.userProfileDao().insertProfile(profile)
    }

    suspend fun saveBudget(monthKey: String, amount: Double) {
        db.monthlyBudgetDao().insertBudget(MonthlyBudget(monthKey = monthKey, amount = amount))
    }

    suspend fun getBudgetForMonth(monthKey: String): Double {
        return db.monthlyBudgetDao().getBudgetForMonth(monthKey)?.amount ?: 0.0
    }

    suspend fun addPersonalExpense(expense: PersonalExpense) {
        db.personalExpenseDao().insertExpense(expense)
    }

    suspend fun updatePersonalExpense(expense: PersonalExpense) {
        db.personalExpenseDao().updateExpense(expense)
    }

    suspend fun deletePersonalExpense(expense: PersonalExpense) {
        db.personalExpenseDao().deleteExpense(expense)
    }

    suspend fun addMember(member: GroupMember) {
        db.groupMemberDao().insertMember(member)
    }

    suspend fun updateMember(member: GroupMember) {
        db.groupMemberDao().updateMember(member)
    }

    suspend fun deleteMember(member: GroupMember) {
        db.groupMemberDao().deleteMember(member)
    }

    suspend fun addGroupTransaction(transaction: GroupTransaction) {
        db.groupTransactionDao().insertTransaction(transaction)
    }

    suspend fun updateGroupTransaction(transaction: GroupTransaction) {
        db.groupTransactionDao().updateTransaction(transaction)
    }

    suspend fun deleteGroupTransaction(transaction: GroupTransaction) {
        db.groupTransactionDao().deleteTransaction(transaction)
    }

    suspend fun resetAllData() {
        db.personalExpenseDao().deleteAllExpenses()
        db.groupTransactionDao().deleteAllTransactions()
        db.groupMemberDao().deleteAllMembers()
        val defaultProfile = UserProfile(id = 1, name = "My Name", photoUri = null, selectedLanguage = "en", isDarkMode = false)
        db.userProfileDao().insertProfile(defaultProfile)
    }
}
