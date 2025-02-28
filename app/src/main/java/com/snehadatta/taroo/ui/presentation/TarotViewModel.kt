package com.snehadatta.taroo.ui.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import com.snehadatta.taroo.data.TarotRepositoryImpl
import com.snehadatta.taroo.data.model.GetAllCardsResponse
import com.snehadatta.taroo.data.model.Message
import com.snehadatta.taroo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TarotViewModel @Inject constructor(
    private val tarotRepositoryImpl: TarotRepositoryImpl
) : ViewModel(){
    private val _cardState = MutableStateFlow<Resource<GetAllCardsResponse>>(Resource.Loading())
    val  cardState:StateFlow<Resource<GetAllCardsResponse>> = _cardState.asStateFlow()

    private var _initialQuestion = mutableStateOf("")
    val initialQuestion = _initialQuestion

    fun updateInitialQuestion(newData: String) {
        _initialQuestion.value = newData
    }

    private var _cardList = mutableListOf<String>()
    val cardList: List<String> = _cardList

    fun updateCardList(data: String) {
        _cardList.add(data)
    }

    val messageList by lazy {
        mutableStateListOf<Message>()
    }

    fun getAllCards() {
        viewModelScope.launch {
            tarotRepositoryImpl.getAllCards().collect { result ->
                _cardState.value = result
            }

        }
    }

    val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-2.0-pro-exp-02-05",
        apiKey = com.snehadatta.taroo.BuildConfig.API_KEY
    )
    fun getAiResponse(message: String) {
        viewModelScope.launch {
            try {
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role) { text(it.message) }
                    }.toList()
                )
                messageList.add(Message(message, "user"))
                messageList.add(Message("Typing...", "model"))
                val response = chat.sendMessage(message)
                messageList.removeLast()
                messageList.add(Message(response.text.toString(), "model"))
            } catch (e: Exception) {
                messageList.removeLast()
                messageList.add(Message("Error : "+e.message.toString(), "model"))
            }
        }
    }
}