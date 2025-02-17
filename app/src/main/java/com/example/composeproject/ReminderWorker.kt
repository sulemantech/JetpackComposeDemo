package com.example.composeproject

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class ReminderWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val taskTitle = inputData.getString("TASK_TITLE") ?: "No Title"

        // Show notification when the task reminder is triggered
        NotificationHelper.showNotification(applicationContext, taskTitle)

        return Result.success()
    }
}
