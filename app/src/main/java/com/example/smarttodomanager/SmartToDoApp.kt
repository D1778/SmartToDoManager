package com.example.smarttodomanager

import android.app.Application
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.ExistingPeriodicWorkPolicy
import com.example.smarttodomanager.data.worker.TaskReminderWorker
import java.util.concurrent.TimeUnit

class SmartToDoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        val reminderRequest = PeriodicWorkRequestBuilder<TaskReminderWorker>(1, TimeUnit.DAYS)
            .build()
            
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_task_reminder",
            ExistingPeriodicWorkPolicy.KEEP,
            reminderRequest
        )
    }
}
