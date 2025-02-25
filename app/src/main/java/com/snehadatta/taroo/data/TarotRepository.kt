package com.snehadatta.taroo.data

import com.snehadatta.taroo.data.model.GetAllCardsResponse
import com.snehadatta.taroo.util.Resource
import kotlinx.coroutines.flow.Flow

interface TarotRepository {
    fun getAllCards(): Flow<Resource<GetAllCardsResponse>>
}