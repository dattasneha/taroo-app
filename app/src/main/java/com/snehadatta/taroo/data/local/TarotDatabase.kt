package com.snehadatta.taroo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.snehadatta.taroo.data.local.entity.History

@Database(entities = [History::class], version = 1)
abstract class TarotDatabase: RoomDatabase(){
    abstract fun tarotDao():TarotDao
}