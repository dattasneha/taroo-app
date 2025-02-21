package com.snehadatta.taroo.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.snehadatta.taroo.R
import com.snehadatta.taroo.ui.theme.TarooTheme
import com.snehadatta.taroo.ui.theme.brown
import com.snehadatta.taroo.ui.theme.fontFamily
import com.snehadatta.taroo.ui.theme.orange


@Composable
fun ChooseDeckScreen(
    modifier: Modifier = Modifier,
    values:String ) {
    val images = listOf(
        R.drawable.cover1,
        R.drawable.cover2,
        R.drawable.cover3,
        R.drawable.cover4
    )
    var values by remember { mutableStateOf("") }
    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 columns
            modifier = Modifier
                .padding(start = 32.dp,top = 32.dp, end = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(images) { imageRes ->
                DeckImage(imageRes)
            }
        }
        Text(
            text = "Pick a deck of your choice",
            modifier = Modifier
                .padding(top= 8.dp)
                .align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Italic

        )
        TextField(
            value = values,
            onValueChange = {values = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 44.dp),
            maxLines = 15,
            placeholder = {
                Text(text = "Enter message...")
            },
            trailingIcon = {
                if (values.isNotEmpty()) {
                    IconButton(onClick = { values = "" }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear text")
                    }
                }
            }
        )

        Button(
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
                .width(100.dp),
            onClick = { /* Action */ }, colors = ButtonDefaults.buttonColors(orange)) {
            Text("Shuffle",
                fontFamily = fontFamily
            )

        }
    }

}

@Composable
fun DeckImage(imageRes: Int) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp)),
        Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Deck Cover",
            modifier = Modifier
                .height(200.dp)
                .width(100.dp)
                .background(color = brown, RoundedCornerShape(8.dp))
                .shadow(elevation = 12.dp, shape = RoundedCornerShape(12.dp)) // Adds shadow
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Fit
        )
    }
}
@Preview(showBackground = true)
@Composable
fun DeckPickerPreview() {
    TarooTheme {
        ChooseDeckScreen(modifier = Modifier,"Nothing")
    }
}