package com.example.composeproject

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composeproject.viewmodel.ToDoViewModel

class FakeToDoViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoViewModel(Application()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
