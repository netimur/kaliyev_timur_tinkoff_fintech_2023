package com.netimur.tinkofffintech2023.data.database.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.netimur.tinkofffintech2023.data.model.Genre
import java.lang.reflect.Type

class GenreConverter {
    @TypeConverter
    fun stringToList(value: String): List<Genre> {
        val listType: Type = object : TypeToken<List<Genre>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun listToString(list: List<Genre>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
