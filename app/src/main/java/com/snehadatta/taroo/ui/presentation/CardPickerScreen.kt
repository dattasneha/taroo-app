package com.snehadatta.taroo.ui.presentation

import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.snehadatta.taroo.data.model.Card
import com.snehadatta.taroo.util.TarotImageMapper
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun CardPickerScreen(
    modifier: Modifier = Modifier,
    deckImageRes: Int,
    tarotCardList: List<Card>,
    resources: Resources,
    navController: NavController,
    viewModel: TarotViewModel,
    onCardClick: (String) -> Unit
) {
    val images = List(78) { deckImageRes }
    val mutableCardList = remember { tarotCardList.toMutableList() }
    var showDialog by remember { mutableStateOf(false) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = modifier.padding(horizontal = 8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        itemsIndexed(images) { index, imageRes ->
            val currentCardImageRes = remember { mutableIntStateOf(imageRes) }

            DeckImage(
                imageRes = currentCardImageRes.intValue,
                isSelected = viewModel.selectedCards.any { it.nameShort == tarotCardList[index].nameShort },
                showBorder = false,
                onClick = {
                    if (viewModel.selectedCards.size >= 3 || mutableCardList.isEmpty()) return@DeckImage

                    val randomIndex = Random.nextInt(mutableCardList.size)
                    val selectedCard = mutableCardList.removeAt(randomIndex)

                    onCardClick(selectedCard.nameShort)
                    viewModel.updateSelectedCardList(selectedCard)
                    currentCardImageRes.intValue = TarotImageMapper.getTarotImage(resources, selectedCard.nameShort)

                    if (viewModel.selectedCards.size == 3) {
                        showDialog = true
                    }
                }
            )
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { navController.popBackStack() },
        ) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your chosen cards",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        viewModel.selectedCards.forEach { card -> // Correctly fetch selected cards
                            DeckImage(
                                imageRes = TarotImageMapper.getTarotImage(resources, card.nameShort),
                                isSelected = false,
                                showBorder = false,
                                onClick = {}
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    TarooButton(
                        text = "Show Readings",
                        onClick = { navController.navigate(Routes.ScreenCardResult) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    TarooButton(
                        text = "Chat with AI",
                        onClick = { navController.navigate(Routes.ScreenChatCardReading) }
                    )
                }
            }
        }
    }
}

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
//@Composable
//fun CardPickerScreenPreview() {
//    val dummyCards = List(78) {
//        Card(
//            type = "major",
//            nameShort = "ar${it + 1}",
//            name = "Arcana ${it + 1}",
//            value = "${it + 1}",
//            valueInt = it + 1,
//            meaningUp = "Positive meaning for card ${it + 1}",
//            meaningRev = "Negative meaning for card ${it + 1}",
//            desc = "Description for card ${it + 1}",
//            suit = "major"
//        )
//    }
//
//    val fakeNavController = rememberNavController()
//
//    TarooTheme {
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    colors = TopAppBarDefaults.topAppBarColors(
//                        containerColor = MaterialTheme.colorScheme.primary,
//                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
//                    ),
//                    title = { Text(text = "Choose 3 Cards") },
//                    navigationIcon = {
//                        Icon(
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .clickable { fakeNavController.popBackStack() },
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Back",
//                            tint = MaterialTheme.colorScheme.onPrimary
//                        )
//                    }
//                )
//            }
//        ) { innerPadding ->
//            CardPickerScreen(
//                modifier = Modifier.padding(innerPadding),
//                deckImageRes = R.drawable.cover2,
//                tarotCardList = dummyCards,
//                resources = Resources.getSystem(),
//                navController = fakeNavController,
//                onCardClick = {}
//            )
//        }
//    }
//}