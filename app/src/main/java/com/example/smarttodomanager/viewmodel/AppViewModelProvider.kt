package com.example.smarttodomanager.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.smarttodomanager.SmartToDoApp
import com.example.smarttodomanager.data.SessionManager
import com.example.smarttodomanager.data.database.AppDatabase
import com.example.smarttodomanager.data.repository.TaskRepository
import com.example.smarttodomanager.data.repository.UserRepository

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SmartToDoApp)
            val db = AppDatabase.getDatabase(app)
            val sessionManager = SessionManager(app)
            AuthViewModel(UserRepository(db.userDao()), sessionManager)
        }
        initializer {
            val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SmartToDoApp)
            val db = AppDatabase.getDatabase(app)
            val sessionManager = SessionManager(app)
            // Handle case where user isn't logged in but ViewModel is requested?
            // User ID should be evaluated inside TaskViewModel from session manager
            TaskViewModel(TaskRepository(db.taskDao()), sessionManager)
        }
    }
}
