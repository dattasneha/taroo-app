package com.snehadatta.taroo.data.model


import com.google.gson.annotations.SerializedName

data class GetAllCardsResponse(
    @SerializedName("cards")
    val cards: List<Card>,
    @SerializedName("nhits")
    val nhits: Int
)