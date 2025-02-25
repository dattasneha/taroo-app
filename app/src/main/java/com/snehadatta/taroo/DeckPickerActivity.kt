package com.snehadatta.taroo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.snehadatta.taroo.ui.presentation.CardResultScreen
import com.snehadatta.taroo.ui.presentation.list
import com.snehadatta.taroo.ui.theme.TarooTheme
import com.snehadatta.taroo.ui.theme.orange

@OptIn(ExperimentalMaterial3Api::class)
class DeckPickerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TarooTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = orange),
                            title = {
                                Text(
                                    text = "Choose a Deck",
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        )
                    }
                ) { innerPadding ->
                    CardResultScreen(Modifier.padding(innerPadding), list)
                }
            }
        }
    }
}