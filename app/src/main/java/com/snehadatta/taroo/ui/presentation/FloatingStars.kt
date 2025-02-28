package com.snehadatta.taroo.ui.presentation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.snehadatta.taroo.ui.theme.MediumOrange
import kotlin.random.Random

@Composable
fun FloatingStars() {
    val stars = remember { List(100) { Offset(Random.nextFloat() * 1000, Random.nextFloat() * 1000) } }
    val transition = rememberInfiniteTransition(label = "")

    val yOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 80f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        stars.forEach { star ->
            drawCircle(
                color = MediumOrange,
                radius = 6f,
                center = Offset(star.x, star.y + yOffset)
            )
        }
    }
}
