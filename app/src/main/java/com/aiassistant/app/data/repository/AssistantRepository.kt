package com.aiassistant.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Entity(tableName = "habits")
data class HabitEntity(
    val name: String,
    val description: String,
    val createdAt: Long = System.currentTimeMillis(),
    val userId: String = "default-user"
) {
    @androidx.room.PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Entity(tableName = "messages")
data class MessageEntity(
    val sender: String,
    val content: String,
    val direction: String,
    val createdAt: Long = System.currentTimeMillis()
) {
    @androidx.room.PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Dao
interface AssistantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Query("SELECT * FROM habits ORDER BY createdAt DESC")
    suspend fun getHabits(): List<HabitEntity>

    @Query("SELECT * FROM messages ORDER BY createdAt DESC")
    suspend fun getMessages(): List<MessageEntity>
}

@Database(
    entities = [HabitEntity::class, MessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun assistantDao(): AssistantDao
}
