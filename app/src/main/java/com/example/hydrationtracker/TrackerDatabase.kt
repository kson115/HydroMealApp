package com.example.hydrationtracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LogEntry::class], version = 1)
abstract class TrackerDatabase : RoomDatabase() {
    abstract fun logDao(): LogDao

    companion object {
        @Volatile
        private var instance: TrackerDatabase? = null

        fun getInstance(context: Context): TrackerDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, TrackerDatabase::class.java, "tracker.db")
                    .build()
                    .also { instance = it }
            }
        }
    }
}