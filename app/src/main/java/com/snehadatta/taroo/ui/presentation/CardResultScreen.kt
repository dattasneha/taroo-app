package com.snehadatta.taroo.ui.presentation

import android.content.res.Resources
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.snehadatta.taroo.data.model.Card
import com.snehadatta.taroo.ui.theme.LightBrown
import com.snehadatta.taroo.ui.theme.LightOrange
import com.snehadatta.taroo.ui.theme.TarooTheme
import com.snehadatta.taroo.util.TarotImageMapper

@Composable
fun CardResultScreen(
    modifier:Modifier,
    resultList: List<Card>,
    resources: Resources,
    viewModel: TarotViewModel,
    navController: NavController
) {
    BackHandler {
        viewModel.changeStateOfInitialQuestion(true)
        navController.navigate(Routes.ScreenChatCardReading) {
            popUpTo(Routes.ScreenChatCardReading) { inclusive = true } // Avoid multiple entries in back stack
        }
    }
    val selectedTabIndex = remember { mutableIntStateOf(0) }

    Column (
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex.intValue,
            containerColor = LightBrown,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ) {
            for (i in resultList.indices) {
                val card = resultList[i]
                Column(
                    modifier = Modifier
                        .background(
                            if (i == selectedTabIndex.intValue)
                                Color.Black.copy(alpha = 0.8f)
                            else
                                Color.Transparent
                        )
                        .clickable { selectedTabIndex.intValue = i },
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    DeckImage(
                        imageRes = TarotImageMapper.getTarotImage(
                            resources,
                            card.nameShort
                        ),
                        isSelected = i == selectedTabIndex.intValue,
                        showBorder = false,
                        enableFlip = false,
                        onClick = {  },
                    )

                    Text(
                        text = card.name,
                        fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                    )
                }

            }
        }

        CardWithHeaderAndBody(
            header = "Meaning",
            body = resultList[selectedTabIndex.intValue].meaningUp
        )

        CardWithHeaderAndBody(
            header = "Reading",
            body = resultList[selectedTabIndex.intValue].desc
        )
    }
}

@Composable
fun CardWithHeaderAndBody(
    header: String,
    body: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = header,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = body,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Preview
//@Composable
//private fun CardResultScreenPreview() {
//    val dummyCard = Card(
//        type = "minor",
//        nameShort = "cu09",
//        name = "Nine of Cups",
//        value = "nine",
//        valueInt = 9,
//        meaningUp = "Concord, contentment, physical bien-être; also victory, success, advantage; " +
//                "satisfaction for the Querent or person for whom the consultation is made.",
//        meaningRev = "Truth, loyalty, liberty; but the readings vary and include mistakes, " +
//                "imperfections, etc.",
//        desc = "A goodly personage has feasted to his heart's content, and abundant refreshment of" +
//                " wine is on the arched counter behind him, seeming to indicate that the future is" +
//                " also assured. The picture offers the material side only, but there are other aspects.",
//        suit = "cups"
//    )
//
//    TarooTheme {
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    colors = TopAppBarDefaults.topAppBarColors(
//                        containerColor = MaterialTheme.colorScheme.primary,
//                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
//                    ),
//                    title = {
//                        Text(
//                            text = "Your readings",
//                        )
//                    },
//                    navigationIcon = {
//                        Icon(
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .clickable { },
//                            imageVector = Icons.Default.Clear,
//                            contentDescription = "Back",
//                            tint = MaterialTheme.colorScheme.onPrimary
//                        )
//                    }
//                )
//            }
//        ) { innerPadding ->
//            CardResultScreen(
//                modifier = Modifier
//                    .background(color = LightOrange)
//                    .padding(innerPadding),
//                resultList = listOf(dummyCard, dummyCard, dummyCard),
//                resources = Resources.getSystem()
//            )
//        }
//    }
//}