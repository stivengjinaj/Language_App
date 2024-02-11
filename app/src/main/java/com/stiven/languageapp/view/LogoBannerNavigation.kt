package com.stiven.languageapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Standard application logo with back icon controller.
 *
 * @param screenSize Screen width.
 * @param onBackButtonClick Function to be executed when back button is clicked.
 * */
@Composable
fun LogoBannerNavigation(screenSize: Int, onBackButtonClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .height((screenSize / 6).dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(onClick = onBackButtonClick) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = "Back",
                tint = Color.White
            )
        }
        Text(
            modifier = Modifier.weight(0.3f),
            text = "TrioLingo",
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = (screenSize / 12).sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}