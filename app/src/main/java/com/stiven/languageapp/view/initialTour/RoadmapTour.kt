package com.stiven.languageapp.view.initialTour

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.stiven.languageapp.R

@Composable
fun RoadmapTour (
    screenSize: Int,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
){
    val roadmap = if(isSystemInDarkTheme()) R.drawable.mapdark else R.drawable.maplight
    Spacer(modifier = Modifier.height((screenSize/6 - 20).dp))
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = roadmap),
            contentScale = ContentScale.Fit,
            contentDescription = "Map"
        )
    }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        IconButton(
            modifier = Modifier.width((screenSize/3).dp),
            onClick = {
                onBackClick()
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        IconButton(
            modifier = Modifier.width((screenSize/3).dp),
            onClick = {
                onNextClick()
            }
        ) {
            Icon(
                modifier = Modifier.rotate(180f),
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = "Next",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}