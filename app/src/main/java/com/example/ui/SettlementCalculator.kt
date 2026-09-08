package com.example.ui

import com.example.data.GroupMember
import com.example.data.GroupTransaction

data class DebtTransfer(
    val debtorId: Int,
    val debtorName: String,
    val creditorId: Int,
    val creditorName: String,
    val amount: Double
)

object SettlementCalculator {

    fun calculateBalances(
        members: List<GroupMember>,
        transactions: List<GroupTransaction>
    ): Map<Int, Double> {
        val balanceMap = members.associate { it.id to 0.0 }.toMutableMap()

        for (tx in transactions) {
            val splitIds = tx.splitWithIds.split(",")
                .filter { it.isNotEmpty() }
                .mapNotNull { it.toIntOrNull() }
                .filter { id -> members.any { m -> m.id == id } }

            if (splitIds.isEmpty()) continue
            val share = tx.amount / splitIds.size

            if (balanceMap.containsKey(tx.paidById)) {
                balanceMap[tx.paidById] = balanceMap[tx.paidById]!! + tx.amount
            }

            for (sid in splitIds) {
                if (balanceMap.containsKey(sid)) {
                    balanceMap[sid] = balanceMap[sid]!! - share
                }
            }
        }
        return balanceMap
    }

    fun calculateSettlements(
        members: List<GroupMember>,
        transactions: List<GroupTransaction>
    ): List<DebtTransfer> {
        val balanceMap = calculateBalances(members, transactions)

        val debtorsTemp = balanceMap.filter { it.value < -0.01 }.map { it.key to -it.value }.toMutableList()
        val creditorsTemp = balanceMap.filter { it.value > 0.01 }.map { it.key to it.value }.toMutableList()

        // Sort debtors descending (highest debt first) and creditors descending (highest credit first)
        debtorsTemp.sortByDescending { it.second }
        creditorsTemp.sortByDescending { it.second }

        val transfers = mutableListOf<DebtTransfer>()
        var dIndex = 0
        var cIndex = 0

        while (dIndex < debtorsTemp.size && cIndex < creditorsTemp.size) {
            val (debId, debAmt) = debtorsTemp[dIndex]
            val (credId, credAmt) = creditorsTemp[cIndex]

            val debtorName = members.find { it.id == debId }?.name ?: "Member $debId"
            val creditorName = members.find { it.id == credId }?.name ?: "Member $credId"

            val minTransfer = minOf(debAmt, credAmt)

            if (minTransfer > 0.01) {
                transfers.add(
                    DebtTransfer(
                        debtorId = debId,
                        debtorName = debtorName,
                        creditorId = credId,
                        creditorName = creditorName,
                        amount = minTransfer
                    )
                )
            }

            debtorsTemp[dIndex] = debId to (debAmt - minTransfer)
            creditorsTemp[cIndex] = credId to (credAmt - minTransfer)

            if (debtorsTemp[dIndex].second < 0.01) {
                dIndex++
            }
            if (creditorsTemp[cIndex].second < 0.01) {
                cIndex++
            }
        }

        return transfers
    }
}
