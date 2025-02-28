package com.snehadatta.taroo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.snehadatta.taroo.data.model.Card
import com.snehadatta.taroo.data.model.Message

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nameCard: List<Card>,
    val message: List<Message>,
)
