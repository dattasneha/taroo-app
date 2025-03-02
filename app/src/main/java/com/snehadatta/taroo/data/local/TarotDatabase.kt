package com.snehadatta.taroo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.snehadatta.taroo.data.local.entity.History
import com.snehadatta.taroo.data.local.entity.Message
import com.snehadatta.taroo.data.model.Card

@Database(
    entities = [History::class, Message::class, Card::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class TarotDatabase: RoomDatabase(){
    abstract fun tarotDao():TarotDao
}