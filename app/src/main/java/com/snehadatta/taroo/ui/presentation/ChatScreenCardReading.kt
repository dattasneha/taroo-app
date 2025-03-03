package com.snehadatta.taroo.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snehadatta.taroo.data.local.entity.Message
import com.snehadatta.taroo.ui.theme.LightBrown
import com.snehadatta.taroo.ui.theme.MediumBrown
import com.snehadatta.taroo.ui.theme.bodyFontFamily
import com.snehadatta.taroo.util.Resource
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun ChatScreenCardReading(
    modifier: Modifier,
    viewModel: TarotViewModel) {

    Column(
        modifier = modifier
    ) {
        val chatHistoryState by viewModel.chatHistoryList.collectAsStateWithLifecycle()

        when(chatHistoryState) {
            is Resource.Loading -> {
                CircularProgressIndicator()
            }
            is Resource.Success -> {
                viewModel.clearMessageList()
                chatHistoryState.data?.forEach { it.messageList.forEach { it ->
                    viewModel.updateMessageList(Message(message = it.message, role = it.role))
                }}
            }
            is Resource.Error -> {
                val errorMessage = chatHistoryState.message ?: "An unknown error occurred"
//                Text(text = "Error: $errorMessage", color = Color.Red)
            }
        }

        MessageList(modifier = Modifier.weight(1f),viewModel.messageList)

        MessageInput(
            initialQuestion = if (viewModel.askInitialQuestion && viewModel.cardNameList.isNotEmpty()) {
                viewModel.askInitialQuestion = false
                viewModel.initialQuestion.value +
                        ". Received cards are" +
                        viewModel.selectedCards.map { it.name }
            } else "",
            onMessageSend = {
                viewModel.getAiResponseCardReading(it)
            }
        )
    }
}

@Composable
fun MessageList(modifier: Modifier,messageList: List<Message>) {
    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ) {
        items(messageList.reversed()) {
            MessageRow(it)
        }
    }
}

@Composable
fun MessageRow(message: Message) {
    var isModel = message.role == Role.MODEL.role
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .align(
                        if (isModel) Alignment.BottomStart else Alignment.BottomEnd
                    )
                    .padding(
                        start = if (isModel) 8.dp else 60.dp,
                        end = if (isModel) 60.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(color = if (isModel) LightBrown else MediumBrown)
                    .padding(16.dp)
            ) {
                MarkdownText(
                    markdown = message.message,
                    color = Color.White,
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontFamily = bodyFontFamily
                    )
                )
            }
        }
    }

}

@Composable
fun MessageInput(initialQuestion: String, onMessageSend: (String) -> Unit) {
    var message by remember { mutableStateOf(initialQuestion) }
    Row (
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        OutlinedTextField(
            modifier = Modifier
                .weight(1f),
            value = message,
            onValueChange = { message = it },
            label = {
                Text(text = "Enter your question here")
            }
        )
        IconButton(onClick = {
            onMessageSend(message)
            message = ""
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}