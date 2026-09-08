package com.example.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.example.data.*
import com.example.ui.theme.MyApplicationTheme
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PasswordConfirmDeleteDialog(
    lang: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var passwordInput by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (lang == "ne") "रेकर्ड हटाउने पुष्टि" else "Confirm Secure Deletion",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = if (lang == "ne") "दुर्घटनाबस डेटा हराउनबाट बच्न कृपया सुरक्षा पासवर्ड '100' प्रविष्ट गर्नुहोस्:" 
                           else "To prevent accidental deletion, please enter security password '100' to confirm:"
                )
                OutlinedTextField(
                    value = passwordInput,
                    onValueChange = { passwordInput = it },
                    label = { Text(if (lang == "ne") "पासवर्ड प्रविष्ट गर्नुहोस्" else "Enter Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = passwordInput.isNotEmpty() && passwordInput != "100"
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (passwordInput == "100") {
                        onConfirm()
                        onDismiss()
                    }
                },
                enabled = passwordInput == "100",
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(if (lang == "ne") "मेट्नुहोस्" else "Secure Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(L10n.cancel(lang))
            }
        }
    )
}

@Composable
fun MainAppScreen(viewModel: ExpenseViewModel) {
    val lang by viewModel.activeLanguage.collectAsState()
    val isDark by viewModel.isDarkMode.collectAsState()

    MyApplicationTheme(darkTheme = isDark) {
        val selectedTab = remember { mutableStateOf(0) }
        
        Scaffold(
            bottomBar = {
                NavigationBar(
                    modifier = Modifier
                        .testTag("bottom_nav_bar")
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f),
                            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                        ),
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 0.dp
                ) {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text(L10n.tabHome(lang), fontSize = 10.sp) },
                        selected = selectedTab.value == 0,
                        onClick = { selectedTab.value = 0 },
                        modifier = Modifier.testTag("nav_home_tab")
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.ReceiptLong, contentDescription = "Personal") },
                        label = { Text(L10n.tabPersonal(lang), fontSize = 10.sp) },
                        selected = selectedTab.value == 1,
                        onClick = { selectedTab.value = 1 },
                        modifier = Modifier.testTag("nav_personal_tab")
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Groups, contentDescription = "Group Splitter") },
                        label = { Text(L10n.tabGroup(lang), fontSize = 10.sp) },
                        selected = selectedTab.value == 2,
                        onClick = { selectedTab.value = 2 },
                        modifier = Modifier.testTag("nav_group_tab")
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.PieChart, contentDescription = "Reports") },
                        label = { Text(L10n.tabReports(lang), fontSize = 10.sp) },
                        selected = selectedTab.value == 3,
                        onClick = { selectedTab.value = 3 },
                        modifier = Modifier.testTag("nav_reports_tab")
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                        label = { Text(L10n.tabSettings(lang), fontSize = 10.sp) },
                        selected = selectedTab.value == 4,
                        onClick = { selectedTab.value = 4 },
                        modifier = Modifier.testTag("nav_settings_tab")
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                when (selectedTab.value) {
                    0 -> HomeScreen(viewModel, lang)
                    1 -> PersonalScreen(viewModel, lang)
                    2 -> GroupScreen(viewModel, lang)
                    3 -> ReportsScreen(viewModel, lang)
                    4 -> SettingsScreen(viewModel, lang)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: ExpenseViewModel, lang: String) {
    val profile by viewModel.profile.collectAsState()
    val expenses by viewModel.monthlyExpenses.collectAsState()
    val budgetAmount by viewModel.activeMonthBudgetAmount.collectAsState()
    val selectedMonth by viewModel.selectedMonth.collectAsState()
    val availableMonths by viewModel.availableMonths.collectAsState()
    val settlements by viewModel.currentSettlements.collectAsState()
    val members by viewModel.members.collectAsState()

    val context = LocalContext.current
    var showEditProfile by remember { mutableStateOf(false) }
    var showEditBudget by remember { mutableStateOf(false) }
    var showAddMonth by remember { mutableStateOf(false) }
    var showAddPersonalOnHome by remember { mutableStateOf(false) }
    var showAddGroupOnHome by remember { mutableStateOf(false) }

    val totalSpent = expenses.sumOf { it.amount }
    val remaining = budgetAmount - totalSpent
    val pct = if (budgetAmount > 0) (totalSpent / budgetAmount).toFloat() else 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
        // App header and Month selector
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = L10n.appName(lang),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = L10n.tabHome(lang),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Black
                    )
                }

                // Month selector box
                var expandedMonthMenu by remember { mutableStateOf(false) }
                OutlinedCard(
                    onClick = { expandedMonthMenu = true },
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)),
                    colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = selectedMonth, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }

                    DropdownMenu(
                        expanded = expandedMonthMenu,
                        onDismissRequest = { expandedMonthMenu = false }
                    ) {
                        availableMonths.forEach { m ->
                            DropdownMenuItem(
                                text = { Text(m) },
                                onClick = {
                                    viewModel.selectMonth(m)
                                    expandedMonthMenu = false
                                }
                            )
                        }
                        Divider()
                        DropdownMenuItem(
                            text = { Text(if (lang == "ne") "+ नयाँ महिना" else "+ Create Month") },
                            onClick = {
                                expandedMonthMenu = false
                                showAddMonth = true
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Profile Card
        item {
            profile?.let { prof ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("profile_card"),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Avatar image or default
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.White, CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            MaterialTheme.colorScheme.primary,
                                            MaterialTheme.colorScheme.secondary
                                        )
                                    )
                                )
                                .clickable { showEditProfile = true },
                            contentAlignment = Alignment.Center
                        ) {
                            if (prof.photoUri != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = Uri.parse(prof.photoUri)),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Text(
                                    text = prof.name.take(1).uppercase(Locale.getDefault()),
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (lang == "ne") "नमस्ते, स्वागत छ" else "Welcome back",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = prof.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        IconButton(
                            onClick = { showEditProfile = true },
                            modifier = Modifier.testTag("edit_profile_btn")
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Profile", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }
        }

        // Monthly Budget Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("budget_card"),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Text(
                                text = "${L10n.monthlyBudget(lang)} (${selectedMonth})",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "रु ${String.format(Locale.US, "%.2f", remaining.coerceAtLeast(0.0))}",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            // "Left %" Badge
                            val pctLeft = (100 - (pct * 100).toInt()).coerceIn(0, 100)
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                                        shape = RoundedCornerShape(99.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "$pctLeft% ${if (lang == "ne") "बाँकी" else "Left"}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            IconButton(
                                onClick = { showEditBudget = true },
                                modifier = Modifier.testTag("edit_budget_btn")
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit Budget", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Progress Bar
                    val progressColor = when {
                        pct >= 0.9f -> MaterialTheme.colorScheme.error
                        pct >= 0.7f -> Color(0xFFFFB300) // Amber
                        else -> MaterialTheme.colorScheme.primary // Indigo Primary!
                    }

                    LinearProgressIndicator(
                        progress = { pct.coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = progressColor,
                        trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "${L10n.spent(lang)}: रु ${String.format(Locale.US, "%.2f", totalSpent)}",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "${if (lang == "ne") "कुल बजेट" else "Total Limit"}: रु ${String.format(Locale.US, "%.2f", budgetAmount)}",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Group Settlements Dynamic Summary Checklist
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (lang == "ne") "समूह कारोबार र हिसाब सम्झौता" else "Group Settlements Checklist",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (settlements.isEmpty()) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (lang == "ne") "सबै हिसाब बराबर छ!" else "Everything Settled!",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        settlements.take(3).forEach { set ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .background(
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                                                RoundedCornerShape(12.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Default.Groups,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = "${set.debtorName} → ${set.creditorName}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = if (lang == "ne") "साझा खरिद" else "Split shared bill",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                        )
                                    }
                                }
                                Text(
                                    text = "रु ${String.format(Locale.US, "%.2f", set.amount)}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFEF4444)
                                )
                            }
                            if (set != settlements.take(3).last()) {
                                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f))
                            }
                        }
                    }
                }
            }
        }

        // Quick overview message
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.OfflineBolt, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = if (lang == "ne") "१००% अफलाइन रेकर्ड" else "100% Offline Secure",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (lang == "ne") "सबै वित्तीय डाटाहरू तपाईंको आफ्नै डिभाइसमा सुरक्षित छन्।" else "All transactions details is saved locally on device.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { showAddPersonalOnHome = true },
            modifier = Modifier
                .weight(1f)
                .testTag("add_personal_home_btn")
                .height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = if (lang == "ne") "व्यक्तिगत थप्नुहोस्" else "Add Personal",
                fontSize = 11.sp,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = { showAddGroupOnHome = true },
            modifier = Modifier
                .weight(1f)
                .testTag("add_group_home_btn")
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Icon(Icons.Default.GroupAdd, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = if (lang == "ne") "समूह थप्नुहोस्" else "Add Group Split",
                fontSize = 11.sp,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

    // Modal Edit Profile Dialog
    if (showEditProfile) {
        var tempName by remember { mutableStateOf(profile?.name ?: "") }
        var tempPhotoUri by remember { mutableStateOf(profile?.photoUri) }

        val imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                tempPhotoUri = uri.toString()
            }
        }

        AlertDialog(
            onDismissRequest = { showEditProfile = false },
            title = { Text(L10n.editProfile(lang)) },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Avatar view
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                            .clickable { imagePickerLauncher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        if (tempPhotoUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(model = Uri.parse(tempPhotoUri)),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color.White)
                        }
                    }
                    Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                        Text(L10n.selectGallery(lang))
                    }

                    OutlinedTextField(
                        value = tempName,
                        onValueChange = { tempName = it },
                        label = { Text(L10n.userName(lang)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (tempName.isNotBlank()) {
                            viewModel.updateProfile(tempName, tempPhotoUri)
                            showEditProfile = false
                        }
                    }
                ) {
                    Text(L10n.save(lang))
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditProfile = false }) {
                    Text(L10n.cancel(lang))
                }
            }
        )
    }

    // Modal Edit Budget Dialog
    if (showEditBudget) {
        var tempBudgetAmt by remember { mutableStateOf(budgetAmount.toString()) }

        AlertDialog(
            onDismissRequest = { showEditBudget = false },
            title = { Text(L10n.setBudget(lang)) },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = tempBudgetAmt,
                        onValueChange = { tempBudgetAmt = it },
                        label = { Text(L10n.enterBudgetAmount(lang)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        tempBudgetAmt.toDoubleOrNull()?.let {
                            viewModel.updateMonthBudget(it)
                            showEditBudget = false
                            Toast.makeText(context, L10n.budgetUpdated(lang), Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text(L10n.save(lang))
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditBudget = false }) {
                    Text(L10n.cancel(lang))
                }
            }
        )
    }

    // Modal Create new Month Dialog
    if (showAddMonth) {
        var yearInput by remember { mutableStateOf("2026") }
        var monthInput by remember { mutableStateOf("06") }

        AlertDialog(
            onDismissRequest = { showAddMonth = false },
            title = { Text(if (lang == "ne") "नयाँ महिना थप्नुहोस्" else "Create New Month Budget") },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = yearInput,
                        onValueChange = { yearInput = it },
                        label = { Text("Year (YYYY)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = monthInput,
                        onValueChange = { monthInput = it },
                        label = { Text("Month (MM)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val key = "${yearInput.trim()}-${monthInput.padStart(2, '0').trim()}"
                        viewModel.selectMonth(key)
                        showAddMonth = false
                    }
                ) {
                    Text(if (lang == "ne") "थप्नुहोस्" else "Create Month")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddMonth = false }) {
                    Text(L10n.cancel(lang))
                }
            }
         )
     }

    // Home Add Personal Dialog
    if (showAddPersonalOnHome) {
        AddOrEditPersonalDialog(
            lang = lang,
            onDismiss = { showAddPersonalOnHome = false },
            onSave = { title, amount, category, desc, notes, dateStr, timeStr ->
                viewModel.addPersonalExpense(title, amount, category, desc, notes, dateStr, timeStr)
                showAddPersonalOnHome = false
            }
        )
    }

    // Home Add Group transaction Dialog
    if (showAddGroupOnHome) {
        var txName by remember { mutableStateOf("") }
        var txAmount by remember { mutableStateOf("") }
        var paidByMemberId by remember { mutableStateOf(members.firstOrNull()?.id ?: 1) }
        val splitWithMemberIds = remember { mutableStateListOf<Int>().apply { addAll(members.map { it.id }) } }

        val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val sdfTime = SimpleDateFormat("HH:mm", Locale.US)
        var dateStr by remember { mutableStateOf(sdfDate.format(Date())) }
        var timeStr by remember { mutableStateOf(sdfTime.format(Date())) }

        AlertDialog(
            onDismissRequest = { showAddGroupOnHome = false },
            title = { Text(if (lang == "ne") "समूह खर्च बाँड्नुहोस्" else "Add Group Split Bill") },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (members.isEmpty()) {
                        Text(
                            text = if (lang == "ne") "कृपया पहिले समूह सदस्यहरू थप्नुहोस्!" else "Please add members first in Group Splitter tab!",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        OutlinedTextField(
                            value = txName,
                            onValueChange = { txName = it },
                            label = { Text("Transaction Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = txAmount,
                            onValueChange = { txAmount = it },
                            label = { Text("Amount (NPR)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Text(text = L10n.selectPaidBy(lang), fontWeight = FontWeight.Bold)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        ) {
                            members.forEach { m ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { paidByMemberId = m.id },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(selected = paidByMemberId == m.id, onClick = { paidByMemberId = m.id })
                                    Text(text = m.name)
                                }
                            }
                        }

                        Text(text = L10n.selectSplitWith(lang), fontWeight = FontWeight.Bold)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        ) {
                            members.forEach { m ->
                                val isChecked = splitWithMemberIds.contains(m.id)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            if (isChecked) {
                                                splitWithMemberIds.remove(m.id)
                                            } else {
                                                splitWithMemberIds.add(m.id)
                                            }
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = isChecked,
                                        onCheckedChange = {
                                            if (isChecked) {
                                                splitWithMemberIds.remove(m.id)
                                            } else {
                                                splitWithMemberIds.add(m.id)
                                            }
                                        }
                                    )
                                    Text(text = m.name)
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                if (members.isNotEmpty()) {
                    Button(
                        onClick = {
                            val dAmt = txAmount.toDoubleOrNull()
                            if (txName.isNotBlank() && dAmt != null && dAmt > 0 && splitWithMemberIds.isNotEmpty()) {
                                viewModel.addGroupTransaction(
                                    txName.trim(),
                                    dAmt,
                                    paidByMemberId,
                                    splitWithMemberIds.toList(),
                                    null,
                                    dateStr,
                                    timeStr
                                )
                                showAddGroupOnHome = false
                            } else {
                                Toast.makeText(context, "Valid Name, Amount, and Split list required", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text(L10n.save(lang))
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddGroupOnHome = false }) {
                    Text(L10n.cancel(lang))
                }
            }
        )
    }
}

// Helpers for Category Icons Mapping
fun getCategoryIcon(cat: String): ImageVector {
    return when (cat.lowercase()) {
        "food" -> Icons.Default.Restaurant
        "transport" -> Icons.Default.DirectionsCar
        "education" -> Icons.Default.School
        "rent" -> Icons.Default.Apartment
        "shopping" -> Icons.Default.LocalMall
        "entertainment" -> Icons.Default.Celebration
        "health" -> Icons.Default.MedicalServices
        "utilities" -> Icons.Default.Bolt
        else -> Icons.Default.Category
    }
}

@Composable
fun PersonalScreen(viewModel: ExpenseViewModel, lang: String) {
    val expenses by viewModel.filteredExpenses.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filterCategory by viewModel.selectedFilterCategory.collectAsState()
    val sortType by viewModel.sortType.collectAsState()
    val activeMonth by viewModel.selectedMonth.collectAsState()

    var showAddExpense by remember { mutableStateOf(false) }
    var selectedToEdit by remember { mutableStateOf<PersonalExpense?>(null) }
    var expenseToDelete by remember { mutableStateOf<PersonalExpense?>(null) }
    val context = LocalContext.current

    val categoriesList = listOf("Food", "Transport", "Education", "Rent", "Shopping", "Entertainment", "Health", "Utilities", "Others")

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = L10n.personalExpensesTitle(lang),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Search text field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setQuery(it) },
                placeholder = { Text(L10n.searchPlaceHolder(lang)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_expenses_field"),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setQuery("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Categories horizontal pills
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // "All" Pill
                FilterChip(
                    selected = filterCategory == null,
                    onClick = { viewModel.setFilterCategory(null) },
                    label = { Text(if (lang == "ne") "सबै" else "All") }
                )

                categoriesList.forEach { cat ->
                    FilterChip(
                        selected = filterCategory == cat,
                        onClick = { viewModel.setFilterCategory(cat) },
                        label = { Text(L10n.translateCategory(cat, lang)) },
                        leadingIcon = {
                            Icon(
                                imageVector = getCategoryIcon(cat),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Sort Toggle Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${if (lang == "ne") "कारोबारहरू" else "History"} (${expenses.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    onClick = {
                        val nextSort = when (sortType) {
                            "date_desc" -> "amount_desc"
                            "amount_desc" -> "amount_asc"
                            "amount_asc" -> "date_asc"
                            else -> "date_desc"
                        }
                        viewModel.setSortType(nextSort)
                    }
                ) {
                    val sortIcon = when (sortType) {
                        "amount_desc" -> Icons.Default.TrendingDown
                        "amount_asc" -> Icons.Default.TrendingUp
                        "date_asc" -> Icons.Default.ArrowUpward
                        else -> Icons.Default.ArrowDownward
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(sortIcon, contentDescription = "Sort style")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = when (sortType) {
                                "amount_desc" -> "रु ↓"
                                "amount_asc" -> "रु ↑"
                                "date_asc" -> "Date ↑"
                                else -> "Date ↓"
                            },
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Expense List
            if (expenses.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Inbox,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = L10n.emptyExpenses(lang),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(expenses) { exp ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("expense_item_card_${exp.id}")
                                .clickable { selectedToEdit = exp },
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Category Icon
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = getCategoryIcon(exp.category),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = exp.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(
                                            text = L10n.translateCategory(exp.category, lang),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                        Text(
                                            text = "•",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.LightGray
                                        )
                                        Text(
                                            text = exp.date,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "रु ${String.format(Locale.US, "%.2f", exp.amount)}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    // Generate Bill Indicator
                                    TextButton(
                                        onClick = { viewModel.exportIndividualPersonalBill(context, exp) },
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.PictureAsPdf, contentDescription = null, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(text = L10n.generateBill(lang), fontSize = 10.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Floating Action Button to Add Expense
        LargeFloatingActionButton(
            onClick = { showAddExpense = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .testTag("add_expense_fab")
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Expense")
        }
    }

    // Modal Sheet or dialog for Adding Personal Expense
    if (showAddExpense) {
        AddOrEditPersonalDialog(
            lang = lang,
            onDismiss = { showAddExpense = false },
            onSave = { title, amount, category, desc, notes, dateStr, timeStr ->
                viewModel.addPersonalExpense(title, amount, category, desc, notes, dateStr, timeStr)
                showAddExpense = false
            }
        )
    }

    // Dialog for Editing Personal Expense
    selectedToEdit?.let { exp ->
        AddOrEditPersonalDialog(
            lang = lang,
            expenseToEdit = exp,
            onDismiss = { selectedToEdit = null },
            onSave = { title, amount, category, desc, notes, dateStr, timeStr ->
                viewModel.updatePersonalExpense(
                    exp.copy(
                        title = title,
                        amount = amount,
                        category = category,
                        description = desc,
                        notes = notes,
                        date = dateStr,
                        time = timeStr
                    )
                )
                selectedToEdit = null
            },
            onDelete = {
                expenseToDelete = exp
                selectedToEdit = null
            }
        )
    }

    expenseToDelete?.let { exp ->
        PasswordConfirmDeleteDialog(
            lang = lang,
            onDismiss = { expenseToDelete = null },
            onConfirm = {
                viewModel.deletePersonalExpense(exp)
                expenseToDelete = null
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrEditPersonalDialog(
    lang: String,
    expenseToEdit: PersonalExpense? = null,
    onDismiss: () -> Unit,
    onSave: (String, Double, String, String?, String?, String, String) -> Unit,
    onDelete: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf(expenseToEdit?.title ?: "") }
    var amount by remember { mutableStateOf(expenseToEdit?.amount?.toString() ?: "") }
    var category by remember { mutableStateOf(expenseToEdit?.category ?: "Food") }
    var desc by remember { mutableStateOf(expenseToEdit?.description ?: "") }
    var notes by remember { mutableStateOf(expenseToEdit?.notes ?: "") }

    val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val sdfTime = SimpleDateFormat("HH:mm", Locale.US)
    var dateStr by remember { mutableStateOf(expenseToEdit?.date ?: sdfDate.format(Date())) }
    var timeStr by remember { mutableStateOf(expenseToEdit?.time ?: sdfTime.format(Date())) }

    val categoriesList = listOf("Food", "Transport", "Education", "Rent", "Shopping", "Entertainment", "Health", "Utilities", "Others")

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (expenseToEdit == null) L10n.addExpense(lang) else L10n.editExpense(lang),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    if (expenseToEdit != null && onDelete != null) {
                        IconButton(onClick = onDelete) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                        }
                    }
                }

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(L10n.title(lang)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text(L10n.amount(lang) + " (NPR रु)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Category Selection Spinner/Row
                Text(text = L10n.category(lang), fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categoriesList.forEach { cat ->
                        FilterChip(
                            selected = category == cat,
                            onClick = { category = cat },
                            label = { Text(L10n.translateCategory(cat, lang)) }
                        )
                    }
                }

                // Date Picker trigger button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "${L10n.date(lang)}: $dateStr", fontWeight = FontWeight.Medium)
                    Button(
                        onClick = {
                            val calendar = Calendar.getInstance()
                            if (expenseToEdit != null) {
                                try {
                                    val d = sdfDate.parse(expenseToEdit.date)
                                    calendar.time = d!!
                                } catch (e: Exception) {}
                            }
                            DatePickerDialog(
                                context,
                                { _, y, m, d ->
                                    dateStr = String.format(Locale.US, "%04d-%02d-%02d", y, m + 1, d)
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            ).show()
                        }
                    ) {
                        Text(if (lang == "ne") "मिति छान्नुहोस्" else "Choose Date")
                    }
                }

                // Time picker trigger button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "${L10n.time(lang)}: $timeStr", fontWeight = FontWeight.Medium)
                    Button(
                        onClick = {
                            val calendar = Calendar.getInstance()
                            TimePickerDialog(
                                context,
                                { _, h, min ->
                                    timeStr = String.format(Locale.US, "%02d:%02d", h, min)
                                },
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                true
                            ).show()
                        }
                    ) {
                        Text(if (lang == "ne") "समय छान्नुहोस्" else "Choose Time")
                    }
                }

                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text(L10n.description(lang)) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text(L10n.notes(lang)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(L10n.cancel(lang))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val dAmount = amount.toDoubleOrNull()
                            if (title.isNotBlank() && dAmount != null && dAmount > 0) {
                                onSave(
                                    title.trim(),
                                    dAmount,
                                    category,
                                    desc.trim().ifEmpty { null },
                                    notes.trim().ifEmpty { null },
                                    dateStr,
                                    timeStr
                                )
                            } else {
                                Toast.makeText(context, "Please fill valid inputs", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text(L10n.save(lang))
                    }
                }
            }
        }
    }
}

@Composable
fun GroupScreen(viewModel: ExpenseViewModel, lang: String) {
    val members by viewModel.members.collectAsState()
    val transactions by viewModel.filteredGroupTransactions.collectAsState()
    val settlements by viewModel.currentSettlements.collectAsState()
    val balances by viewModel.currentBalancesMap.collectAsState()
    val groupSearchKey by viewModel.groupSearchQuery.collectAsState()
    val allGroupTransactions by viewModel.groupTransactions.collectAsState()

    var activeSubTab by remember { mutableStateOf(0) } // 0: Dashboard, 1: Members, 2: Transactions
    var showAddMember by remember { mutableStateOf(false) }
    var showAddTransaction by remember { mutableStateOf(false) }

    var selectedMemberProfile by remember { mutableStateOf<GroupMember?>(null) }
    var selectedTxToEdit by remember { mutableStateOf<GroupTransaction?>(null) }
    var memberToDelete by remember { mutableStateOf<GroupMember?>(null) }
    var transactionToDelete by remember { mutableStateOf<GroupTransaction?>(null) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = L10n.groupDashboard(lang),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Tab Selector Row
        TabRow(selectedTabIndex = activeSubTab) {
            Tab(selected = activeSubTab == 0, onClick = { activeSubTab = 0 }) {
                Box(modifier = Modifier.padding(12.dp)) {
                    Text(if (lang == "ne") "ड्यासबोर्ड" else "Summary", fontWeight = FontWeight.Bold)
                }
            }
            Tab(selected = activeSubTab == 1, onClick = { activeSubTab = 1 }) {
                Box(modifier = Modifier.padding(12.dp)) {
                    Text(if (lang == "ne") "सदस्यहरू (${members.size})" else "Members (${members.size})", fontWeight = FontWeight.Bold)
                }
            }
            Tab(selected = activeSubTab == 2, onClick = { activeSubTab = 2 }) {
                Box(modifier = Modifier.padding(12.dp)) {
                    Text(if (lang == "ne") "लेनदेन" else "Transactions", fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (activeSubTab) {
            0 -> {
                // Group Dashboard calculations
                val totalSpent = transactions.sumOf { it.amount }
                
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Headline Statistics
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = L10n.totalGroupExpenses(lang),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "रु ${String.format(Locale.US, "%.2f", totalSpent)}",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = L10n.totalMembers(lang),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "${members.size}",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }

                    // optimal settlement transactions list
                    item {
                        Text(
                            text = L10n.settlements(lang),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    if (settlements.isEmpty()) {
                        item {
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (lang == "ne") "सबै हिसाब बराबर छ! कुनै भी सदस्यको हिसाब तिर्न बाँकी छैन।" else "All transactions settled! No active debts.",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    } else {
                        items(settlements) { set ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.ArrowCircleRight, contentDescription = null, tint = Color.Red)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = if (lang == "ne") "${set.debtorName} ले ${set.creditorName} लाई बुझाउनुपर्ने" else "${set.debtorName} owes ${set.creditorName}",
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = if (lang == "ne") "हिसाब मिलाउन रु ${String.format(Locale.US, "%.2f", set.amount)} भुक्तान गर्नुहोस्" else "Settle payment of रु ${String.format(Locale.US, "%.2f", set.amount)}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                    }
                                    Text(
                                        text = "रु ${String.format(Locale.US, "%.2f", set.amount)}",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Black,
                                        color = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }

            1 -> {
                // MEMBERS SUB-TAB
                Box(modifier = Modifier.weight(1f)) {
                    if (members.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.PersonSearch, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = L10n.noMembers(lang), color = Color.Gray)
                            }
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(members) { m ->
                                val bal = balances[m.id] ?: 0.0
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("member_card_${m.id}")
                                        .clickable { selectedMemberProfile = m },
                                    shape = RoundedCornerShape(20.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // Photo or Default Ava
                                        Box(
                                            modifier = Modifier
                                                .size(48.dp)
                                                .clip(CircleShape)
                                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (m.photoUri != null) {
                                                Image(
                                                    painter = rememberAsyncImagePainter(model = Uri.parse(m.photoUri)),
                                                    contentDescription = null,
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentScale = ContentScale.Crop
                                                )
                                            } else {
                                                Text(
                                                    text = m.name.take(1).uppercase(Locale.getDefault()),
                                                    fontWeight = FontWeight.Black,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.width(12.dp))

                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(text = m.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                            if (!m.phone.isNullOrBlank()) {
                                                Text(text = m.phone, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                            }
                                        }

                                        // Net balance coloring
                                        val balText = if (bal >= 0) "+ रु ${String.format(Locale.US, "%.2f", bal)}" else "- रु ${String.format(Locale.US, "%.2f", -bal)}"
                                        val balColor = if (bal >= 0) Color(0xFF107C41) else Color.Red

                                        Text(
                                            text = balText,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Black,
                                            color = balColor
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Add Button FAB for Members space
                    SmallFloatingActionButton(
                        onClick = { showAddMember = true },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .testTag("add_member_fab")
                    ) {
                        Icon(Icons.Default.PersonAdd, contentDescription = "Add Member")
                    }
                }
            }

            2 -> {
                // TRANSACTIONS SUB-TAB
                Box(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // search
                        OutlinedTextField(
                            value = groupSearchKey,
                            onValueChange = { viewModel.setGroupQuery(it) },
                            placeholder = { Text(if (lang == "ne") "खोज्नुहोस्..." else "Search bills") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        if (transactions.isEmpty()) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(text = if (lang == "ne") "कुनै समूह लेनदेनहरू छैनन्!" else "No group splits transactions recorded yet!")
                            }
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(transactions) { tx ->
                                    val paidByMember = members.find { it.id == tx.paidById }?.name ?: "Member ${tx.paidById}"
                                    val splits = tx.splitWithIds.split(",").filter { it.isNotEmpty() }
                                    val splittedMemberNames = splits.mapNotNull { mIdStr -> mIdStr.toIntOrNull()?.let { mId -> members.find { it.id == mId }?.name } }.joinToString(", ")

                                    OutlinedCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { selectedTxToEdit = tx },
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Text(text = tx.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                                    Text(
                                                        text = "${if (lang == "ne") "तिर्ने व्यक्ति:" else "Paid by:"} $paidByMember",
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                                    )
                                                    Text(
                                                        text = "${if (lang == "ne") "बाँडफाँड:" else "Split with:"} $splittedMemberNames",
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                                    )
                                                }

                                                Column(horizontalAlignment = Alignment.End) {
                                                    Text(
                                                        text = "रु ${String.format(Locale.US, "%.2f", tx.amount)}",
                                                        style = MaterialTheme.typography.titleMedium,
                                                        fontWeight = FontWeight.Black,
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                    IconButton(onClick = { viewModel.exportIndividualGroupBill(context, tx) }) {
                                                        Icon(Icons.Default.PictureAsPdf, contentDescription = "Export split pdf", tint = MaterialTheme.colorScheme.secondary)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Add FAB for split
                    SmallFloatingActionButton(
                        onClick = {
                            if (members.isEmpty()) {
                                Toast.makeText(context, "Please add group members first", Toast.LENGTH_SHORT).show()
                            } else {
                                showAddTransaction = true
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .testTag("add_group_tx_fab")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add Split Group bill")
                    }
                }
            }
        }
    }

    // Modal Member detailed profile page
    selectedMemberProfile?.let { m ->
        val payerTransfers = settlements.filter { it.creditorId == m.id } // receivable
        val debtorTransfers = settlements.filter { it.debtorId == m.id } // payable
        val mBalance = balances[m.id] ?: 0.0

        Dialog(onDismissRequest = { selectedMemberProfile = null }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = L10n.memberProfile(lang), style = MaterialTheme.typography.titleMedium)
                        IconButton(onClick = {
                            memberToDelete = m
                            selectedMemberProfile = null
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete member", tint = Color.Red)
                        }
                    }

                    // Profile Photo
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .align(Alignment.CenterHorizontally),
                        contentAlignment = Alignment.Center
                    ) {
                        if (m.photoUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(model = Uri.parse(m.photoUri)),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(Icons.Default.Person, contentDescription = null, tint = Color.White)
                        }
                    }

                    Text(
                        text = m.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (!m.phone.isNullOrBlank()) {
                        Text(text = "${L10n.memberPhone(lang)}: ${m.phone}", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Net balance row
                    Text(text = L10n.netBalance(lang), fontWeight = FontWeight.Bold)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (mBalance >= 0) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (mBalance >= 0) "+ रु ${String.format(Locale.US, "%.2f", mBalance)}" else "- रु ${String.format(Locale.US, "%.2f", -mBalance)}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                color = if (mBalance >= 0) Color(0xFF1B5E20) else Color(0xFFB71C1C)
                            )
                        }
                    }

                    // Individual settlements map
                    Text(text = L10n.settlements(lang), fontWeight = FontWeight.Bold)

                    val otherMembers = members.filter { it.id != m.id }
                    if (otherMembers.isEmpty()) {
                        Text(
                            text = if (lang == "ne") "समूहमा अन्य कुनै सदस्य छैनन्।" else "No other members in this group.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        otherMembers.forEach { other ->
                            var otherOwesM = 0.0
                            var mOwesOther = 0.0

                            allGroupTransactions.forEach { tx ->
                                val splitIds = tx.splitWithIds.split(",")
                                    .filter { it.isNotEmpty() }
                                    .mapNotNull { it.toIntOrNull() }
                                    .filter { id -> members.any { mm -> mm.id == id } }
                                    
                                if (splitIds.isNotEmpty()) {
                                    val share = tx.amount / splitIds.size
                                    if (splitIds.contains(other.id) && tx.paidById == m.id) {
                                        otherOwesM += share
                                    }
                                    if (splitIds.contains(m.id) && tx.paidById == other.id) {
                                        mOwesOther += share
                                    }
                                }
                            }
                            val net = otherOwesM - mOwesOther

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = other.name.take(1).uppercase(Locale.getDefault()),
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontSize = 10.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = other.name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    if (net > 0.01) {
                                        Text(
                                            text = if (lang == "ne") "${other.name} ले प्रविष्टि कर्तालाई: रु ${String.format(Locale.US, "%.2f", net)}" else "${other.name} owes ${m.name}: रु ${String.format(Locale.US, "%.2f", net)}",
                                            color = Color(0xFF1B5E20),
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    } else if (net < -0.01) {
                                        Text(
                                            text = if (lang == "ne") "${m.name} ले ${other.name} लाई: रु ${String.format(Locale.US, "%.2f", -net)}" else "${m.name} owes ${other.name}: रु ${String.format(Locale.US, "%.2f", -net)}",
                                            color = Color(0xFFB71C1C),
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    } else {
                                        Text(
                                            text = if (lang == "ne") "${other.name} सँग हिसाब: रु 0.00" else "Owes ${m.name}: 0",
                                            color = Color.Gray,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Button(
                        onClick = { selectedMemberProfile = null },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (lang == "ne") "बन्द गर्नुहोस्" else "Close")
                    }
                }
            }
        }
    }

    // Modal Edit group split transaction
    selectedTxToEdit?.let { gtx ->
        Dialog(onDismissRequest = { selectedTxToEdit = null }) {
            Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.padding(16.dp)) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = if (lang == "ne") "कारोबार सम्पादन गर्नुहोस्" else "Edit Group Transaction", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Divider()
                    Text(text = "Name: ${gtx.name}", fontWeight = FontWeight.Bold)
                    Text(text = "Amount: रु ${String.format(Locale.US, "%.2f", gtx.amount)}")
                    Text(text = "Date: ${gtx.date} ${gtx.time}")

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            onClick = {
                                transactionToDelete = gtx
                                selectedTxToEdit = null
                            }
                        ) {
                            Text(L10n.delete(lang))
                        }
                        TextButton(onClick = { selectedTxToEdit = null }) {
                            Text(L10n.cancel(lang))
                        }
                    }
                }
            }
        }
    }

    // Modal Dialog to Add Member
    if (showAddMember) {
        var mName by remember { mutableStateOf("") }
        var mPhone by remember { mutableStateOf("") }
        var mNotes by remember { mutableStateOf("") }
        var mPhotoUri by remember { mutableStateOf<String?>(null) }

        val galleryPicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                mPhotoUri = uri.toString()
            }
        }

        AlertDialog(
            onDismissRequest = { showAddMember = false },
            title = { Text(L10n.addMember(lang)) },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                            .clickable { galleryPicker.launch("image/*") }
                            .align(Alignment.CenterHorizontally),
                        contentAlignment = Alignment.Center
                    ) {
                        if (mPhotoUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(model = Uri.parse(mPhotoUri)),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color.White)
                        }
                    }

                    OutlinedTextField(
                        value = mName,
                        onValueChange = { mName = it },
                        label = { Text(L10n.memberName(lang)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = mPhone,
                        onValueChange = { mPhone = it },
                        label = { Text(L10n.memberPhone(lang)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (mName.isNotBlank()) {
                            viewModel.addMember(mName.trim(), mPhone.trim().ifEmpty { null }, mNotes.trim().ifEmpty { null }, mPhotoUri)
                            showAddMember = false
                        }
                    }
                ) {
                    Text(L10n.save(lang))
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddMember = false }) {
                    Text(L10n.cancel(lang))
                }
            }
        )
    }

    // Modal Dialog to Add Group Transaction (Split)
    if (showAddTransaction) {
        var txName by remember { mutableStateOf("") }
        var txAmount by remember { mutableStateOf("") }
        var paidByMemberId by remember { mutableStateOf(members.firstOrNull()?.id ?: 1) }
        val splitWithMemberIds = remember { mutableStateListOf<Int>().apply { addAll(members.map { it.id }) } }

        val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val sdfTime = SimpleDateFormat("HH:mm", Locale.US)
        var dateStr by remember { mutableStateOf(sdfDate.format(Date())) }
        var timeStr by remember { mutableStateOf(sdfTime.format(Date())) }

        AlertDialog(
            onDismissRequest = { showAddTransaction = false },
            title = { Text(if (lang == "ne") "समूह खर्च बाँड्नुहोस्" else "Add Group Split Bill") },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = txName,
                        onValueChange = { txName = it },
                        label = { Text("Transaction Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = txAmount,
                        onValueChange = { txAmount = it },
                        label = { Text("Amount (NPR)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Paid By List spinner selector
                    Text(text = L10n.selectPaidBy(lang), fontWeight = FontWeight.Bold)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        members.forEach { m ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { paidByMemberId = m.id },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(selected = paidByMemberId == m.id, onClick = { paidByMemberId = m.id })
                                Text(text = m.name)
                            }
                        }
                    }

                    // Split With checkbox list selector
                    Text(text = L10n.selectSplitWith(lang), fontWeight = FontWeight.Bold)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        members.forEach { m ->
                            val isChecked = splitWithMemberIds.contains(m.id)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (isChecked) {
                                            splitWithMemberIds.remove(m.id)
                                        } else {
                                            splitWithMemberIds.add(m.id)
                                        }
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = {
                                        if (isChecked) {
                                            splitWithMemberIds.remove(m.id)
                                        } else {
                                            splitWithMemberIds.add(m.id)
                                        }
                                    }
                                )
                                Text(text = m.name)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val dAmt = txAmount.toDoubleOrNull()
                        if (txName.isNotBlank() && dAmt != null && dAmt > 0 && splitWithMemberIds.isNotEmpty()) {
                            viewModel.addGroupTransaction(
                                txName.trim(),
                                dAmt,
                                paidByMemberId,
                                splitWithMemberIds.toList(),
                                null,
                                dateStr,
                                timeStr
                            )
                            showAddTransaction = false
                        } else {
                            Toast.makeText(context, "Valid Name, Amount, and Split list required", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text(L10n.save(lang))
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddTransaction = false }) {
                    Text(L10n.cancel(lang))
                }
            }
        )
    }

    memberToDelete?.let { m ->
        PasswordConfirmDeleteDialog(
            lang = lang,
            onDismiss = { memberToDelete = null },
            onConfirm = {
                viewModel.deleteMember(m)
                memberToDelete = null
            }
        )
    }

    transactionToDelete?.let { gtx ->
        PasswordConfirmDeleteDialog(
            lang = lang,
            onDismiss = { transactionToDelete = null },
            onConfirm = {
                viewModel.deleteGroupTransaction(gtx)
                transactionToDelete = null
            }
        )
    }
}

@Composable
fun ReportsScreen(viewModel: ExpenseViewModel, lang: String) {
    val expenses by viewModel.monthlyExpenses.collectAsState()
    val activeMonth by viewModel.selectedMonth.collectAsState()
    val context = LocalContext.current
    var showGroupReportDialog by remember { mutableStateOf(false) }

    val categories = listOf("Food", "Transport", "Education", "Rent", "Shopping", "Entertainment", "Health", "Utilities", "Others")
    val totalExpenses = expenses.sumOf { it.amount }

    val categoryBreakdown = categories.associateWith { cat ->
        expenses.filter { it.category.lowercase() == cat.lowercase() }.sumOf { it.amount }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = L10n.reportsTitle(lang),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Category Wise breakdown chart section
        item {
            Text(
                text = L10n.categoryWiseTitle(lang),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (totalExpenses == 0.0) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = if (lang == "ne") "यस महिनाको कुनै विवरण छैन।" else "No transactions for this month.", color = Color.Gray)
                        }
                    } else {
                        // Horizontal custom bars
                        categoryBreakdown.forEach { (cat, amt) ->
                            if (amt > 0) {
                                val ratio = (amt / totalExpenses).toFloat()
                                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = getCategoryIcon(cat),
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp),
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(text = L10n.translateCategory(cat, lang), fontWeight = FontWeight.Bold)
                                        }
                                        Text(text = "रु ${String.format(Locale.US, "%.2f", amt)} (${(ratio * 100).toInt()}%)")
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    LinearProgressIndicator(
                                        progress = { ratio },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(8.dp)
                                            .clip(CircleShape),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Action PDF Generation Section
        item {
            Text(
                text = if (lang == "ne") "मासिक रसिद र रिपोर्ट डाउनलोड" else "Download Offline PDF Reports",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Personal Report Button
                Button(
                    onClick = { viewModel.exportMonthlyPersonalBill(context) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Download, contentDescription = null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = L10n.exportReport(lang))
                }

                // Group Report Button
                Button(
                    onClick = { showGroupReportDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.InsertChart, contentDescription = null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = if (lang == "ne") "समूह रिपोर्ट डाउनलोड (विवरण प्रविष्टि)" else "Download Group Custom Report")
                }
            }
        }
    }

    if (showGroupReportDialog) {
        GroupReportExportDialog(
            lang = lang,
            onDismiss = { showGroupReportDialog = false },
            onConfirm = { groupName, periodDesc, filterType, monthKey, yearKey, startDate, endDate, format ->
                viewModel.exportFilteredGroupReport(
                    context = context,
                    groupName = groupName,
                    periodDesc = periodDesc,
                    filterType = filterType,
                    monthKey = monthKey,
                    yearKey = yearKey,
                    startDate = startDate,
                    endDate = endDate,
                    format = format
                )
                showGroupReportDialog = false
            }
        )
    }
}

@Composable
fun GroupReportExportDialog(
    lang: String,
    onDismiss: () -> Unit,
    onConfirm: (
        groupName: String,
        periodDesc: String,
        filterType: String,
        monthKey: String,
        yearKey: String,
        startDate: String,
        endDate: String,
        format: String
    ) -> Unit
) {
    var groupName by remember { mutableStateOf("Artha Splitter Group") }
    
    // Filter type options: "all", "month", "year", "custom"
    var filterType by remember { mutableStateOf("all") }
    
    var monthKey by remember { mutableStateOf("2026-06") }
    var yearKey by remember { mutableStateOf("2026") }
    var startDate by remember { mutableStateOf("2026-01-01") }
    var endDate by remember { mutableStateOf("2026-12-31") }
    
    // Format options: "PDF", "EXCEL", "CSV"
    var exportFormat by remember { mutableStateOf("PDF") }

    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header
                Text(
                    text = if (lang == "ne") "समूह विवरण डाउनलोड र निर्यात" else "Download Group Split Report",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                // Developer credit (CRITICAL: shri bibash lamichhane big and bold)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = if (lang == "ne") "विकासकर्ता:" else "Developer:",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Shri Bibash Lamichhane",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                // Group Name Input
                OutlinedTextField(
                    value = groupName,
                    onValueChange = { groupName = it },
                    label = { Text(if (lang == "ne") "समूहको नाम" else "Group Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // Filter options header
                Text(
                    text = if (lang == "ne") "फिल्टर विकल्पहरू" else "Filter Period Choices",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                // Radios or chips for filter options
                listOf(
                    "all" to (if (lang == "ne") "सबै समयको विवरण" else "All-Time Transactions"),
                    "month" to (if (lang == "ne") "विशेष महिना" else "Specific Month (YYYY-MM)"),
                    "year" to (if (lang == "ne") "विशेष वर्ष" else "Specific Year (YYYY)"),
                    "custom" to (if (lang == "ne") "तपाईंको अनुकूल मिति" else "Custom Date Range")
                ).forEach { (type, label) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { filterType = type }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (filterType == type),
                            onClick = { filterType = type }
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = label, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                // Sub-inputs depending on filter type
                when (filterType) {
                    "month" -> {
                        OutlinedTextField(
                            value = monthKey,
                            onValueChange = { monthKey = it },
                            label = { Text(if (lang == "ne") "महिना प्रविष्टि (प्रारूप: YYYY-MM)" else "Enter Month (Format: YYYY-MM)") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                    "year" -> {
                        OutlinedTextField(
                            value = yearKey,
                            onValueChange = { yearKey = it },
                            label = { Text(if (lang == "ne") "वर्ष प्रविष्टि (प्रारूप: YYYY)" else "Enter Year (Format: YYYY)") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                    "custom" -> {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = startDate,
                                onValueChange = { startDate = it },
                                label = { Text("Start (YYYY-MM-DD)") },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            OutlinedTextField(
                                value = endDate,
                                onValueChange = { endDate = it },
                                label = { Text("End (YYYY-MM-DD)") },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // EXPORT FORMAT HEADER
                Text(
                    text = if (lang == "ne") "निर्यात ढाँचा (Format)" else "Choose Export Format",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("PDF", "EXCEL", "CSV").forEach { fmt ->
                        val isSelected = (exportFormat == fmt)
                        Button(
                            onClick = { exportFormat = fmt },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        ) {
                            Text(text = fmt, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (lang == "ne") "रद्द गर्नुहोस्" else "Cancel")
                    }

                    Button(
                        onClick = {
                            val periodDesc = when (filterType) {
                                "month" -> monthKey
                                "year" -> "Year $yearKey"
                                "custom" -> "$startDate to $endDate"
                                else -> "All-Time"
                            }
                            onConfirm(groupName, periodDesc, filterType, monthKey, yearKey, startDate, endDate, exportFormat)
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (lang == "ne") "डाउनलोड" else "Download")
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(viewModel: ExpenseViewModel, lang: String) {
    val isDark by viewModel.isDarkMode.collectAsState()
    val context = LocalContext.current

    var showRestoreDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    var showResetConfirmPassword by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = L10n.tabSettings(lang),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Language toggle
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = L10n.changeLanguage(lang), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ElevatedButton(
                            onClick = { viewModel.updateLanguage("en") },
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = if (lang == "en") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                            ),
                            modifier = Modifier.weight(1f).testTag("lang_en_btn")
                        ) {
                            Text(text = "English", color = if (lang == "en") Color.White else Color.Unspecified)
                        }

                        ElevatedButton(
                            onClick = { viewModel.updateLanguage("ne") },
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = if (lang == "ne") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                            ),
                            modifier = Modifier.weight(1f).testTag("lang_ne_btn")
                        ) {
                            Text(text = "नेपाली (Nepali)", color = if (lang == "ne") Color.White else Color.Unspecified)
                        }
                    }
                }
            }
        }

        // Theme dark/light switch
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = L10n.changeTheme(lang), fontWeight = FontWeight.Bold)
                        Text(
                            text = if (isDark) "Black background enabled" else "Default Theme",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                    Switch(
                        checked = isDark,
                        onCheckedChange = { viewModel.updateTheme(it) },
                        modifier = Modifier.testTag("dark_theme_switch")
                    )
                }
            }
        }

        // Backup and Restore
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = if (lang == "ne") "डाटा व्यवस्थापन" else "Off-Grid Data Backup & Restore", fontWeight = FontWeight.Bold)
                    
                    OutlinedButton(
                        onClick = { viewModel.exportBackupToJson(context) },
                        modifier = Modifier.fillMaxWidth().testTag("backup_btn")
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = L10n.backupData(lang))
                    }

                    OutlinedButton(
                        onClick = { showRestoreDialog = true },
                        modifier = Modifier.fillMaxWidth().testTag("restore_btn")
                    ) {
                        Icon(Icons.Default.CloudUpload, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = L10n.restoreData(lang))
                    }
                }
            }
        }

        // Reset application database
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = if (lang == "ne") "विशेष संवेदनशील कार्यहरू" else "Danger Area", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        onClick = { showResetDialog = true },
                        modifier = Modifier.fillMaxWidth().testTag("reset_app_btn")
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = L10n.resetAppData(lang))
                    }
                }
            }
        }
    }

    // Restore Paste Dialog
    if (showRestoreDialog) {
        var pasteStr by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showRestoreDialog = false },
            title = { Text(if (lang == "ne") "ब्याकअप कोड रिस्टोर गर्नुहोस्" else "Paste Backup JSON String") },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = pasteStr,
                        onValueChange = { pasteStr = it },
                        label = { Text("Backup JSON String") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp),
                        maxLines = 10
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (pasteStr.isNotBlank()) {
                            val ok = viewModel.restoreBackupFromJsonString(context, pasteStr.trim())
                            if (ok) showRestoreDialog = false
                        }
                    }
                ) {
                    Text(if (lang == "ne") "रिस्टोर" else "Restore")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRestoreDialog = false }) {
                    Text(L10n.cancel(lang))
                }
            }
        )
    }

    // Delete Double Confirm Dialog
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text(if (lang == "ne") "स्थायी डाटाहरू हटाउनुहोस्?" else "Erase Database Permanently?") },
            text = { Text(L10n.resetConfirm(lang)) },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    onClick = {
                        showResetConfirmPassword = true
                        showResetDialog = false
                    }
                ) {
                    Text(if (lang == "ne") "हो, मेट्नुहोस्" else "Confirm Erase")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text(L10n.cancel(lang))
                }
            }
        )
    }

    if (showResetConfirmPassword) {
        PasswordConfirmDeleteDialog(
            lang = lang,
            onDismiss = { showResetConfirmPassword = false },
            onConfirm = {
                viewModel.resetAllData(context)
                showResetConfirmPassword = false
            }
        )
    }
}
