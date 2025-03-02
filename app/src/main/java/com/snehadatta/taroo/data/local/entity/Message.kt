package com.snehadatta.taroo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class Message(
    @PrimaryKey(autoGenerate = true) val messageId: Long = 0,
    val message: String,
    val role: String
)