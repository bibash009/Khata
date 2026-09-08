package com.example.ui

import com.example.data.UserProfile
import com.example.data.MonthlyBudget
import com.example.data.PersonalExpense
import com.example.data.GroupMember
import com.example.data.GroupTransaction
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BackupPayload(
    val profile: UserProfile,
    val budgets: List<MonthlyBudget>,
    val personalExpenses: List<PersonalExpense>,
    val groupMembers: List<GroupMember>,
    val groupTransactions: List<GroupTransaction>
)
