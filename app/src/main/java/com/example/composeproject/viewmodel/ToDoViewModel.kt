package com.example.composeproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.composeproject.ReminderWorker
import com.example.composeproject.database.TaskDatabase
import com.example.composeproject.database.TaskEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ToDoViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao = TaskDatabase.getDatabase(application).taskDao()
    private val workManager = WorkManager.getInstance(application)

    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks = _tasks.asStateFlow() // Expose as StateFlow

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory = _selectedCategory.asStateFlow()

    fun setCategoryFilter(category: String) {
        _selectedCategory.value = category
    }

//    val filteredTasks: StateFlow<List<TaskEntity>> = _selectedCategory.flatMapLatest { category ->
//        if (category == "All") tasks else tasks.map { it.filter { task -> task.category == category } }
//    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
//
    init {
        viewModelScope.launch {
            taskDao.getAllTasks().collect { fetchedTasks ->
                _tasks.value = fetchedTasks
            }
        }
    }

    fun addTask(title: String, description: String, date: String) {
        viewModelScope.launch {
            taskDao.insertTask(TaskEntity(title = title, description = description, date = date))
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            taskDao.editTask(task)
        }
    }

    fun removeTask(task: TaskEntity) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }

    fun scheduleTaskReminder(taskTitle: String, delayInMinutes: Long) {
        val inputData = workDataOf("TASK_TITLE" to taskTitle)

        val reminderRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delayInMinutes, TimeUnit.MINUTES)
            .setInputData(inputData)
            .build()

        workManager.enqueue(reminderRequest)
    }
}
