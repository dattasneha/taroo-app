package com.snehadatta.taroo

import android.os.Bundle
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.snehadatta.taroo.ui.presentation.CardPickerScreen
import com.snehadatta.taroo.ui.presentation.ChooseDeckScreen
import com.snehadatta.taroo.ui.presentation.TarotViewModel
import com.snehadatta.taroo.ui.theme.TarooTheme
import com.snehadatta.taroo.util.Resource
import com.snehadatta.taroo.ui.presentation.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardPickerActivity : ComponentActivity() {
    private val tarotViewModel: TarotViewModel by viewModels()

    val images = listOf(
        R.drawable.cover1,
        R.drawable.cover2,
        R.drawable.cover3,
        R.drawable.cover4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tarotViewModel.getAllCards()

        enableEdgeToEdge()
        setContent {
            TarooTheme {
                val navController = rememberNavController()

                val cardState by tarotViewModel.cardState.collectAsStateWithLifecycle()
                var selectedIndex by remember { mutableStateOf(-1) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(navController = navController, startDestination = Routes.ScreenDeckPicker, builder = {
                        composable(Routes.ScreenDeckPicker) {
                            ChooseDeckScreen(Modifier.padding(innerPadding), selectedIndex, navController)
                        }
                        composable(Routes.ScreenCardPicker) {
                            when(cardState) {
                                is Resource.Loading -> {
                                    CircularProgressIndicator()
                                }
                                is Resource.Success -> {
                                    cardState.data?.let {

                                            CardPickerScreen(Modifier.padding(innerPadding), images[selectedIndex],
                                                it.cards, resources)
                                    }
                                }
                                is Resource.Error -> {
                                    val errorMessage = cardState.message ?: "An unknown error occurred"
                                    Text(text = "Error: $errorMessage", color = Color.Red)
                                }

                                else -> {}
                            }
                        }
                    })


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