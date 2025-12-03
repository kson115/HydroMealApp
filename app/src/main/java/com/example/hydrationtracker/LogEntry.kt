package com.example.hydrationtracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logs")
data class LogEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val type: String,
    val amount: Int,
    val timestamp: Long = System.currentTimeMillis()
)