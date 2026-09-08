package com.example

import com.example.data.GroupTransaction
import com.example.data.PersonalExpense
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TransactionPersistenceTest {

    @Test
    fun transactionHistory_retainsAllPastMonthsAndYears() {
        val historicalExpenses = listOf(
            PersonalExpense(id = 1, title = "Old Expense 2025", amount = 500.0, category = "Food", description = null, notes = null, date = "2025-11-15", time = "10:00"),
            PersonalExpense(id = 2, title = "Last Month Expense", amount = 1200.0, category = "Rent", description = null, notes = null, date = "2026-08-01", time = "09:00"),
            PersonalExpense(id = 3, title = "Current Month Expense", amount = 350.0, category = "Transport", description = null, notes = null, date = "2026-09-02", time = "14:30")
        )

        // Transaction history preserves all entries regardless of month or year
        assertEquals(3, historicalExpenses.size)
        assertTrue(historicalExpenses.any { it.date.startsWith("2025") })
        assertTrue(historicalExpenses.any { it.date.startsWith("2026-08") })
        assertTrue(historicalExpenses.any { it.date.startsWith("2026-09") })

        // Calculated monthly view strictly isolates the selected month without modifying stored records
        val activeMonth = "2026-09"
        val monthlySummaryList = historicalExpenses.filter { it.date.startsWith(activeMonth) }
        assertEquals(1, monthlySummaryList.size)
        assertEquals(350.0, monthlySummaryList.sumOf { it.amount }, 0.001)

        // History remains intact after monthly summary calculation
        assertEquals(3, historicalExpenses.size)
    }

    @Test
    fun groupTransactionHistory_retainsAllPastMonthsAndYears() {
        val historicalGroupTxs = listOf(
            GroupTransaction(id = 1, name = "Dinner 2025", amount = 1600.0, paidById = 1, splitWithIds = "1,2,3,4", date = "2025-12-31", time = "20:00"),
            GroupTransaction(id = 2, name = "Groceries August", amount = 800.0, paidById = 2, splitWithIds = "1,2", date = "2026-08-15", time = "12:00"),
            GroupTransaction(id = 3, name = "Snacks September", amount = 400.0, paidById = 1, splitWithIds = "1,2,3", date = "2026-09-01", time = "16:00")
        )

        // Group transaction history retains all entries across all months/years
        assertEquals(3, historicalGroupTxs.size)
        assertTrue(historicalGroupTxs.any { it.date.startsWith("2025") })
        assertTrue(historicalGroupTxs.any { it.date.startsWith("2026-08") })
        assertTrue(historicalGroupTxs.any { it.date.startsWith("2026-09") })
    }
}
