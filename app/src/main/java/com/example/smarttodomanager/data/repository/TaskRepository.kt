package com.example.smarttodomanager.data.repository

import com.example.smarttodomanager.data.dao.TaskDao
import com.example.smarttodomanager.data.entity.Task
import kotlinx.coroutines.flow.Flow

class  TaskRepository(private val taskDao: TaskDao) {
    suspend fun insertTask(task: Task) = taskDao.insertTask(task)
    
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    
    fun getTasksByUserId(userId: Int): Flow<List<Task>> = taskDao.getTasksByUserId(userId)
    
    fun getTasksByUserIdAndStatus(userId: Int, isCompleted: Boolean): Flow<List<Task>> = 
        taskDao.getTasksByUserIdAndStatus(userId, isCompleted)
        
    fun getTasksForToday(userId: Int, startOfDay: Long, endOfDay: Long): Flow<List<Task>> = 
        taskDao.getTasksForToday(userId, startOfDay, endOfDay)
        
    fun getTaskCountByStatus(userId: Int, isCompleted: Boolean): Flow<Int> = 
        taskDao.getTaskCountByStatus(userId, isCompleted)

    suspend fun getTaskById(taskId: Int): Task? = taskDao.getTaskById(taskId)
}
