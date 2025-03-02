package com.snehadatta.taroo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.snehadatta.taroo.data.local.entity.History
import com.snehadatta.taroo.data.local.entity.Message
import com.snehadatta.taroo.data.model.Card

@Dao
interface TarotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTarotCards(tarotCards: List<Card>)

    @Query("SELECT * FROM card")
    suspend fun getAllTarotCards(): List<Card>

    @Query("SELECT * FROM card WHERE nameShort = :cardId")
    suspend fun getTarotCardById(cardId: String): Card

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewChatHistory(history: History):Long

    @Query("SELECT * FROM history ORDER BY historyId ASC")
    suspend fun getAllChatsHistory(): List<History>

    @Query("SELECT * FROM message WHERE messageId = :messageId")
    suspend fun getMessageById(messageId: Long): Message

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message): Long

    @Query("UPDATE history SET messageIdList = :messageIdList WHERE historyId = :historyId")
    suspend fun updateMessageIdList(historyId: Long, messageIdList: List<Long>)
}