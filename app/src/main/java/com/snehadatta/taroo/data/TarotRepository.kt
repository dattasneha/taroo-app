package com.snehadatta.taroo.data

import com.snehadatta.taroo.data.local.entity.History
import com.snehadatta.taroo.data.local.entity.Message
import com.snehadatta.taroo.data.model.GetAllCardsResponse
import com.snehadatta.taroo.data.model.HistoryUi
import com.snehadatta.taroo.util.Resource
import kotlinx.coroutines.flow.Flow

interface TarotRepository {
    fun getAllCards(): Flow<Resource<GetAllCardsResponse>>

    suspend fun insertNewChatHistory(history: History): Long
    suspend fun insertMessageIntoChatHistory(message: Message, historyId: Long)
    suspend fun getAllChatsHistory():List<HistoryUi>
    suspend fun insertMessage(message: Message): Long
    suspend fun updateMessageIdList(historyId: Long, messageIdList: List<Long>)
}