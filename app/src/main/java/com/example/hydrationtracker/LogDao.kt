package com.example.hydrationtracker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LogDao {
    @Insert
    suspend fun insertLog(log: LogEntry)

    @Query("SELECT * FROM logs WHERE date = :date ORDER BY timestamp DESC")
    fun getLogsByDate(date: String): Flow<List<LogEntry>>

    @Query("DELETE FROM logs WHERE id = :id")
    suspend fun deleteLog(id: Int)
}
