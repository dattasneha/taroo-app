package com.snehadatta.taroo

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.snehadatta.taroo.ui.presentation.CardPickerScreen
import com.snehadatta.taroo.ui.presentation.CardResultScreen
import com.snehadatta.taroo.ui.presentation.ChatScreenCardReading
import com.snehadatta.taroo.ui.presentation.ChooseDeckScreen
import com.snehadatta.taroo.ui.presentation.Routes
import com.snehadatta.taroo.ui.presentation.TarotViewModel
import com.snehadatta.taroo.ui.theme.LightOrange
import com.snehadatta.taroo.ui.theme.TarooTheme
import com.snehadatta.taroo.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardPickerActivity : ComponentActivity() {
    private val tarotViewModel: TarotViewModel by viewModels()
    private var mediaPlayer: MediaPlayer? = null
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tarotViewModel.getAllCards()

        enableEdgeToEdge()
        setContent {
            TarooTheme {

                mediaPlayer?.release();
                mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
                mediaPlayer?.start()

                tarotViewModel.getChatHistory()

                val navController = rememberNavController()
                val cardState by tarotViewModel.cardState.collectAsStateWithLifecycle()

                val title = remember { mutableStateOf("Taroo") }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                            title = {
                                Text(
                                    text = title.value,
                                )
                            },
                        )
                    }
                ) { innerPadding ->

                    NavHost(navController = navController, startDestination = Routes.ScreenDeckPicker, builder = {
                        composable(Routes.ScreenDeckPicker) {
                            title.value = "Taroo"
                            ChooseDeckScreen(
                                Modifier
                                    .background(color = LightOrange)
                                    .padding(innerPadding),
                                navController,tarotViewModel
                            )
                        }
                        composable(
                            Routes.ScreenCardPicker+"/{deckImageRes}",
                            arguments = listOf(navArgument("deckImageRes"){ type = NavType.IntType})
                        ) { backStackEntry ->
                            val deckImageRes = backStackEntry.arguments?.getInt("deckImageRes")
                            when(cardState) {
                                is Resource.Loading -> {
                                    CircularProgressIndicator()
                                }
                                is Resource.Success -> {
                                    cardState.data?.let {
                                        if (deckImageRes != null) {
                                            title.value = "Choose 3 cards"
                                            CardPickerScreen(Modifier.padding(innerPadding), deckImageRes,
                                                it.cards, resources, navController,tarotViewModel) { cardName ->
                                                tarotViewModel.updateCardList(cardName)
                                            }
                                        }
                                    }
                                }
                                is Resource.Error -> {
                                    val errorMessage = cardState.message ?: "An unknown error occurred"
                                    Text(text = "Error: $errorMessage", color = Color.Red)
                                }
                            }
                        }
                        composable(Routes.ScreenCardResult) {
                            title.value = "Your reading"
                            CardResultScreen(Modifier.padding(innerPadding),tarotViewModel.selectedCards,resources)
                        }
                        composable(Routes.ScreenChatCardReading) {
                            title.value = "Chat with AI"
                            ChatScreenCardReading(Modifier.padding(innerPadding),tarotViewModel)
                        }
                    })
                }
            }
        }
    }
}