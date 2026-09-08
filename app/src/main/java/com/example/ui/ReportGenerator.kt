package com.example.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.example.data.GroupMember
import com.example.data.GroupTransaction
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ReportGenerator {

    private fun createReportFile(context: Context, prefix: String, extension: String): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        return File(context.cacheDir, "${prefix}_$timestamp.$extension")
    }

    // Helper class to compute all totals
    data class GroupReportData(
        val groupName: String,
        val reportPeriod: String,
        val membersList: List<GroupMember>,
        val filteredTxs: List<GroupTransaction>,
        val totalGroupExpenses: Double,
        val memberPaidMap: Map<Int, Double>,
        val memberOwedMap: Map<Int, Double>,
        val pairwiseDebts: List<Triple<String, String, Double>> // Pairwise simplified or calculated debts: debtor, creditor, amount
    )

    fun prepareReportData(
        groupName: String,
        periodDesc: String,
        members: List<GroupMember>,
        allTransactions: List<GroupTransaction>,
        filterType: String, // "month", "year", "custom", "all"
        monthKey: String = "", // "YYYY-MM"
        yearKey: String = "", // "YYYY"
        startDate: String = "", // "YYYY-MM-DD"
        endDate: String = "" // "YYYY-MM-DD"
    ): GroupReportData {
        // 1. Filter Transactions
        val filtered = allTransactions.filter { tx ->
            when (filterType) {
                "month" -> tx.date.startsWith(monthKey)
                "year" -> tx.date.startsWith(yearKey)
                "custom" -> tx.date >= startDate && tx.date <= endDate
                else -> true // all-time
            }
        }.sortedWith(compareBy<GroupTransaction> { it.date }.thenBy { it.time })

        // 2. Compute totals
        val totalExp = filtered.sumOf { it.amount }
        
        val paidMap = members.associate { it.id to 0.0 }.toMutableMap()
        val owedMap = members.associate { it.id to 0.0 }.toMutableMap()

        for (tx in filtered) {
            paidMap[tx.paidById] = (paidMap[tx.paidById] ?: 0.0) + tx.amount

            val splits = tx.splitWithIds.split(",")
                .filter { it.isNotEmpty() }
                .mapNotNull { it.toIntOrNull() }
                .filter { id -> members.any { it.id == id } }

            if (splits.isNotEmpty()) {
                val share = tx.amount / splits.size
                for (sId in splits) {
                    owedMap[sId] = (owedMap[sId] ?: 0.0) + share
                }
            }
        }

        // Calculate simplified net debts between everyone
        // Let's use the direct/net relationship approach mathematically
        val directOwed = mutableMapOf<Pair<Int, Int>, Double>()
        for (m1 in members) {
            for (m2 in members) {
                if (m1.id != m2.id) {
                    directOwed[Pair(m1.id, m2.id)] = 0.0
                }
            }
        }

        for (tx in filtered) {
            val splits = tx.splitWithIds.split(",")
                .filter { it.isNotEmpty() }
                .mapNotNull { it.toIntOrNull() }
                .filter { sId -> members.any { it.id == sId } }
            if (splits.isEmpty()) continue
            val share = tx.amount / splits.size
            val pId = tx.paidById
            for (sId in splits) {
                if (sId != pId) {
                    val key = Pair(sId, pId)
                    directOwed[key] = (directOwed[key] ?: 0.0) + share
                }
            }
        }

        // Pairwise offset
        val debtList = mutableListOf<Triple<String, String, Double>>()
        val visited = mutableSetOf<Pair<Int, Int>>()
        for (m1 in members) {
            for (m2 in members) {
                if (m1.id >= m2.id) continue
                val raw1o2 = directOwed[Pair(m1.id, m2.id)] ?: 0.0
                val raw2o1 = directOwed[Pair(m2.id, m1.id)] ?: 0.0
                val net = raw1o2 - raw2o1
                if (net > 0.01) {
                    debtList.add(Triple(m1.name, m2.name, net))
                } else if (net < -0.01) {
                    debtList.add(Triple(m2.name, m1.name, -net))
                }
            }
        }

        return GroupReportData(
            groupName = if (groupName.isNotBlank()) groupName else "Artha Splitter Group",
            reportPeriod = periodDesc,
            membersList = members,
            filteredTxs = filtered,
            totalGroupExpenses = totalExp,
            memberPaidMap = paidMap,
            memberOwedMap = owedMap,
            pairwiseDebts = debtList
        )
    }

    // CSV format exporter
    fun exportToCsv(context: Context, data: GroupReportData): File {
        val file = createReportFile(context, "group_report", "csv")
        val sb = StringBuilder()

        sb.append("\"Developer Profile\",\"Shri Bibash Lamichhane\"\n")
        sb.append("\"Group Report Information\"\n")
        sb.append("\"Group Name\",\"${escapeCsv(data.groupName)}\"\n")
        sb.append("\"Total Members\",\"${data.membersList.size}\"\n")
        sb.append("\"Report Period\",\"${escapeCsv(data.reportPeriod)}\"\n")
        sb.append("\"Generated At\",\"${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date())}\"\n")
        sb.append("\n")

        sb.append("\"Group Expense Totals\"\n")
        sb.append("\"Member Name\",\"Total Paid (NPR)\",\"Total Owed (Share NPR)\",\"Net Balance (NPR)\"\n")
        data.membersList.forEach { m ->
            val paid = data.memberPaidMap[m.id] ?: 0.0
            val owed = data.memberOwedMap[m.id] ?: 0.0
            val net = paid - owed
            sb.append("\"${escapeCsv(m.name)}\",\"${String.format(Locale.US, "%.2f", paid)}\",\"${String.format(Locale.US, "%.2f", owed)}\",\"${String.format(Locale.US, "%.2f", net)}\"\n")
        }
        sb.append("\"Total Group Expenses\",\"${String.format(Locale.US, "%.2f", data.totalGroupExpenses)}\"\n")
        sb.append("\n")

        sb.append("\"Pairwise Settlement Summary\"\n")
        if (data.pairwiseDebts.isEmpty()) {
            sb.append("\"Status\",\"All transactions resolved! No debts pending.\"\n")
        } else {
            sb.append("\"Debtor (Owes)\",\"Creditor (Is Owed)\",\"Net Amount (NPR)\"\n")
            data.pairwiseDebts.forEach { debt ->
                sb.append("\"${escapeCsv(debt.first)}\",\"${escapeCsv(debt.second)}\",\"${String.format(Locale.US, "%.2f", debt.third)}\"\n")
            }
        }
        sb.append("\n")

        sb.append("\"Transaction History\"\n")
        sb.append("\"Date\",\"Payer\",\"Transaction Name\",\"Shared With Members\",\"Total Amount (NPR)\",\"Individual Share Amount (NPR)\"\n")
        data.filteredTxs.forEach { tx ->
            val payerName = data.membersList.find { it.id == tx.paidById }?.name ?: "Unknown"
            val splits = tx.splitWithIds.split(",")
                .filter { it.isNotEmpty() }
                .mapNotNull { it.toIntOrNull() }
                .map { id -> data.membersList.find { it.id == id }?.name ?: "ID-$id" }
                .joinToString(", ")
            val numSplits = maxOf(1, tx.splitWithIds.split(",").filter { it.isNotEmpty() }.size)
            val indShare = tx.amount / numSplits

            sb.append("\"${tx.date}\",\"${escapeCsv(payerName)}\",\"${escapeCsv(tx.name)}\",\"${escapeCsv(splits)}\",\"${String.format(Locale.US, "%.2f", tx.amount)}\",\"${String.format(Locale.US, "%.2f", indShare)}\"\n")
        }

        FileOutputStream(file).use { out ->
            out.write(sb.toString().toByteArray(Charsets.UTF_8))
        }
        return file
    }

    // HTML / Excel format exporter (.xls / .xlsx styled wrapper)
    fun exportToExcel(context: Context, data: GroupReportData): File {
        val file = createReportFile(context, "group_report", "xlsx")
        val sb = StringBuilder()

        sb.append("<html xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns=\"http://www.w3.org/TR/REC-html40\">\n")
        sb.append("<head>\n")
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n")
        sb.append("<style>\n")
        sb.append("  body { font-family: 'Segoe UI', Tahoma, Arial, sans-serif; margin: 20px; color: #333; }\n")
        sb.append("  h1, h3 { color: #0f5132; }\n")
        sb.append("  table { border-collapse: collapse; width: 100%; margin-bottom: 25px; box-shadow: 0 2px 3px rgba(0,0,0,0.1); }\n")
        sb.append("  th { background-color: #1a5c3d; color: white; border: 1px solid #ddd; padding: 10px; font-weight: bold; text-align: left; }\n")
        sb.append("  td { border: 1px solid #ddd; padding: 10px; text-align: left; }\n")
        sb.append("  tr:nth-child(even) { background-color: #f9f9f9; }\n")
        sb.append("  .header-box { background-color: #f3fbf7; border: 2px solid #1a5c3d; padding: 20px; border-radius: 8px; margin-bottom: 25px; }\n")
        sb.append("  .dev-name { font-size: 20px; font-weight: bold; color: #1a5c3d; margin-bottom: 10px; font-family: Arial, sans-serif; }\n")
        sb.append("  .badge { padding: 4px 8px; border-radius: 4px; font-weight: bold; font-size: 11px; }\n")
        sb.append("  .green { color: #155724; background-color: #d4edda; }\n")
        sb.append("  .red { color: #721c24; background-color: #f8d7da; }\n")
        sb.append("  .accent { font-weight: bold; color: #1a5c3d; }\n")
        sb.append("</style>\n")
        sb.append("</head>\n")
        sb.append("<body>\n")

        sb.append("<div class=\"header-box\">\n")
        sb.append("  <div class=\"dev-name\">Developer: <b>Shri Bibash Lamichhane</b></div>\n")
        sb.append("  <h2>ARTHA SPLITTER GROUP STATEMENT</h2>\n")
        sb.append("  <p><b>Group Name:</b> ${data.groupName}</p>\n")
        sb.append("  <p><b>Total Group Members:</b> ${data.membersList.size}</p>\n")
        sb.append("  <p><b>Report Period:</b> ${data.reportPeriod}</p>\n")
        sb.append("  <p><b>Generated At:</b> ${SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(Date())}</p>\n")
        sb.append("</div>\n")

        sb.append("<h3>1. MEMBERS totals</h3>\n")
        sb.append("<table>\n")
        sb.append("  <thead>\n")
        sb.append("    <tr>\n")
        sb.append("      <th>Member ID</th>\n")
        sb.append("      <th>Member Name</th>\n")
        sb.append("      <th>Total Amount Paid (NPR)</th>\n")
        sb.append("      <th>Total Expenses Share (Owed NPR)</th>\n")
        sb.append("      <th>Net Balance Status</th>\n")
        sb.append("    </tr>\n")
        sb.append("  </thead>\n")
        sb.append("  <tbody>\n")
        data.membersList.forEach { m ->
            val paid = data.memberPaidMap[m.id] ?: 0.0
            val owed = data.memberOwedMap[m.id] ?: 0.0
            val net = paid - owed
            val statusClass = if (net >= 0) "green" else "red"
            val statusSign = if (net >= 0) "+" else ""

            sb.append("    <tr>\n")
            sb.append("      <td>${m.id}</td>\n")
            sb.append("      <td class=\"accent\">${m.name}</td>\n")
            sb.append("      <td>रु ${String.format(Locale.US, "%.2f", paid)}</td>\n")
            sb.append("      <td>रु ${String.format(Locale.US, "%.2f", owed)}</td>\n")
            sb.append("      <td><span class=\"badge $statusClass\">$statusSign रु ${String.format(Locale.US, "%.2f", net)}</span></td>\n")
            sb.append("    </tr>\n")
        }
        sb.append("    <tr style=\"background-color:#eaeaea; font-weight:bold;\">\n")
        sb.append("      <td colspan=\"2\">GRAND TOTAL</td>\n")
        sb.append("      <td>रु ${String.format(Locale.US, "%.2f", data.totalGroupExpenses)}</td>\n")
        sb.append("      <td>रु ${String.format(Locale.US, "%.2f", data.totalGroupExpenses)}</td>\n")
        sb.append("      <td><span class=\"badge\" style=\"background-color:#fff; border:1px solid #aaa;\">Balanced 0.00</span></td>\n")
        sb.append("    </tr>\n")
        sb.append("  </tbody>\n")
        sb.append("</table>\n")

        sb.append("<h3>2. SETTLEMENT SUMMARY (WHO OWES WHOM)</h3>\n")
        if (data.pairwiseDebts.isEmpty()) {
            sb.append("<p style=\"color:green; font-weight:bold;\">All bills are settled! No payments pending.</p>\n")
        } else {
            sb.append("<table>\n")
            sb.append("  <thead>\n")
            sb.append("    <tr>\n")
            sb.append("      <th>Debtor (Gets to pay)</th>\n")
            sb.append("      <th>Arrow</th>\n")
            sb.append("      <th>Creditor (Receives payment)</th>\n")
            sb.append("      <th>Amount to Settle (NPR)</th>\n")
            sb.append("    </tr>\n")
            sb.append("  </thead>\n")
            sb.append("  <tbody>\n")
            data.pairwiseDebts.forEach { debt ->
                sb.append("    <tr>\n")
                sb.append("      <td>${debt.first}</td>\n")
                sb.append("      <td style=\"text-align:center;\">&rArr;</td>\n")
                sb.append("      <td class=\"accent\">${debt.second}</td>\n")
                sb.append("      <td class=\"red\">रु ${String.format(Locale.US, "%.2f", debt.third)}</td>\n")
                sb.append("    </tr>\n")
            }
            sb.append("  </tbody>\n")
            sb.append("</table>\n")
        }

        sb.append("<h3>3. COMPLETE GROUP TRANSACTION HISTORY</h3>\n")
        if (data.filteredTxs.isEmpty()) {
            sb.append("<p>No transactions registered for the selected filter period.</p>\n")
        } else {
            sb.append("<table>\n")
            sb.append("  <thead>\n")
            sb.append("    <tr>\n")
            sb.append("      <th>Date</th>\n")
            sb.append("      <th>Payer</th>\n")
            sb.append("      <th>Transaction / Description</th>\n")
            sb.append("      <th>Shared With Splitted Members List</th>\n")
            sb.append("      <th>Total Spent Amount</th>\n")
            sb.append("      <th>Individual Split Share Value (NPR)</th>\n")
            sb.append("    </tr>\n")
            sb.append("  </thead>\n")
            sb.append("  <tbody>\n")
            data.filteredTxs.forEach { tx ->
                val payerName = data.membersList.find { it.id == tx.paidById }?.name ?: "Unknown"
                val splits = tx.splitWithIds.split(",")
                    .filter { it.isNotEmpty() }
                    .mapNotNull { it.toIntOrNull() }
                    .map { id -> data.membersList.find { it.id == id }?.name ?: "ID-$id" }
                    .joinToString(", ")
                val numSplits = maxOf(1, tx.splitWithIds.split(",").filter { it.isNotEmpty() }.size)
                val indShare = tx.amount / numSplits

                sb.append("    <tr>\n")
                sb.append("      <td>${tx.date}</td>\n")
                sb.append("      <td>${payerName}</td>\n")
                sb.append("      <td class=\"accent\">${tx.name}</td>\n")
                sb.append("      <td style=\"font-size:12px; color:#555;\">${splits}</td>\n")
                sb.append("      <td style=\"font-weight:bold;\">रु ${String.format(Locale.US, "%.2f", tx.amount)}</td>\n")
                sb.append("      <td>रु ${String.format(Locale.US, "%.2f", indShare)}</td>\n")
                sb.append("    </tr>\n")
            }
            sb.append("  </tbody>\n")
            sb.append("</table>\n")
        }

        sb.append("<hr>\n")
        sb.append("<p style=\"font-size:11px; text-align:center; color:gray;\">This is an offline system generated group statement from Artha Mantralaya, crafted by <b>Shri Bibash Lamichhane</b>.</p>\n")
        sb.append("</body>\n")
        sb.append("</html>\n")

        FileOutputStream(file).use { out ->
            out.write(sb.toString().toByteArray(Charsets.UTF_8))
        }
        return file
    }

    // PDF format exporter (multi-page robust PDF reporter)
    fun exportToPdf(context: Context, data: GroupReportData): File {
        val file = createReportFile(context, "group_report", "pdf")
        val document = PdfDocument()

        val pageWidth = 595
        val pageHeight = 842
        var pageCount = 1

        var page = document.startPage(PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageCount).create())
        var canvas = page.canvas

        val paint = Paint().apply { isAntiAlias = true }
        var currentY = 40f

        // Helper function to print header on any page
        fun drawPageHeader(pageNum: Int) {
            paint.color = Color.parseColor("#0F5132") // Emerald Forest
            canvas.drawRect(0f, 0f, pageWidth.toFloat(), 130f, paint)

            paint.color = Color.WHITE
            paint.textSize = 18f
            paint.isFakeBoldText = true
            canvas.drawText("ARTHA SPLITTER REPORT", 30f, 45f, paint)

            paint.textSize = 10f
            paint.isFakeBoldText = false
            canvas.drawText("Group Name: ${data.groupName}   |   Period: ${data.reportPeriod}", 30f, 75f, paint)
            canvas.drawText("Total Members: ${data.membersList.size}   |   Generated: ${SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(Date())}", 30f, 95f, paint)
            canvas.drawText("Page $pageNum", (pageWidth - 80).toFloat(), 45f, paint)

            // Draw line to split header
            paint.color = Color.WHITE
            canvas.drawLine(30f, 112f, (pageWidth - 30).toFloat(), 112f, paint)
        }

        // Helper function to check page limit and overflow
        fun verifyYOffsetAndLazyNewPage(neededHeight: Float) {
            if (currentY + neededHeight > pageHeight - 60f) {
                document.finishPage(page)
                pageCount++
                page = document.startPage(PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageCount).create())
                canvas = page.canvas
                currentY = 160f
                drawPageHeader(pageCount)
            }
        }

        // Page 1 Header Trigger
        drawPageHeader(1)
        currentY = 160f

        // Developer Credit Card inside first page
        verifyYOffsetAndLazyNewPage(65f)
        paint.color = Color.parseColor("#F4F8F6")
        canvas.drawRect(30f, currentY, (pageWidth - 30).toFloat(), currentY + 50f, paint)

        paint.color = Color.parseColor("#0F5132")
        paint.textSize = 15f
        paint.isFakeBoldText = true
        canvas.drawText("Developer: Shri Bibash Lamichhane", 45f, currentY + 31f, paint)
        currentY += 65f

        // 1. Group Summary totals Block
        verifyYOffsetAndLazyNewPage(50f)
        paint.color = Color.parseColor("#0F5132")
        paint.textSize = 12f
        paint.isFakeBoldText = true
        canvas.drawText("1. GROUP MEMBER EXPENSE TOTALS", 30f, currentY, paint)
        currentY += 15f

        // Table Header
        verifyYOffsetAndLazyNewPage(30f)
        paint.color = Color.parseColor("#E4E7EB")
        canvas.drawRect(30f, currentY, (pageWidth - 30).toFloat(), currentY + 20f, paint)
        paint.color = Color.BLACK
        paint.textSize = 9f
        paint.isFakeBoldText = true
        canvas.drawText("Member Name", 40f, currentY + 14f, paint)
        canvas.drawText("Total Paid (NPR)", 210f, currentY + 14f, paint)
        canvas.drawText("Share Owed (NPR)", 340f, currentY + 14f, paint)
        canvas.drawText("Net Balance", 470f, currentY + 14f, paint)
        currentY += 26f

        // Rows
        paint.isFakeBoldText = false
        data.membersList.forEach { m ->
            verifyYOffsetAndLazyNewPage(18f)
            val paid = data.memberPaidMap[m.id] ?: 0.0
            val owed = data.memberOwedMap[m.id] ?: 0.0
            val net = paid - owed

            // Underline row
            paint.color = Color.parseColor("#F4F4F4")
            canvas.drawLine(30f, currentY + 12f, (pageWidth - 30).toFloat(), currentY + 12f, paint)

            paint.color = Color.BLACK
            canvas.drawText(m.name, 40f, currentY, paint)
            canvas.drawText("रु ${String.format(Locale.US, "%.2f", paid)}", 210f, currentY, paint)
            canvas.drawText("रु ${String.format(Locale.US, "%.2f", owed)}", 340f, currentY, paint)

            if (net >= -0.01) {
                paint.color = Color.parseColor("#1B5E20")
                canvas.drawText("+रु ${String.format(Locale.US, "%.2f", net)}", 470f, currentY, paint)
            } else {
                paint.color = Color.parseColor("#B71C1C")
                canvas.drawText("-रु ${String.format(Locale.US, "%.2f", -net)}", 470f, currentY, paint)
            }
            currentY += 18f
        }

        // Grand Total row
        verifyYOffsetAndLazyNewPage(24f)
        paint.color = Color.parseColor("#E4E7EB")
        canvas.drawRect(30f, currentY, (pageWidth - 30).toFloat(), currentY + 20f, paint)
        paint.color = Color.BLACK
        paint.isFakeBoldText = true
        canvas.drawText("GRAND TOTAL", 40f, currentY + 14f, paint)
        canvas.drawText("रु ${String.format(Locale.US, "%.2f", data.totalGroupExpenses)}", 210f, currentY + 14f, paint)
        canvas.drawText("रु ${String.format(Locale.US, "%.2f", data.totalGroupExpenses)}", 340f, currentY + 14f, paint)
        canvas.drawText("Balanced", 470f, currentY + 14f, paint)
        currentY += 40f

        // 2. Settlement summary of who owes whom
        verifyYOffsetAndLazyNewPage(40f)
        paint.color = Color.parseColor("#0F5132")
        paint.textSize = 12f
        paint.isFakeBoldText = true
        canvas.drawText("2. SETTLEMENT SUMMARY (WHO OWES WHOM)", 30f, currentY, paint)
        currentY += 18f

        if (data.pairwiseDebts.isEmpty()) {
            verifyYOffsetAndLazyNewPage(20f)
            paint.color = Color.parseColor("#1B5E20")
            paint.textSize = 10f
            paint.isFakeBoldText = false
            canvas.drawText("All group transactions are fully resolved! No settlements required.", 40f, currentY, paint)
            currentY += 30f
        } else {
            verifyYOffsetAndLazyNewPage(24f)
            paint.color = Color.parseColor("#E4E7EB")
            canvas.drawRect(30f, currentY, (pageWidth - 30).toFloat(), currentY + 20f, paint)
            paint.color = Color.BLACK
            paint.textSize = 9f
            paint.isFakeBoldText = true
            canvas.drawText("Debtor (Who Pays)", 40f, currentY + 14f, paint)
            canvas.drawText("Creditor (Who Receives)", 270f, currentY + 14f, paint)
            canvas.drawText("Amount to Pay (NPR)", 450f, currentY + 14f, paint)
            currentY += 26f

            paint.isFakeBoldText = false
            data.pairwiseDebts.forEach { debt ->
                verifyYOffsetAndLazyNewPage(18f)
                paint.color = Color.parseColor("#F4F4F4")
                canvas.drawLine(30f, currentY + 12f, (pageWidth - 30).toFloat(), currentY + 12f, paint)

                paint.color = Color.BLACK
                canvas.drawText(debt.first, 40f, currentY, paint)
                canvas.drawText(debt.second, 270f, currentY, paint)
                paint.color = Color.parseColor("#B71C1C")
                canvas.drawText("रु ${String.format(Locale.US, "%.2f", debt.third)}", 450f, currentY, paint)
                currentY += 18f
            }
            currentY += 25f
        }

        // 3. Complete Transactions History List
        verifyYOffsetAndLazyNewPage(40f)
        paint.color = Color.parseColor("#0F5132")
        paint.textSize = 12f
        paint.isFakeBoldText = true
        canvas.drawText("3. GROUP TRANSACTION HISTORY LOGS", 30f, currentY, paint)
        currentY += 18f

        if (data.filteredTxs.isEmpty()) {
            verifyYOffsetAndLazyNewPage(20f)
            paint.color = Color.GRAY
            paint.textSize = 10f
            paint.isFakeBoldText = false
            canvas.drawText("No Transactions occurred during the selected period.", 40f, currentY, paint)
            currentY += 30f
        } else {
            verifyYOffsetAndLazyNewPage(24f)
            paint.color = Color.parseColor("#E4E7EB")
            canvas.drawRect(30f, currentY, (pageWidth - 30).toFloat(), currentY + 20f, paint)
            paint.color = Color.BLACK
            paint.textSize = 9f
            paint.isFakeBoldText = true
            canvas.drawText("Date", 40f, currentY + 14f, paint)
            canvas.drawText("Payer", 115f, currentY + 14f, paint)
            canvas.drawText("Transaction Description", 195f, currentY + 14f, paint)
            canvas.drawText("Total Amount", 415f, currentY + 14f, paint)
            canvas.drawText("Your Split", 500f, currentY + 14f, paint)
            currentY += 26f

            paint.textSize = 8.5f
            paint.isFakeBoldText = false
            data.filteredTxs.forEach { tx ->
                verifyYOffsetAndLazyNewPage(32f)
                paint.color = Color.parseColor("#FAFAFA")
                canvas.drawRect(30f, currentY - 12f, (pageWidth - 30).toFloat(), currentY + 18f, paint)

                paint.color = Color.BLACK
                canvas.drawText(tx.date, 35f, currentY + 4f, paint)

                val payerName = data.membersList.find { it.id == tx.paidById }?.name ?: "Unknown"
                canvas.drawText(payerName, 115f, currentY + 4f, paint)

                val txName = if (tx.name.length > 36) tx.name.substring(0, 33) + "..." else tx.name
                paint.isFakeBoldText = true
                canvas.drawText(txName, 195f, currentY, paint)
                paint.isFakeBoldText = false

                val splitNames = tx.splitWithIds.split(",")
                    .filter { it.isNotEmpty() }
                    .mapNotNull { it.toIntOrNull() }
                    .map { id -> data.membersList.find { it.id == id }?.name ?: "ID-$id" }
                    .joinToString(", ")
                val splitText = "Split with: $splitNames"
                val truncatedSplitText = if (splitText.length > 45) splitText.substring(0, 42) + "..." else splitText
                canvas.drawText(truncatedSplitText, 195f, currentY + 10f, paint)

                canvas.drawText("रु ${String.format(Locale.US, "%.2f", tx.amount)}", 415f, currentY + 4f, paint)

                val splitCount = maxOf(1, tx.splitWithIds.split(",").filter { it.isNotEmpty() }.size)
                val indSplit = tx.amount / splitCount
                canvas.drawText("रु ${String.format(Locale.US, "%.2f", indSplit)}", 500f, currentY + 4f, paint)

                paint.color = Color.parseColor("#E4E7EB")
                canvas.drawLine(30f, currentY + 18f, (pageWidth - 30).toFloat(), currentY + 18f, paint)

                currentY += 32f
            }
        }

        // Drawer signature footer on active page
        verifyYOffsetAndLazyNewPage(40f)
        paint.color = Color.GRAY
        paint.textSize = 8f
        paint.isFakeBoldText = false
        canvas.drawText("Generated by Artha Mantralaya offline application. Created by Developer: Shri Bibash Lamichhane.", 35f, (pageHeight - 25).toFloat(), paint)

        document.finishPage(page)
        document.writeTo(FileOutputStream(file))
        document.close()
        return file
    }

    private fun escapeCsv(str: String): String {
        return str.replace("\"", "\"\"")
    }
}
