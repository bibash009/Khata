package com.example.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.example.data.GroupMember
import com.example.data.GroupTransaction
import com.example.data.PersonalExpense
import com.example.data.UserProfile
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PdfGenerator {

    private fun createPdfFile(context: Context, prefix: String): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        return File(context.cacheDir, "${prefix}_$timestamp.pdf")
    }

    fun generateIndividualPersonalBill(
        context: Context,
        expense: PersonalExpense,
        profile: UserProfile,
        lang: String
    ): File {
        val file = createPdfFile(context, "personal_bill")
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = document.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val paint = Paint().apply { isAntiAlias = true }

        // Draw header background
        paint.color = Color.parseColor("#1B365D") // Deep Treasury Slate
        canvas.drawRect(0f, 0f, 595f, 140f, paint)

        // Title
        paint.color = Color.WHITE
        paint.textSize = 24f
        paint.isFakeBoldText = true
        val appTitle = if (lang == "ne") "अर्थ मन्त्रालय" else "ARTHA MANTRALAYA"
        canvas.drawText(appTitle, 40f, 60f, paint)

        paint.textSize = 14f
        paint.isFakeBoldText = false
        val subtitle = if (lang == "ne") "व्यक्तिगत खर्च रसिद" else "Personal Expense Receipt"
        canvas.drawText(subtitle, 40f, 90f, paint)

        // Metadata on right side
        paint.textSize = 11f
        val billNoLabel = if (lang == "ne") "रसिद नम्बर: #${expense.id + 1000}" else "Bill No: #${expense.id + 1000}"
        val dateLabel = if (lang == "ne") "मिति: ${expense.date}" else "Date: ${expense.date}"
        canvas.drawText(billNoLabel, 420f, 55f, paint)
        canvas.drawText(dateLabel, 420f, 75f, paint)
        canvas.drawText(if (lang == "ne") "समय: ${expense.time}" else "Time: ${expense.time}", 420f, 95f, paint)

        // User profile Card
        paint.color = Color.parseColor("#F5F7FA")
        canvas.drawRect(40f, 170f, 555f, 230f, paint)

        paint.color = Color.parseColor("#1B365D")
        paint.textSize = 12f
        paint.isFakeBoldText = true
        val userInfoTitle = if (lang == "ne") "प्रयोगकर्ता विवरण" else "User Information"
        canvas.drawText(userInfoTitle, 55f, 195f, paint)

        paint.color = Color.BLACK
        paint.isFakeBoldText = false
        paint.textSize = 11f
        canvas.drawText("${if (lang == "ne") "नाम" else "Name"}: ${profile.name}", 55f, 215f, paint)

        // Expense Details Header Table
        paint.color = Color.parseColor("#E4E7EB")
        canvas.drawRect(40f, 260f, 555f, 290f, paint)

        paint.color = Color.parseColor("#1B365D")
        paint.isFakeBoldText = true
        canvas.drawText(if (lang == "ne") "विवरण" else "Description / Detail Key", 55f, 280f, paint)
        canvas.drawText(if (lang == "ne") "मूल्य / विवरण स्तर" else "Value Details", 300f, 280f, paint)

        // Transaction Details List
        val details = listOf(
            (if (lang == "ne") "खर्च शीर्षक" else "Expense Title") to expense.title,
            (if (lang == "ne") "विधा" else "Category") to L10n.translateCategory(expense.category, lang),
            (if (lang == "ne") "खर्च रकम" else "Amount (NPR)") to "रु ${String.format(Locale.US, "%.2f", expense.amount)}",
            (if (lang == "ne") "वर्णन" else "Description") to (expense.description ?: "-"),
            (if (lang == "ne") "टिप्पणी" else "Notes") to (expense.notes ?: "-")
        )

        var currentY = 320f
        paint.isFakeBoldText = false
        paint.color = Color.BLACK
        for ((label, valText) in details) {
            paint.color = Color.parseColor("#52606D")
            paint.isFakeBoldText = true
            canvas.drawText(label, 55f, currentY, paint)

            paint.color = Color.BLACK
            paint.isFakeBoldText = false
            canvas.drawText(valText, 300f, currentY, paint)

            // draw a subtle underline
            paint.color = Color.parseColor("#F5F7FA")
            canvas.drawLine(40f, currentY + 10f, 555f, currentY + 10f, paint)
            currentY += 40f
        }

        // Total Summary Card
        paint.color = Color.parseColor("#1B365D")
        canvas.drawRect(40f, currentY + 10f, 555f, currentY + 70f, paint)

        paint.color = Color.WHITE
        paint.textSize = 14f
        paint.isFakeBoldText = true
        canvas.drawText(if (lang == "ne") "कुल भुक्तानी रसिद योग" else "Total Receipt Amount", 60f, currentY + 45f, paint)
        canvas.drawText("रु ${String.format(Locale.US, "%.2f", expense.amount)}", 420f, currentY + 45f, paint)

        // Footer block
        paint.color = Color.GRAY
        paint.textSize = 9f
        paint.isFakeBoldText = false
        val footerDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(Date())
        canvas.drawText("${if (lang == "ne") "बनाएको मिति" else "Generated on"}: $footerDate", 40f, 800f, paint)
        canvas.drawText(if (lang == "ne") "यो अर्थ मन्त्रालय अफलाइन एपद्वारा स्वतः उत्पन्न बिल हो।" else "This is an offline system generated receipt from Artha Mantralaya.", 40f, 815f, paint)

        document.finishPage(page)
        document.writeTo(FileOutputStream(file))
        document.close()
        return file
    }

    fun generateMonthlyPersonalReport(
        context: Context,
        month: String,
        budget: Double,
        expenses: List<PersonalExpense>,
        profile: UserProfile,
        lang: String
    ): File {
        val file = createPdfFile(context, "personal_monthly_${month}")
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = document.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val paint = Paint().apply { isAntiAlias = true }

        // Header Background
        paint.color = Color.parseColor("#1B365D")
        canvas.drawRect(0f, 0f, 595f, 130f, paint)

        // App title
        paint.color = Color.WHITE
        paint.textSize = 22f
        paint.isFakeBoldText = true
        canvas.drawText(if (lang == "ne") "अर्थ मन्त्रालय" else "ARTHA MANTRALAYA", 40f, 55f, paint)

        paint.textSize = 13f
        paint.isFakeBoldText = false
        canvas.drawText(if (lang == "ne") "मासिक व्यक्तिगत खर्च विवरण" else "Monthly Personal Expenditure Statement", 40f, 82f, paint)
        canvas.drawText("${if (lang == "ne") "महिना" else "Month/Year"}: $month", 40f, 105f, paint)

        // Metadata
        paint.textSize = 10f
        val genDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(Date())
        canvas.drawText("${if (lang == "ne") "बनाएको मिति" else "Generated On"}: $genDate", 410f, 55f, paint)
        canvas.drawText("${if (lang == "ne") "प्रयोगकर्ता" else "User Name"}: ${profile.name}", 410f, 75f, paint)

        // Calculations
        val totalSpent = expenses.sumOf { it.amount }
        val remaining = budget - totalSpent

        // Budget Summary Cards
        paint.color = Color.parseColor("#F5F7FA")
        canvas.drawRect(40f, 150f, 195f, 210f, paint) // Card 1
        canvas.drawRect(215f, 150f, 370f, 210f, paint) // Card 2
        canvas.drawRect(390f, 150f, 555f, 210f, paint) // Card 3

        paint.color = Color.parseColor("#1B365D")
        paint.textSize = 10f
        paint.isFakeBoldText = true
        canvas.drawText(if (lang == "ne") "मासिक बजेट" else "Monthly Budget", 50f, 170f, paint)
        canvas.drawText(if (lang == "ne") "कुल खर्च" else "Total Spent", 225f, 170f, paint)
        canvas.drawText(if (lang == "ne") "बाँकी बजेट" else "Remaining Budget", 400f, 170f, paint)

        paint.color = Color.BLACK
        paint.textSize = 14f
        canvas.drawText("रु ${String.format(Locale.US, "%.2f", budget)}", 50f, 195f, paint)
        paint.color = if (totalSpent > budget) Color.RED else Color.parseColor("#1B365D")
        canvas.drawText("रु ${String.format(Locale.US, "%.2f", totalSpent)}", 225f, 195f, paint)
        paint.color = if (remaining < 0) Color.RED else Color.parseColor("#107C41") // Dark green
        canvas.drawText("रु ${String.format(Locale.US, "%.2f", remaining)}", 400f, 195f, paint)

        // Transactions Table Header
        paint.color = Color.parseColor("#1B365D")
        canvas.drawRect(40f, 235f, 555f, 260f, paint)

        paint.color = Color.WHITE
        paint.textSize = 10f
        paint.isFakeBoldText = true
        canvas.drawText(if (lang == "ne") "मिति" else "Date", 45f, 252f, paint)
        canvas.drawText(if (lang == "ne") "शीर्षक / विधा" else "Title / Category", 125f, 252f, paint)
        canvas.drawText(if (lang == "ne") "विधा" else "Category", 340f, 252f, paint)
        canvas.drawText(if (lang == "ne") "रकम" else "Amount", 480f, 252f, paint)

        paint.color = Color.BLACK
        paint.isFakeBoldText = false
        var currentY = 282f
        
        // draw up to 12 recent items due to single page constraint
        val showList = expenses.take(13)
        for (item in showList) {
            canvas.drawText(item.date, 45f, currentY, paint)
            
            // Trim title if too long
            val titleTrimmed = if (item.title.length > 24) item.title.substring(0, 21) + "..." else item.title
            canvas.drawText(titleTrimmed, 125f, currentY, paint)
            canvas.drawText(L10n.translateCategory(item.category, lang), 340f, currentY, paint)
            canvas.drawText("रु ${String.format(Locale.US, "%.2f", item.amount)}", 480f, currentY, paint)

            paint.color = Color.parseColor("#E4E7EB")
            canvas.drawLine(40f, currentY + 8f, 555f, currentY + 8f, paint)
            paint.color = Color.BLACK

            currentY += 28f
        }

        if (expenses.size > 13) {
            paint.color = Color.GRAY
            paint.textSize = 9f
            canvas.drawText("+ ${expenses.size - 13} more transactions occurred in this month...", 45f, currentY + 5f, paint)
        }

        // Footer
        paint.color = Color.GRAY
        paint.textSize = 9f
        paint.isFakeBoldText = false
        canvas.drawText(if (lang == "ne") "यो अर्थ मन्त्रालय अफलाइन एपद्वारा स्वतः उत्पन्न विबरण हो।" else "This is an offline system generated statement from Artha Mantralaya.", 40f, 815f, paint)

        document.finishPage(page)
        document.writeTo(FileOutputStream(file))
        document.close()
        return file
    }

    fun generateIndividualGroupBill(
        context: Context,
        tx: GroupTransaction,
        paidBy: String,
        splitMembers: List<String>,
        shares: List<Double>,
        lang: String
    ): File {
        val file = createPdfFile(context, "group_tx_bill")
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = document.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val paint = Paint().apply { isAntiAlias = true }

        // Header Background
        paint.color = Color.parseColor("#0F5132") // Dark Forest Green for splits
        canvas.drawRect(0f, 0f, 595f, 130f, paint)

        // Title
        paint.color = Color.WHITE
        paint.textSize = 22f
        paint.isFakeBoldText = true
        canvas.drawText(if (lang == "ne") "अर्थ मन्त्रालय - समूह हिसाब" else "ARTHA MANTRALAYA - GROUP SPLIT", 40f, 55f, paint)

        paint.textSize = 13f
        paint.isFakeBoldText = false
        canvas.drawText(if (lang == "ne") "समूह विभाजन रसिद" else "Group Share / Settlement Bill", 40f, 82f, paint)

        // Metadata on right side
        paint.textSize = 10f
        canvas.drawText(if (lang == "ne") "मिति: ${tx.date}" else "Date: ${tx.date}", 430f, 55f, paint)
        canvas.drawText(if (lang == "ne") "समय: ${tx.time}" else "Time: ${tx.time}", 430f, 75f, paint)
        canvas.drawText(if (lang == "ne") "रसिद नम्बर: #G-${tx.id + 2000}" else "Bill Number: #G-${tx.id + 2000}", 430f, 95f, paint)

        // Transaction main details
        paint.color = Color.parseColor("#F4F8F6")
        canvas.drawRect(40f, 150f, 555f, 220f, paint)

        paint.color = Color.parseColor("#0F5132")
        paint.textSize = 12f
        paint.isFakeBoldText = true
        canvas.drawText(if (lang == "ne") "कारोबार विवरण" else "Transaction Details", 60f, 175f, paint)

        paint.color = Color.BLACK
        paint.isFakeBoldText = false
        paint.textSize = 11f
        canvas.drawText("${if (lang == "ne") "कारोबार नाम" else "Transaction"}: ${tx.name}", 60f, 202f, paint)
        canvas.drawText("${if (lang == "ne") "कुल रकम" else "Total Amount"}: रु ${String.format(Locale.US, "%.2f", tx.amount)}", 270f, 202f, paint)
        canvas.drawText("${if (lang == "ne") "तिर्ने सदस्य" else "Paid By"}: $paidBy", 420f, 202f, paint)

        // Splits Table Header
        paint.color = Color.parseColor("#0F5132")
        canvas.drawRect(40f, 250f, 555f, 280f, paint)

        paint.color = Color.WHITE
        paint.textSize = 11f
        paint.isFakeBoldText = true
        canvas.drawText(if (lang == "ne") "बाँडफाँड सदस्यको नाम" else "Member Split List", 60f, 270f, paint)
        canvas.drawText(if (lang == "ne") "बाँडफाँड हिस्सा" else "Settle Share Amount", 350f, 270f, paint)

        paint.color = Color.BLACK
        paint.isFakeBoldText = false
        var currentY = 305f

        for (i in splitMembers.indices) {
            canvas.drawText(splitMembers[i], 60f, currentY, paint)
            canvas.drawText("रु ${String.format(Locale.US, "%.2f", shares.getOrElse(i) { 0.0 })}", 350f, currentY, paint)

            paint.color = Color.parseColor("#E4E7EB")
            canvas.drawLine(40f, currentY + 10f, 555f, currentY + 10f, paint)
            paint.color = Color.BLACK

            currentY += 35f
        }

        // Footer
        paint.color = Color.GRAY
        paint.textSize = 9f
        paint.isFakeBoldText = false
        canvas.drawText(if (lang == "ne") "यो अर्थ मन्त्रालय अफलाइन एपद्वारा स्वतः उत्पन्न विबरण हो।" else "This is an offline system generated group statement from Artha Mantralaya.", 40f, 815f, paint)

        document.finishPage(page)
        document.writeTo(FileOutputStream(file))
        document.close()
        return file
    }

    fun generateMonthlyGroupReport(
        context: Context,
        month: String,
        members: List<GroupMember>,
        txs: List<GroupTransaction>,
        settlements: List<DebtTransfer>,
        lang: String
    ): File {
        val file = createPdfFile(context, "group_monthly_${month}")
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = document.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val paint = Paint().apply { isAntiAlias = true }

        // Header
        paint.color = Color.parseColor("#0F5132")
        canvas.drawRect(0f, 0f, 595f, 130f, paint)

        paint.color = Color.WHITE
        paint.textSize = 21f
        paint.isFakeBoldText = true
        canvas.drawText(if (lang == "ne") "अर्थ मन्त्रालय - मासिक समूह विवरण" else "ARTHA MANTRALAYA - GROUP MONTHLY SUMMARY", 40f, 55f, paint)

        paint.textSize = 12f
        paint.isFakeBoldText = false
        canvas.drawText("${if (lang == "ne") "महिना" else "Month/Year"}: $month", 40f, 85f, paint)
        canvas.drawText("${if (lang == "ne") "जम्मा सदस्यहरू" else "Total Members"}: ${members.size}", 40f, 105f, paint)

        // Metadata right side
        val genDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(Date())
        canvas.drawText("${if (lang == "ne") "बनाएको मिति" else "Generated On"}: $genDate", 410f, 55f, paint)

        val totalExpenses = txs.sumOf { it.amount }

        // Brief Summary Box
        paint.color = Color.parseColor("#F4F8F6")
        canvas.drawRect(40f, 150f, 555f, 200f, paint)

        paint.color = Color.parseColor("#0F5132")
        paint.textSize = 12f
        paint.isFakeBoldText = true
        canvas.drawText("${if (lang == "ne") "समूहको कुल खर्च" else "Total Group Splitted Expenses"}: रु ${String.format(Locale.US, "%.2f", totalExpenses)}", 60f, 180f, paint)

        // Settlement Table Header
        paint.color = Color.parseColor("#0F5132")
        canvas.drawRect(40f, 220f, 555f, 245f, paint)

        paint.color = Color.WHITE
        paint.textSize = 10f
        paint.isFakeBoldText = true
        canvas.drawText(if (lang == "ne") "तिर्नु पर्ने व्यक्ति (Debtor)" else "Person Who Owes (Debtor)", 50f, 237f, paint)
        canvas.drawText(if (lang == "ne") "पाउनु पर्ने व्यक्ति (Creditor)" else "Owes To (Creditor)", 270f, 237f, paint)
        canvas.drawText(if (lang == "ne") "तिर्न बाँकी रकम" else "Settle Amount", 450f, 237f, paint)

        paint.color = Color.BLACK
        paint.isFakeBoldText = false
        var currentY = 270f

        val showSets = settlements.take(15)
        if (showSets.isEmpty()) {
            canvas.drawText(if (lang == "ne") "कुनै बाँकी रकम छैन! सबै भुक्तानी र हिसाब बराबर छन्।" else "All accounts are settled! No payments pending.", 60f, currentY + 15f, paint)
        } else {
            for (set in showSets) {
                canvas.drawText(set.debtorName, 50f, currentY, paint)
                canvas.drawText(set.creditorName, 270f, currentY, paint)
                canvas.drawText("रु ${String.format(Locale.US, "%.2f", set.amount)}", 450f, currentY, paint)

                paint.color = Color.parseColor("#E4E7EB")
                canvas.drawLine(40f, currentY + 8f, 555f, currentY + 8f, paint)
                paint.color = Color.BLACK

                currentY += 28f
            }
        }

        // Footer
        paint.color = Color.GRAY
        paint.textSize = 9f
        paint.isFakeBoldText = false
        canvas.drawText(if (lang == "ne") "यो अर्थ मन्त्रालय अफलाइन एपद्वारा स्वतः उत्पन्न समूह विवरण हो।" else "This is an offline system generated group statement from Artha Mantralaya.", 40f, 815f, paint)

        document.finishPage(page)
        document.writeTo(FileOutputStream(file))
        document.close()
        return file
    }
}
