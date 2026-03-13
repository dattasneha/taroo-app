package com.snehadatta.taroo.data

import com.google.gson.annotations.SerializedName
import com.snehadatta.taroo.data.local.TarotDao
import com.snehadatta.taroo.data.local.entity.History
import com.snehadatta.taroo.data.local.entity.Message
import com.snehadatta.taroo.data.model.Card
import com.snehadatta.taroo.data.model.GetAllCardsResponse
import com.snehadatta.taroo.data.model.HistoryUi
import com.snehadatta.taroo.network.TarotApi
import kotlinx.coroutines.test.runTest
import okhttp3.Response
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class TarotRepositoryImplTest {
    private lateinit var tarotApi: TarotApi
    private lateinit var tarotDao: TarotDao
    private lateinit var repository: TarotRepositoryImpl

    @Before
    fun setup(){
        tarotApi = mock(TarotApi::class.java)
        tarotDao = mock(TarotDao::class.java)
        repository = TarotRepositoryImpl(tarotApi,tarotDao)
    }


    @Test
    fun insertNewChatHistory_returnsId() = runTest {
        val history = History(
            historyId = 0,
            cardIdList = emptyList(),
            messageIdList = emptyList()
        )
        `when`(tarotDao.insertNewChatHistory(history)).thenReturn(1L)

        val result = repository.insertNewChatHistory(history)
        assertEquals(1L,result)
    }

    @Test
    fun insertMessageIntoChatHistory_insertsMessage_and_updatesHistory() = runTest {
        val historyId = 1L
        val message = Message(1,"Hello","admin")
        val history = listOf(
            History(
                historyId,
                listOf("a","b"),
                listOf(2L,3L)
            )
        )
        `when`(tarotDao.insertMessage(message)).thenReturn(5L)
        `when`(tarotDao.getAllChatsHistory()).thenReturn(history)

        repository.insertMessageIntoChatHistory(message,historyId)

        verify(tarotDao).insertMessage(message)
        verify(tarotDao).getAllChatsHistory()

        val expectedList = listOf(2L,3L,5L)
        verify(tarotDao).updateMessageIdList(historyId,expectedList)

    }
    @Test
    fun getAllChatsHistory_return_history() = runTest{
        val historyId = 1L
        val history = listOf(
            History(
                historyId,
                listOf("a","b"),
                listOf(2L,3L)
            )
        )
        `when`(tarotDao.getAllChatsHistory()).thenReturn(history)
        repository.getAllChatsHistory()
        verify(tarotDao).getAllChatsHistory()
    }
}