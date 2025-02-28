package com.snehadatta.taroo.ui.presentation

import android.content.res.Resources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import com.snehadatta.taroo.data.model.Card
import com.snehadatta.taroo.ui.theme.TarooTheme
import com.snehadatta.taroo.util.TarotImageMapper

@Composable
fun CardResultScreen(
    modifier:Modifier,
    images: List<Card>
) {
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

@Preview
@Composable
private fun CardResultScreenPreview() {
    val dummyCard = Card(
        type = "minor",
        nameShort = "cu09",
        name = "Nine of Cups",
        value = "nine",
        valueInt = 9,
        meaningUp = "Concord, contentment, physical bien-être; also victory, success, advantage; " +
                "satisfaction for the Querent or person for whom the consultation is made.",
        meaningRev = "Truth, loyalty, liberty; but the readings vary and include mistakes, " +
                "imperfections, etc.",
        desc = "A goodly personage has feasted to his heart's content, and abundant refreshment " +
                "of wine is on the arched counter behind him, seeming to indicate that the future " +
                "is also assured. The picture offers the material side only, but there are other" +
                " aspects.",
        suit = "cups"
    )

    TarooTheme {
        Scaffold { innerPadding ->
            CardResultScreen(
                modifier = Modifier.padding(innerPadding),
                images = listOf(dummyCard, dummyCard, dummyCard)
            )
        }
    }
}