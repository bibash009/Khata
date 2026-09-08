package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1,
    val name: String = "User",
    val photoUri: String? = null,
    val selectedLanguage: String = "en", // "en" (English) or "ne" (Nepali / नेपाली)
    val isDarkMode: Boolean = false
)

@Entity(tableName = "monthly_budget")
data class MonthlyBudget(
    @PrimaryKey val monthKey: String, // format: "YYYY-MM" (e.g., "2026-06")
    val amount: Double
)

@Entity(tableName = "personal_expenses")
data class PersonalExpense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String? = null,
    val amount: Double,
    val category: String, // Food, Transport, Education, Rent, Shopping, Entertainment, Health, Utilities, Others
    val date: String, // "YYYY-MM-DD"
    val time: String, // "HH:mm"
    val notes: String? = null
)

@Entity(tableName = "group_members")
data class GroupMember(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val photoUri: String? = null,
    val phone: String? = null,
    val notes: String? = null
)

@Entity(tableName = "group_transactions")
data class GroupTransaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val amount: Double,
    val date: String, // "YYYY-MM-DD"
    val time: String, // "HH:mm"
    val paidById: Int, // Member ID
    val splitWithIds: String, // Comma-separated member ID strings: e.g., "1,2,3"
    val notes: String? = null
)

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getProfileFlow(): Flow<UserProfile?>

    @Query("SELECT * FROM user_profile WHERE id = 1")
    suspend fun getProfileDirect(): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: UserProfile)
}

@Dao
interface MonthlyBudgetDao {
    @Query("SELECT * FROM monthly_budget WHERE monthKey = :monthKey")
    suspend fun getBudgetForMonth(monthKey: String): MonthlyBudget?

    @Query("SELECT * FROM monthly_budget")
    fun getAllBudgetsFlow(): Flow<List<MonthlyBudget>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: MonthlyBudget)
}

@Dao
interface PersonalExpenseDao {
    @Query("SELECT * FROM personal_expenses ORDER BY date DESC, time DESC")
    fun getAllExpensesFlow(): Flow<List<PersonalExpense>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: PersonalExpense)

    @Update
    suspend fun updateExpense(expense: PersonalExpense)

    @Delete
    suspend fun deleteExpense(expense: PersonalExpense)

    @Query("DELETE FROM personal_expenses")
    suspend fun deleteAllExpenses()
}

@Dao
interface GroupMemberDao {
    @Query("SELECT * FROM group_members ORDER BY id ASC")
    fun getAllMembersFlow(): Flow<List<GroupMember>>

    @Query("SELECT * FROM group_members WHERE id = :id")
    suspend fun getMemberById(id: Int): GroupMember?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: GroupMember)

    @Update
    suspend fun updateMember(member: GroupMember)

    @Delete
    suspend fun deleteMember(member: GroupMember)

    @Query("DELETE FROM group_members")
    suspend fun deleteAllMembers()
}

@Dao
interface GroupTransactionDao {
    @Query("SELECT * FROM group_transactions ORDER BY date DESC, time DESC")
    fun getAllTransactionsFlow(): Flow<List<GroupTransaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: GroupTransaction)

    @Update
    suspend fun updateTransaction(transaction: GroupTransaction)

    @Delete
    suspend fun deleteTransaction(transaction: GroupTransaction)

    @Query("DELETE FROM group_transactions")
    suspend fun deleteAllTransactions()
}

@Database(
    entities = [
        UserProfile::class,
        MonthlyBudget::class,
        PersonalExpense::class,
        GroupMember::class,
        GroupTransaction::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun monthlyBudgetDao(): MonthlyBudgetDao
    abstract fun personalExpenseDao(): PersonalExpenseDao
    abstract fun groupMemberDao(): GroupMemberDao
    abstract fun groupTransactionDao(): GroupTransactionDao

    companion object {
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        fun getDatabase(context: android.content.Context): ExpenseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                    "artha_mantralaya_db"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
