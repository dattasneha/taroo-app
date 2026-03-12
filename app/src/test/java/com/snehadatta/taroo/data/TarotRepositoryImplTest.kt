package com.snehadatta.taroo.data

import com.snehadatta.taroo.data.local.TarotDao
import com.snehadatta.taroo.data.local.entity.History
import com.snehadatta.taroo.network.TarotApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
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
}