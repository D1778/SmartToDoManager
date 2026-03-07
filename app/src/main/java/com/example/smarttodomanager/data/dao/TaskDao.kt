package com.example.smarttodomanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.smarttodomanager.data.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks WHERE userId = :userId ORDER BY dueDate ASC")
    fun getTasksByUserId(userId: Int): Flow<List<Task>>
    
    @Query("SELECT * FROM tasks WHERE userId = :userId AND isCompleted = :isCompleted ORDER BY dueDate ASC")
    fun getTasksByUserIdAndStatus(userId: Int, isCompleted: Boolean): Flow<List<Task>>
    
    @Query("SELECT * FROM tasks WHERE userId = :userId AND dueDate >= :startOfDay AND dueDate <= :endOfDay")
    fun getTasksForToday(userId: Int, startOfDay: Long, endOfDay: Long): Flow<List<Task>>
    
    @Query("SELECT COUNT(*) FROM tasks WHERE userId = :userId AND isCompleted = :isCompleted")
    fun getTaskCountByStatus(userId: Int, isCompleted: Boolean): Flow<Int>
}
