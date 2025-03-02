package com.snehadatta.taroo.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.snehadatta.taroo.util.GsonParser

@ProvidedTypeConverter
class Converters(
    private val jsonParser: GsonParser
) {
    @TypeConverter
    fun fromCardIdListJson(json: String): List<String> {
        return jsonParser.fromJson<ArrayList<String>>(
            json,
            object : TypeToken<ArrayList<String>>(){}.type
        )?: emptyList()
    }

    @TypeConverter
    fun toCardIdListJson(cards: List<String>): String {
        return jsonParser.toJson(
            cards,
            object : TypeToken<ArrayList<String>>(){}.type
        )?: "[]"
    }


    @TypeConverter
    fun fromMessageIdListJson(json: String): List<Long> {
        return jsonParser.fromJson<ArrayList<Long>>(
            json,
            object : TypeToken<ArrayList<Long>>(){}.type
        )?: emptyList()
    }

    @TypeConverter
    fun toMessageIdListJson(cards: List<Long>): String {
        return jsonParser.toJson(
            cards,
            object : TypeToken<ArrayList<Long>>(){}.type
        )?: "[]"
    }
}