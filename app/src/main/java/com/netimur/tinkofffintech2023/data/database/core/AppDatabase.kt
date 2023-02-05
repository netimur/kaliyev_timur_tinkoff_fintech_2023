package com.netimur.tinkofffintech2023.data.database.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.netimur.tinkofffintech2023.data.database.daos.FilmCardRepresentationDao
import com.netimur.tinkofffintech2023.data.database.typeconverters.CountryConverter
import com.netimur.tinkofffintech2023.data.database.typeconverters.GenreConverter
import com.netimur.tinkofffintech2023.data.model.FilmCardRepresentation

@Database(entities = [FilmCardRepresentation::class], version = 2, exportSchema = false)
@TypeConverters(GenreConverter::class, CountryConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmCardRepresentationDao(): FilmCardRepresentationDao
}
