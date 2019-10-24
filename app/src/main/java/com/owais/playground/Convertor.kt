package com.owais.playground

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.owais.playground.news.model.Source

class Convertor {

    @TypeConverter
    fun fromSource(value: String?): Source? {
        return if (value == null) null else Gson().fromJson(value, Source::class.java)
    }

    @TypeConverter
    fun sourcetoString(source: Source?): String? {
        if (source != null) {
            Gson().toJson(source)
        }
        return ""
    }

}