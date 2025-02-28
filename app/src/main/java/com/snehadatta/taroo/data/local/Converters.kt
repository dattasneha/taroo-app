package com.snehadatta.taroo.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.snehadatta.taroo.data.model.Card
import com.snehadatta.taroo.data.model.Message
import com.snehadatta.taroo.util.GsonParser

@ProvidedTypeConverter
class Converters(
    private val jsonParser: GsonParser
) {
    @TypeConverter
    fun fromCardJson(json: String):List<Card> {
        return jsonParser.fromJson<ArrayList<Card>>(
            json,
            object : TypeToken<ArrayList<Card>>(){}.type
        )?: emptyList()
    }

    @TypeConverter
    fun toCardJson(cards:List<Card>):String {
        return jsonParser.toJson(
            cards,
            object : TypeToken<ArrayList<Card>>(){}.type
        )?: "[]"
    }

    @TypeConverter
    fun fromMessageJson(json: String):List<Message> {
        return jsonParser.fromJson<ArrayList<Message>>(
            json,
            object : TypeToken<ArrayList<Message>>(){}.type
        )?: emptyList()
    }

    @TypeConverter
    fun toMessageJson(messages:List<Message>):String {
        return jsonParser.toJson(
            messages,
            object : TypeToken<ArrayList<Message>>(){}.type
        )?: "[]"
    }
}