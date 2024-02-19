package com.stiven.languageapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Standard application logo.
 *
 * @param screenSize Screen width.
 * */
@Composable
fun LogoBanner(screenSize: Int){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .height((screenSize / 6).dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "LinguaLearn",
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = (screenSize / 12).sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}