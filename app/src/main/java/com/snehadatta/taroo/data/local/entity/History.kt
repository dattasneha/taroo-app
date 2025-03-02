package com.snehadatta.taroo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true)
    val historyId: Long = 0,
    val cardIdList: List<String>,
    val messageIdList: List<Long>,
)