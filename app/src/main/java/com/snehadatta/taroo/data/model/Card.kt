package com.snehadatta.taroo.data.model


import com.google.gson.annotations.SerializedName

data class Card(
    @SerializedName("desc")
    val desc: String,
    @SerializedName("meaning_rev")
    val meaningRev: String,
    @SerializedName("meaning_up")
    val meaningUp: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("name_short")
    val nameShort: String,
    @SerializedName("suit")
    val suit: String?,
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: String,
    @SerializedName("value_int")
    val valueInt: Int
)