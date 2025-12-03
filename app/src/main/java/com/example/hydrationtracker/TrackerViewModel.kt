package com.example.hydrationtracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.LocalDate

class TrackerViewModel(private val dao: LogDao) : ViewModel() {
    private val today = LocalDate.now().toString()
    val todayLogs = dao.getLogsByDate(today)

    fun addLog(type: String, amount: Int) {
        viewModelScope.launch {
            dao.insertLog(LogEntry(date = today, type = type, amount = amount))
        }
    }

    fun deleteLog(id: Int) {
        viewModelScope.launch {
            dao.deleteLog(id)
        }
    }
}

class TrackerViewModelFactory(private val dao: LogDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrackerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TrackerViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
