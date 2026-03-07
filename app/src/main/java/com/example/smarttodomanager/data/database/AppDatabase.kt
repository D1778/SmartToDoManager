package com.example.smarttodomanager.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smarttodomanager.data.entity.User
import com.example.smarttodomanager.data.entity.Task
import com.example.smarttodomanager.data.dao.UserDao
import com.example.smarttodomanager.data.dao.TaskDao

@Database(entities = [User::class, Task::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smart_todo_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
