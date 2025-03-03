package com.snehadatta.taroo.ui.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.snehadatta.taroo.data.TarotRepositoryImpl
import com.snehadatta.taroo.data.local.entity.History
import com.snehadatta.taroo.data.local.entity.Message
import com.snehadatta.taroo.data.model.Card
import com.snehadatta.taroo.data.model.GetAllCardsResponse
import com.snehadatta.taroo.data.model.HistoryUi
import com.snehadatta.taroo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Role(val role: String) {
    USER("user"),
    MODEL("model")
}
@HiltViewModel
class TarotViewModel @Inject constructor(
    private val tarotRepositoryImpl: TarotRepositoryImpl
) : ViewModel(){
    private val _cardState = MutableStateFlow<Resource<GetAllCardsResponse>>(Resource.Loading())
    val  cardState:StateFlow<Resource<GetAllCardsResponse>> = _cardState.asStateFlow()

    private val _chatHistoryList = MutableStateFlow<Resource<List<HistoryUi>>>(Resource.Loading())
    val chatHistoryList: StateFlow<Resource<List<HistoryUi>>> = _chatHistoryList.asStateFlow()

    private var _initialQuestion = mutableStateOf("")
    val initialQuestion = _initialQuestion

    private var _cardNameList = mutableListOf<String>()
    val cardNameList: List<String> = _cardNameList

    private var _selectedCards = mutableListOf<Card>()
    val selectedCards: List<Card> = _selectedCards

    private var _messageIdList = mutableListOf<Long>()
    val messageIdList : List<Long> = _messageIdList

    var historyId: Long? = null

    var askInitialQuestion = false

    fun updateInitialQuestion(newData: String) {
        _initialQuestion.value = newData
    }
    fun changeStateOfInitialQuestion(it: Boolean) {
        askInitialQuestion = it
    }
    fun updateCardList(data: String) {
        _cardNameList.add(data)
    }

    val messageList by lazy {
        mutableStateListOf<Message>()
    }

    fun updateMessageList(data: Message) {
        messageList.add(data)
    }

    fun clearMessageList() {
        messageList.clear()
    }

    fun updateSelectedCardList(data: Card) {
        _selectedCards.add(data)
    }

    fun getAllCards() {
        viewModelScope.launch {
            tarotRepositoryImpl.getAllCards().collect { result ->
                _cardState.value = result
            }
        }
    }

    fun getChatHistory() {
        viewModelScope.launch {
            try {
                val allChatsHistory = tarotRepositoryImpl.getAllChatsHistory()
                if (allChatsHistory.isNotEmpty()) {
                    _chatHistoryList.value = Resource.Success(allChatsHistory)
                } else {
                    _chatHistoryList.value = Resource.Error("No chat history found")
                }
            } catch (e: Exception) {
                _chatHistoryList.value = Resource.Error("Error: ${e.message}")
            }
        }
    }

    private val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-2.0-pro-exp-02-05",
        apiKey = com.snehadatta.taroo.BuildConfig.API_KEY
    )

    fun getAiResponseCardReading(message: String) {
        viewModelScope.launch {
            val userMessageId = tarotRepositoryImpl.insertMessage(Message(message = message, role = Role.USER.role))
            _messageIdList.add(userMessageId)
            var modelMessageId: Long = 0
            try {
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role) { text(it.message) }
                    }.toList()
                )
                messageList.add(Message(messageId = userMessageId, message = message, role = Role.USER.role))
                messageList.add(Message(message = "Typing...", role = Role.MODEL.role))

                val response = chat.sendMessage(message)

                messageList.removeLast()

                modelMessageId = tarotRepositoryImpl.insertMessage(Message(message = response.text.toString(), role = Role.MODEL.role))
                messageList.add(Message(messageId = modelMessageId, message = response.text.toString(), role = Role.MODEL.role))

            }catch (e: Exception) {
                messageList.removeLast()
                modelMessageId = tarotRepositoryImpl.insertMessage(Message(message ="Error : "+e.message.toString(), role = Role.MODEL.role))
                messageList.add(Message(modelMessageId,"Error : "+e.message.toString(), Role.MODEL.role))
            }
            _messageIdList.add(modelMessageId)
            if(historyId == null) {
                historyId = tarotRepositoryImpl.insertNewChatHistory(History(cardIdList = cardNameList, messageIdList = messageIdList))
            } else {
                tarotRepositoryImpl.updateMessageIdList(
                    messageIdList = messageIdList,
                    historyId = historyId!!
                )
            }
        }
    }

}

