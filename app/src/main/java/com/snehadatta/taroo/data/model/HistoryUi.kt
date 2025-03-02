package com.snehadatta.taroo.data.model

import com.snehadatta.taroo.data.local.entity.Message

data class HistoryUi(
    val historyId: Long,
    val cardList: List<Card>,
    val messageList: List<Message>,
)