package com.example.smarttodomanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val taskId: Int = 0,
    val userId: Int,
    val title: String,
    val description: String,
    val category: String,
    val priority: String,
    val dueDate: Long,
    val isCompleted: Boolean = false
)
