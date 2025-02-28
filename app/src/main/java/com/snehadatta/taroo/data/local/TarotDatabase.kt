package com.snehadatta.taroo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.snehadatta.taroo.data.local.entity.History

@Database(entities = [History::class], version = 1)
@TypeConverters(Converters::class)
abstract class TarotDatabase: RoomDatabase(){
    abstract fun tarotDao():TarotDao
}