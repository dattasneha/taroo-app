package com.snehadatta.taroo.ui.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snehadatta.taroo.ui.theme.Brown

@Composable
fun TarooButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    OutlinedButton (
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(Brown)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}