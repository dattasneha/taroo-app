package com.snehadatta.taroo.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.F
import androidx.compose.ui.input.key.Key.Companion.U
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snehadatta.taroo.Greeting
import com.snehadatta.taroo.R
import com.snehadatta.taroo.ui.theme.TarooTheme
val list = listOf(R.drawable.cover3,R.drawable.cover2,R.drawable.cover4)
@Composable
fun CardResultScreen(
    modifier:Modifier,
    images: List<Int>) {
    var imageRes by  remember { mutableStateOf(images) }
    var selectedIndex by remember { mutableStateOf(-1) }
    Column (modifier = Modifier.padding(16.dp)){
        Text(
            text = "Your reading",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row (
            modifier = Modifier
                .height(200.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            DeckImage(imageRes[0],false) {}
            DeckImage(imageRes[1],false) {}
            DeckImage(imageRes[2],false) {}


        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Meaning of cards",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
        )

        Text(
            text = "Meanings",
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.Start)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CardResultPreview() {
    TarooTheme {
        CardResultScreen(Modifier, list)
    }
}