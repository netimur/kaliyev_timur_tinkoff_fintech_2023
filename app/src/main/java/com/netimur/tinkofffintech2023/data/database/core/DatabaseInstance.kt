package com.netimur.tinkofffintech2023.data.database.core

import android.content.Context
import androidx.room.Room

object DatabaseInstance {
    private lateinit var db: AppDatabase;
    fun getDb(context: Context): AppDatabase {
        db = Room.databaseBuilder(context, AppDatabase::class.java, "application-database")
            .build()
        return db
    }
}
