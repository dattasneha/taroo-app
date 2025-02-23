package com.snehadatta.taroo.network

import com.snehadatta.taroo.data.model.GetAllCardsResponse
import retrofit2.Response
import retrofit2.http.GET

interface TarotApi {
    @GET("cards")
    fun getAllCards():Response<GetAllCardsResponse>

    companion object {
        const val BASE_URL = "https://tarotapi.dev/api/v1/"
    }
}