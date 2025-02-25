package com.snehadatta.taroo.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.snehadatta.taroo.ui.theme.orange

@Composable
fun DeckImage(imageRes: Int, isSelected: Boolean, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "Deck Cover",
        modifier = Modifier
            .aspectRatio(0.6f)
            .padding(12.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp)) // Adds shadow
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .border(
                width = if (isSelected) 6.dp else 0.dp, // Adds a red border if selected
                color = if (isSelected) orange else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            ),
        contentScale = ContentScale.Crop
    )
}