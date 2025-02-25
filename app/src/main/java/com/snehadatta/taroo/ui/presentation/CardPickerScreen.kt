package com.snehadatta.taroo.ui.presentation

import android.content.res.Resources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.snehadatta.taroo.R
import com.snehadatta.taroo.data.model.Card
import com.snehadatta.taroo.util.TarotImageMapper
import kotlin.random.Random

@Composable
fun CardPickerScreen(
    modifier: Modifier,
    deckImageRes: Int,
    tarotCardList: List<Card>,
    resources: Resources
) {

    val images = mutableListOf<Int>().apply {
        repeat(78) { add(deckImageRes) }
    }
    val mutableCardList = tarotCardList.toMutableList()

    var selectedIndex by remember { mutableStateOf(-1) }

            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                modifier = modifier
                    .padding(16.dp)
                    .padding(top = 32.dp),

                verticalArrangement = Arrangement.spacedBy(0.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                itemsIndexed(images) { index, imageRes ->
                    DeckImage(
                        imageRes = imageRes,
                        isSelected = selectedIndex == index,
                        onClick = {selectedIndex = index}
                    )

                    if(selectedIndex == index) {
                        val randomIndex = Random.nextInt(mutableCardList.size)
                        val image = mutableCardList.removeAt(randomIndex)
                        val selectedImage = TarotImageMapper.getTarotImage(resources,image.nameShort)
                        DeckImage(
                            imageRes = selectedImage,
                            isSelected = false,
                            onClick = {}
                        )
                    }

                }

            }

}


