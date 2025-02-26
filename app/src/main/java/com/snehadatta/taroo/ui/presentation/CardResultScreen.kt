package com.snehadatta.taroo.ui.presentation

import android.content.res.Resources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snehadatta.taroo.R
import com.snehadatta.taroo.data.model.Card
import com.snehadatta.taroo.ui.theme.TarooTheme
import com.snehadatta.taroo.util.TarotImageMapper

val list = listOf(R.drawable.cover3,R.drawable.cover2,R.drawable.cover4)
@Composable
fun CardResultScreen(
    modifier:Modifier,
    images: List<Card>) {
    val imageRes by  remember { mutableStateOf(images) }
    val selectedIndex by remember { mutableStateOf(-1) }

    Column (modifier = modifier.padding(16.dp)){
        Text(
            text = "Your reading",
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row (
            modifier = Modifier
                .height(200.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            DeckImage(TarotImageMapper.getTarotImage(Resources.getSystem(),imageRes[0].nameShort),false,false) {}
            DeckImage(TarotImageMapper.getTarotImage(Resources.getSystem(),imageRes[1].nameShort),false,false) {}
            DeckImage(TarotImageMapper.getTarotImage(Resources.getSystem(),imageRes[2].nameShort),false,false) {}

        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Meaning of cards",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
        )

        Text(
            text = "your personalised reading",
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.Start)
        )

    }
}
