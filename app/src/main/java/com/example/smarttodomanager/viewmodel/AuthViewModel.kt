package com.example.smarttodomanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttodomanager.data.SessionManager
import com.example.smarttodomanager.data.entity.User
import com.example.smarttodomanager.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val user = userRepository.loginUser(email, password)
            if (user != null) {
                sessionManager.saveUserId(user.userId)
                _authState.value = AuthState.Success(user)
            } else {
                _authState.value = AuthState.Error("Invalid email or password")
            }
        }
    }

    fun signup(username: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val existingUser = userRepository.getUserByEmail(email)
            if (existingUser != null) {
                _authState.value = AuthState.Error("Email already in use")
                return@launch
            }
            
            val newUser = User(username = username, email = email, password = password)
            val userId = userRepository.insertUser(newUser)
            sessionManager.saveUserId(userId.toInt())
            _authState.value = AuthState.Success(newUser.copy(userId = userId.toInt()))
        }
    }
    
    fun logout() {
        sessionManager.clearSession()
        _authState.value = AuthState.Idle
    }
    
    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}
