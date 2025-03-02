package com.snehadatta.taroo.data

import com.snehadatta.taroo.data.local.TarotDao
import com.snehadatta.taroo.data.local.entity.History
import com.snehadatta.taroo.data.local.entity.Message
import com.snehadatta.taroo.data.model.GetAllCardsResponse
import com.snehadatta.taroo.data.model.HistoryUi
import com.snehadatta.taroo.network.TarotApi
import com.snehadatta.taroo.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

import javax.inject.Inject

class TarotRepositoryImpl @Inject constructor(
    private val tarotApi: TarotApi,
    private val tarotDao: TarotDao
) : TarotRepository {
    override fun getAllCards(): Flow<Resource<GetAllCardsResponse>> = flow {
        emit(Resource.Loading())

        try {
            val localResponse = tarotDao.getAllTarotCards()
            if (localResponse.isNotEmpty()) {
                emit(
                    Resource.Success(
                        GetAllCardsResponse(
                            cards = localResponse,
                            nhits = localResponse.size
                        )
                    )
                )
            } else {
                val response = tarotApi.getAllCards()
                if(response.isSuccessful) {
                    tarotDao.insertTarotCards(response.body()?.cards ?: emptyList())
                    response.body()?.let {
                        emit(Resource.Success(it))
                    } ?: emit(Resource.Error("No response found"))
                }
                else {
                    emit(Resource.Error("API Error: ${response.code()}"))
                }
            }

        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "Oops, Something went wrong!"
            ))

        }catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, Check your internet connection."
            ))

        }

    }

    override suspend fun insertNewChatHistory(history: History): Long{
        return tarotDao.insertNewChatHistory(history)
    }

    override suspend fun insertMessageIntoChatHistory(message: Message, historyId: Long) {
        val messageId = tarotDao.insertMessage(message)
        val history = tarotDao.getAllChatsHistory().find { it.historyId == historyId }

        val newMessageIdList = history?.messageIdList?.toMutableList() ?: mutableListOf()
        newMessageIdList.add(messageId)

        tarotDao.updateMessageIdList(historyId, newMessageIdList)
    }

    override suspend fun getAllChatsHistory(): List<HistoryUi> {
        return tarotDao.getAllChatsHistory().map {
            HistoryUi(
                historyId = it.historyId,
                cardList = it.cardIdList.map { cardId ->
                    tarotDao.getTarotCardById(cardId)
                },
                messageList = it.messageIdList.map { messageId ->
                    tarotDao.getMessageById(messageId)
                }
            )
        }
    }

    override suspend fun insertMessage(message: Message): Long {
        return tarotDao.insertMessage(message)
    }

    override suspend fun updateMessageIdList(historyId: Long, messageIdList: List<Long>) {
        tarotDao.updateMessageIdList(historyId,messageIdList)
    }
}