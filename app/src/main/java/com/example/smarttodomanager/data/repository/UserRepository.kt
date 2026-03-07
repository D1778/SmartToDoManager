package com.example.smarttodomanager.data.repository

import com.example.smarttodomanager.data.dao.UserDao
import com.example.smarttodomanager.data.entity.User

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.loginUser(email, password)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
}
