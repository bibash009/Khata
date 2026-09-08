package com.example.ui

object L10n {
    fun appName(lang: String) = if (lang == "ne") "अर्थ मन्त्रालय (Artha Mantralaya)" else "Artha Mantralaya"
    fun tabHome(lang: String) = if (lang == "ne") "गृहपृष्ठ" else "Home"
    fun tabPersonal(lang: String) = if (lang == "ne") "व्यक्तिगत" else "Personal"
    fun tabGroup(lang: String) = if (lang == "ne") "समूह" else "Group"
    fun tabReports(lang: String) = if (lang == "ne") "रिपोर्ट" else "Reports"
    fun tabSettings(lang: String) = if (lang == "ne") "सेटिङ" else "Settings"

    // Dashboard
    fun userProfile(lang: String) = if (lang == "ne") "प्रयोगकर्ता प्रोफाइल" else "User Profile"
    fun editProfile(lang: String) = if (lang == "ne") "प्रोफाइल सम्पादन गर्नुहोस्" else "Edit Profile"
    fun userName(lang: String) = if (lang == "ne") "प्रयोगकर्ताको नाम" else "User Name"
    fun profilePhoto(lang: String) = if (lang == "ne") "प्रोफाइल फोटो" else "Profile Photo"
    fun selectGallery(lang: String) = if (lang == "ne") "ग्यालेरीबाट छान्नुहोस्" else "Select from Gallery"
    fun save(lang: String) = if (lang == "ne") "बचत गर्नुहोस्" else "Save"
    fun cancel(lang: String) = if (lang == "ne") "रद्द गर्नुहोस्" else "Cancel"

    // Monthly Budget Card
    fun monthlyBudget(lang: String) = if (lang == "ne") "मासिक बजेट" else "Monthly Budget"
    fun budgetCardTitle(lang: String) = if (lang == "ne") "मासिक बजेट विवरण" else "Monthly Budget Details"
    fun spent(lang: String) = if (lang == "ne") "खर्च भएको" else "Total Spent"
    fun remaining(lang: String) = if (lang == "ne") "बाँकी रकम" else "Remaining"
    fun progress(lang: String) = if (lang == "ne") "बजेट खर्च प्रतिशत" else "Spending Percentage"
    fun setBudget(lang: String) = if (lang == "ne") "बजेट सेट गर्नुहोस्" else "Set Monthly Budget"
    fun enterBudgetAmount(lang: String) = if (lang == "ne") "बजेट रकम हाल्नुहोस् (रु)" else "Enter Budget Amount (NPR)"
    fun budgetUpdated(lang: String) = if (lang == "ne") "बजेट सफलतापूर्वक अद्यावधिक भयो" else "Budget updated successfully"

    // Personal Expenses
    fun personalExpensesTitle(lang: String) = if (lang == "ne") "व्यक्तिगत खर्च व्यवस्थापक" else "Personal Expense Manager"
    fun addExpense(lang: String) = if (lang == "ne") "खर्च थप्नुहोस्" else "Add Expense"
    fun editExpense(lang: String) = if (lang == "ne") "खर्च सम्पादन गर्नुहोस्" else "Edit Expense"
    fun title(lang: String) = if (lang == "ne") "शीर्षक" else "Expense Title"
    fun amount(lang: String) = if (lang == "ne") "रकम" else "Amount"
    fun category(lang: String) = if (lang == "ne") "विधा" else "Category"
    fun date(lang: String) = if (lang == "ne") "मिति" else "Date"
    fun time(lang: String) = if (lang == "ne") "समय" else "Time"
    fun description(lang: String) = if (lang == "ne") "विवरण (ऐच्छिक)" else "Description (optional)"
    fun notes(lang: String) = if (lang == "ne") "टिप्पणी (ऐच्छिक)" else "Notes (optional)"
    fun searchPlaceHolder(lang: String) = if (lang == "ne") "खोज्नुहोस्..." else "Search transactions..."
    fun emptyExpenses(lang: String) = if (lang == "ne") "हालसम्म कुनै खर्च रेकर्ड गरिएको छैन!" else "No transactions recorded yet!"
    fun deleteConfirm(lang: String) = if (lang == "ne") "के तपाईं यो मेटाउन चाहनुहुन्छ?" else "Are you sure you want to delete this?"
    fun delete(lang: String) = if (lang == "ne") "मेटाउनुहोस्" else "Delete"

    // Categories
    fun catFood(lang: String) = if (lang == "ne") "खाना" else "Food"
    fun catTransport(lang: String) = if (lang == "ne") "यातायात" else "Transport"
    fun catEducation(lang: String) = if (lang == "ne") "शिक्षा" else "Education"
    fun catRent(lang: String) = if (lang == "ne") "भाडा" else "Rent"
    fun catShopping(lang: String) = if (lang == "ne") "किनमेल" else "Shopping"
    fun catEntertainment(lang: String) = if (lang == "ne") "मनोरञ्जन" else "Entertainment"
    fun catHealth(lang: String) = if (lang == "ne") "स्वास्थ्य" else "Health"
    fun catUtilities(lang: String) = if (lang == "ne") "उपयोगिताहरू" else "Utilities"
    fun catOthers(lang: String) = if (lang == "ne") "अन्य" else "Others"

    fun translateCategory(cat: String, lang: String): String {
        return when (cat.lowercase()) {
            "food" -> catFood(lang)
            "transport" -> catTransport(lang)
            "education" -> catEducation(lang)
            "rent" -> catRent(lang)
            "shopping" -> catShopping(lang)
            "entertainment" -> catEntertainment(lang)
            "health" -> catHealth(lang)
            "utilities" -> catUtilities(lang)
            else -> catOthers(lang)
        }
    }

