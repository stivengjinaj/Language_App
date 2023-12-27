package com.stiven.languageapp.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.stiven.languageapp.model.Letter
import com.stiven.languageapp.viewmodels.SpeechActions
import com.stiven.languageapp.viewmodels.SpeechToTextViewModel

/**
 * Row containing data and logics-view of a letter.
 *
 * @param letter the letter the row contains.
 * @param speechToTextViewModel speech-to-text viewModel.
 * @param letterModels all letter's similarities present in database.
 * */
@Composable
fun LetterView(
    letter: String,
    speechToTextViewModel: SpeechToTextViewModel,
    letterModels: LiveData<List<Letter>>
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp / 12
    val speechText = remember {
        mutableStateOf("")
    }
    val checkLetter = remember{
        mutableStateOf( false )
    }
    val pressed = remember {
        mutableStateOf(false)
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colorScheme.primary)
            .width((screenWidth * 12 - 50).dp)
            .height((screenWidth + 20).dp)
    ){
        Text(
            text = letter.uppercase(),
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold,
            fontSize = (screenWidth - 5).sp
        )
        Spacer(modifier = Modifier.width((screenWidth - 20).dp))
        Text(
            text = letter.lowercase(),
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold,
            fontSize = (screenWidth - 10).sp
        )
        Spacer(modifier = Modifier.width((screenWidth + 100).dp))
        IconButton(
            modifier = Modifier
                .size(screenWidth.dp)
                .clip(RoundedCornerShape(100))
                .background(if (checkLetter.value) Color.Green else Color.Red),
            onClick = {
                if(pressed.value){
                    pressed.value = false
                    speechToTextViewModel.send(SpeechActions.EndRecord)
                    letterModels.value?.let {
                        Log.d("DONE", speechToTextViewModel.state.spokenText)
                        checkLetter.value = checkLetter(speechToTextViewModel.state.spokenText, it)
                    }
                }else{
                    pressed.value = true
                    speechToTextViewModel.send(SpeechActions.StartRecord)
                    speechText.value = speechToTextViewModel.state.spokenText
                }
            }
        ){
            when(checkLetter.value){
                true -> {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Correct",
                        tint = Color.White,
                        modifier = Modifier.size((screenWidth).dp)
                    )
                }
                false -> {
                    if(pressed.value){
                        Icon(imageVector = Icons.Rounded.Stop, contentDescription = "Recording", tint = Color.White)
                    }else{
                        Icon(imageVector = Icons.Rounded.Mic, contentDescription = "Mic", tint = Color.White)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(screenWidth.dp))
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowDown,
            contentDescription = "dropdown",
            tint = Color.White,
            modifier = Modifier.size(screenWidth.dp)
        )
    }
}

/**
 * Function that checks the correctness of a letter.
 * */
fun checkLetter(spokenText: String, letterModels: List<Letter>): Boolean{
    val similarToStrings: List<String> = letterModels.map { it.similarTo }
    return similarToStrings.contains(spokenText)
}