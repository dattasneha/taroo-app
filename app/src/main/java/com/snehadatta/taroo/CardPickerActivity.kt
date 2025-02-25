package com.snehadatta.taroo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snehadatta.taroo.ui.presentation.CardPickerScreen
import com.snehadatta.taroo.ui.presentation.TarotViewModel
import com.snehadatta.taroo.ui.theme.TarooTheme
import com.snehadatta.taroo.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardPickerActivity : ComponentActivity() {
    private val tarotViewModel: TarotViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tarotViewModel.getAllCards()

        enableEdgeToEdge()
        setContent {
            TarooTheme {
                val cardState by tarotViewModel.cardState.collectAsStateWithLifecycle()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when(cardState) {
                        is Resource.Loading -> {
                            CircularProgressIndicator()
                        }
                        is Resource.Success -> {
                            cardState.data?.let {
                                CardPickerScreen(Modifier.padding(innerPadding),R.drawable.ar03,
                                    it.cards)
                            }
                        }
                        is Resource.Error -> {
                            val errorMessage = cardState.message ?: "An unknown error occurred"
                            Text(text = "Error: $errorMessage", color = Color.Red)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TarooTheme {
        Greeting("Android")
    }
}