package com.stiven.languageapp.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Customized card view for exercises.
 *
 * @param exercise exercise name.
 * @param trailingIcon trailing icon.
 * @param onClick function callback to execute when clicked.
 * */
@Composable
fun ExercisesCard(
    exercise: String,
    trailingIcon: ImageVector,
    onClick: () -> Unit
){
    val screenSize = LocalConfiguration.current.screenWidthDp
    Card (
        shape = RoundedCornerShape(30),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 20.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(
                onClick = { onClick() }
            )
    ){
        Row (
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp, end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = exercise,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold,
                fontSize = (screenSize/6 - 40).sp,
                modifier = Modifier.padding(8.dp)
            )
            Icon(
                imageVector = trailingIcon,
                contentDescription = "Exercise Icon",
                tint = Color.White
            )
        }
    }
}