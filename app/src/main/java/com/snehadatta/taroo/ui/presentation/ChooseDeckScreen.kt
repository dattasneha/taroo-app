package com.snehadatta.taroo.ui.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.snehadatta.taroo.R
import com.snehadatta.taroo.ui.theme.Brown
import com.snehadatta.taroo.ui.theme.LightOrange
import com.snehadatta.taroo.ui.theme.MediumBrown
import com.snehadatta.taroo.ui.theme.MediumOrange

@Composable
fun ChooseDeckScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: TarotViewModel
) {
    val images = listOf(
        R.drawable.cover1,
        R.drawable.cover2,
        R.drawable.cover3,
        R.drawable.cover4
    )
    var selectedIndex by remember { mutableStateOf(-1) }
    var question by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Brown, // Start color
                            MediumBrown,
                            MediumOrange// End color
                        )
                    ), RoundedCornerShape(8.dp)
                )
                .aspectRatio(2.5f)
                .clickable { navController.navigate(Routes.ScreenChatCardReading) },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(R.drawable.tarot_reader),
                contentDescription = "AI tarot reader",
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(3.dp, LightOrange, RoundedCornerShape(8.dp))
            )
            Column (
                modifier = Modifier
                    .padding(top = 16.dp)
                    .weight(1f),
            ) {
                Text(
                    text = "Your AI reader",
                    color = Color.White,
                    modifier = Modifier,
                    fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Click here to chat with your personalised mentor.",
                    color = Color.White,
                    fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
                    fontWeight = FontWeight.Medium
                )
            }

        }
        Text(
            text = "Pick a deck of your choice...",
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Italic,
            color = Brown,
            modifier = Modifier.padding(top = 12.dp)
        )
        Box( // Wrapping in a Box with height constraint
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .aspectRatio(2f)
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                itemsIndexed(images) { index, imageRes ->
                    DeckImage(
                        imageRes = imageRes,
                        isSelected = selectedIndex == index,
                        showBorder = true,
                        enableFlip = false,
                        onClick = { selectedIndex = if (selectedIndex == index) -1 else index }
                    )
                }
            }
        }

        OutlinedTextField(
            value = question,
            onValueChange = { question = it },
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = false,
            maxLines = 15,
            label = {
                Text(text = "Enter your question here")
            },
            trailingIcon = {
                if (question.isNotEmpty()) {
                    IconButton(onClick = { question = "" }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear text")
                    }
                }
            },
        )

        Spacer(modifier = Modifier.height(20.dp))

        TarooButton(
            text = "Shuffle",
            onClick = {
                if (selectedIndex != -1 && question.isNotEmpty()) {
                    viewModel.updateInitialQuestion(question)
                    navController.navigate(Routes.ScreenCardPicker+"/${images[selectedIndex]}")
                }
                else if(selectedIndex == -1) {
                    Toast.makeText(context, "Please choose a deck.", Toast.LENGTH_SHORT ).show()
                }
                else if(question.isEmpty()) {
                    Toast.makeText(context, "Please write a message.", Toast.LENGTH_SHORT ).show()
                }
            }
        )
    }
}



//@PreviewLightDark()
//@Composable
//fun DeckPickerPreview() {
//    TarooTheme {
//        Scaffold { innerPadding ->
//            ChooseDeckScreen(modifier = Modifier.padding(innerPadding))
//        }
//    }
//}