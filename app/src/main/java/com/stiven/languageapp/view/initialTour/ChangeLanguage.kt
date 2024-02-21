package com.stiven.languageapp.view.initialTour

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stiven.languageapp.R
import com.stiven.languageapp.screens.changeLanguage
import com.stiven.languageapp.utils.Languages
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

@Composable
fun ChangeLanguage(
    screenSize: Int,
    context: Context,
    textToSpeechViewModel: TextToSpeechViewModel,
    onNextClick: () -> Unit
) {
    val ttsDone = remember {
        mutableIntStateOf(0)
    }
    if (ttsDone.intValue == 0){
        textToSpeechViewModel.textToSpeech(context, context.getString(R.string.change_language))
        ttsDone.intValue = 1
    }
    Spacer(modifier = Modifier.height((screenSize/6 + 10).dp))
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Text(
            text = context.getString(R.string.change_language),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = (screenSize/6 - 45).sp,
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height((screenSize/6 - 10).dp))
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        IconButton(
            onClick = {
                changeLanguage(Languages.ENGLISH, context)
            },
            modifier = Modifier.size((screenSize/2 - 40).dp)
        ) {
            Image(
                modifier = Modifier.size((screenSize/2 - 70).dp),
                painter = painterResource(id = R.drawable.gb),
                contentDescription = "English"
            )
        }

    }
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        IconButton(
            onClick = {
                changeLanguage(Languages.ITALIAN, context)
            },
            Modifier.size((screenSize/2 - 40).dp)
        ) {
            Image(
                modifier = Modifier.size((screenSize/2 - 70).dp),
                painter = painterResource(id = R.drawable.it),
                contentDescription = "English"
            )
        }
    }
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        IconButton(
            onClick = {
                changeLanguage(Languages.FRENCH, context)
            },
            Modifier.size((screenSize/2 - 40).dp)
        ) {
            Image(
                modifier = Modifier.size((screenSize/2 - 70).dp),
                painter = painterResource(id = R.drawable.fr),
                contentDescription = "English"
            )
        }

    }
    Spacer(modifier = Modifier.height((screenSize/6 - 10).dp))
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 30.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.End
    ){
        IconButton(
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