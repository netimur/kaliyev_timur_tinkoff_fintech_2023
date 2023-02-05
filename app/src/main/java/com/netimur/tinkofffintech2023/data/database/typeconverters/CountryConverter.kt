package com.netimur.tinkofffintech2023.data.database.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import com.netimur.tinkofffintech2023.data.model.Country
import java.lang.reflect.Type


class CountryConverter {
    @TypeConverter
    fun stringToList(value: String): List<Country> {
        val listType: Type = object : TypeToken<List<Country>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun listToString(list: List<Country>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