    // Bill Generation
    fun generateBill(lang: String) = if (lang == "ne") "बिल बनाउनुहोस्" else "Generate Bill"
    fun individualBill(lang: String) = if (lang == "ne") "व्यक्तिगत रसिद" else "Individual Bill"
    fun billNumber(lang: String) = if (lang == "ne") "रसिद नम्बर" else "Bill Number"
    fun exportPDF(lang: String) = if (lang == "ne") "PDF मा निर्यात गर्नुहोस्" else "Export PDF"
    fun exportReport(lang: String) = if (lang == "ne") "मासिक विवरण निर्यात" else "Export Monthly Report"
    fun shareBill(lang: String) = if (lang == "ne") "बिल साझा गर्नुहोस्" else "Share Bill"
    fun billHeader(lang: String) = if (lang == "ne") "अर्थ मन्त्रालय खर्च रसिद" else "Artha Mantralaya Bill Receipt"
    fun generatedOn(lang: String) = if (lang == "ne") "बनाएको मिति" else "Generated On"

    // Group Expenses
    fun groupDashboard(lang: String) = if (lang == "ne") "समूह खर्च ड्यासबोर्ड" else "Group Splitter Dashboard"
    fun totalGroupExpenses(lang: String) = if (lang == "ne") "कुल समूह खर्च" else "Total Group Expenses"
    fun totalMembers(lang: String) = if (lang == "ne") "सदस्य संख्या" else "Total Members"
    fun memberSection(lang: String) = if (lang == "ne") "सदस्यहरूको विवरण" else "Members"
    fun groupTransactions(lang: String) = if (lang == "ne") "समूह कारोबार" else "Group Transactions"
    fun addMember(lang: String) = if (lang == "ne") "सदस्य थप्नुहोस्" else "Add Member"
    fun editMember(lang: String) = if (lang == "ne") "सदस्य विवरण अद्यावधिक" else "Edit Member"
    fun memberName(lang: String) = if (lang == "ne") "नाम" else "Member Name"
    fun memberPhone(lang: String) = if (lang == "ne") "फोन (ऐच्छिक)" else "Phone (optional)"
    fun paidBy(lang: String) = if (lang == "ne") "तिर्ने व्यक्ति" else "Paid By"
    fun splitWith(lang: String) = if (lang == "ne") "बाँडफाँड गर्ने सदस्यहरू" else "Split With"
    fun amountOwed(lang: String) = if (lang == "ne") "तिर्नु पर्ने" else "Owes"
    fun amountReceivable(lang: String) = if (lang == "ne") "प्राप्त हुने" else "Receivable"
    fun netBalance(lang: String) = if (lang == "ne") "नेट ब्यालेन्स" else "Net Balance"
    fun settlements(lang: String) = if (lang == "ne") "फरफारक हिसाब" else "Settlements Summary"
    fun memberProfile(lang: String) = if (lang == "ne") "सदस्य प्रोफाइल" else "Member Profile"
    fun noMembers(lang: String) = if (lang == "ne") "कुनै सदस्य थपिएको छैन" else "No members added yet"
    fun selectSplitWith(lang: String) = if (lang == "ne") "हिसाब बाँड्ने सदस्यहरू चयन गर्नुहोस्" else "Select members to split with"
    fun selectPaidBy(lang: String) = if (lang == "ne") "तिर्ने सदस्य चयन गर्नुहोस्" else "Select who paid"

    // Settings & Reports
    fun changeLanguage(lang: String) = if (lang == "ne") "भाषा परिवर्तन गर्नुहोस्" else "Change Language"
    fun changeTheme(lang: String) = if (lang == "ne") "थिम परिवर्तन गर्नुहोस्" else "Change Theme"
    fun backupData(lang: String) = if (lang == "ne") "डाटा ब्याकअप गर्नुहोस् (JSON)" else "Backup Data (JSON)"
    fun restoreData(lang: String) = if (lang == "ne") "डाटा रिस्टोर गर्नुहोस् (JSON)" else "Restore Data (JSON)"
    fun resetAppData(lang: String) = if (lang == "ne") "सबै डाटा खाली गर्नुहोस्" else "Reset App Data"
    fun resetConfirm(lang: String) = if (lang == "ne") "के तपाईं यो एपको सम्पूर्ण विवरण पूर्ण रूपमा हटाउन चाहनुहुन्छ?" else "Are you sure you want to erase all app directories & transaction history permanently?"
    fun resetSuccess(lang: String) = if (lang == "ne") "नयाँ जस्तै खाली गरियो" else "App reset successfully"
    fun backupSuccess(lang: String) = if (lang == "ne") "ब्याकअप डाउनलोडमा सुरक्षित भयो" else "Backup saved to Downloads folder"
    fun restoreSuccess(lang: String) = if (lang == "ne") "डाटा रिस्टोर गरियो" else "Data restored successfully"
    fun restoreError(lang: String) = if (lang == "ne") "ब्याकअप फाइल मिलेन" else "Invalid backup file"

    // Monthly Report View
    fun reportsTitle(lang: String) = if (lang == "ne") "विश्लेषण र रिपोर्ट" else "Financial Reports"
    fun categoryWiseTitle(lang: String) = if (lang == "ne") "विधागत खर्च विश्लेषण" else "Category-Wise Analytics"
    fun monthlyPersonalBill(lang: String) = if (lang == "ne") "व्यक्तिगत मासिक खर्च विवरण " else "Monthly Personal Bill"
    fun monthlyGroupBill(lang: String) = if (lang == "ne") "समूह मासिक हिसाब विबरण" else "Monthly Group Splitter Bill"
    fun currencyNpr(lang: String) = "रु"
    fun selectMonth(lang: String) = if (lang == "ne") "महिना चयन गर्नुहोस्" else "Select Month"
}
