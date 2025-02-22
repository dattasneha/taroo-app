package com.snehadatta.taroo.ui.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snehadatta.taroo.R
import com.snehadatta.taroo.ui.theme.TarooTheme
import com.snehadatta.taroo.ui.theme.orange

@Composable
fun ChooseDeckScreen(
    modifier: Modifier = Modifier
) {
    val images = listOf(
        R.drawable.cover1,
        R.drawable.cover2,
        R.drawable.cover3,
        R.drawable.cover4
    )
    var selectedIndex by remember { mutableStateOf(-1) }
    var question by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box( // Wrapping in a Box with height constraint
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .aspectRatio(0.6f)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(images) { index, imageRes ->
                    DeckImage(
                        imageRes = imageRes,
                        isSelected = selectedIndex == index,
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

        OutlinedButton (
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {},
            colors = ButtonDefaults.buttonColors(orange)
        ) {
            Text(
                text = "Shuffle",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }

}



@PreviewLightDark()
@Composable
fun DeckPickerPreview() {
    TarooTheme {
        Scaffold { innerPadding ->
            ChooseDeckScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}

