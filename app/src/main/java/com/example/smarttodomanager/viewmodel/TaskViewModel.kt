package com.example.smarttodomanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttodomanager.data.SessionManager
import com.example.smarttodomanager.data.entity.Task
import com.example.smarttodomanager.data.repository.TaskRepository
import com.example.smarttodomanager.data.api.QuoteApiService
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TaskViewModel(
    private val taskRepository: TaskRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val userId = sessionManager.getUserId()
    
    private val _currentCategory = MutableStateFlow<String?>("All")
    val currentCategory: StateFlow<String?> = _currentCategory
    
    private val _dailyQuote = MutableStateFlow<String?>("Loading quote...")
    val dailyQuote: StateFlow<String?> = _dailyQuote.asStateFlow()

    init {
        fetchQuote()
    }

    private fun fetchQuote() {
        viewModelScope.launch {
            try {
                val service = QuoteApiService.create()
                val response = service.getRandomQuote()
                if (response.isNotEmpty()) {
                    _dailyQuote.value = "\"${response[0].q}\" - ${response[0].a}"
                }
            } catch (e: Exception) {
                _dailyQuote.value = "Stay productive and conquer the day!"
            }
        }
    }
    
    fun setCategory(category: String?) {
        _currentCategory.value = category
    }

    val allTasks: StateFlow<List<Task>> = taskRepository.getTasksByUserId(userId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredTasks: StateFlow<List<Task>> = _currentCategory.flatMapLatest { category ->
        taskRepository.getTasksByUserId(userId).map { tasks ->
            if (category == null || category == "All") tasks
            else tasks.filter { it.category == category }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val pendingTasks: StateFlow<List<Task>> = taskRepository.getTasksByUserIdAndStatus(userId, false)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        
    val completedTasks: StateFlow<List<Task>> = taskRepository.getTasksByUserIdAndStatus(userId, true)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTask(title: String, description: String, category: String, priority: String, dueDate: Long) {
        viewModelScope.launch {
            val task = Task(
                userId = userId,
                title = title,
                description = description,
                category = category,
                priority = priority,
                dueDate = dueDate
            )
            taskRepository.insertTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task.copy(isCompleted = !task.isCompleted))
        }
    }
}
