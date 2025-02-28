package com.snehadatta.taroo.data.local

import android.service.autofill.FillEventHistory
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.snehadatta.taroo.data.local.entity.History

@Dao
interface TarotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: History)

    @Delete
    suspend fun delete(histories: List<History>)

    @Query("SELECT * FROM history ORDER BY id ASC")
    fun getHistory(): List<History>
}