package com.snehadatta.taroo.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snehadatta.taroo.data.TarotRepositoryImpl
import com.snehadatta.taroo.data.model.GetAllCardsResponse
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

    fun getAllCards() {
        viewModelScope.launch {
            tarotRepositoryImpl.getAllCards().collect { result ->
                _cardState.value = result
            }

        }
    }
}