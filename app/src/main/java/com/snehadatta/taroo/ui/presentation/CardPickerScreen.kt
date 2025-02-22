package com.snehadatta.taroo.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastMaxOf
import com.snehadatta.taroo.R
import java.util.Collections.list

@Composable
fun CardPickerScreen(
    modifier: Modifier,
    deckImageRes: Int
) {
    val images = mutableListOf<Int>().apply {
        repeat(78) { add(deckImageRes) }
    }
    var selectedIndex by remember { mutableStateOf(-1) }

            LazyVerticalGrid(
                columns = GridCells.Fixed(6),
                modifier = Modifier
                    .padding(16.dp)
                    .padding(top = 32.dp),

                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
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