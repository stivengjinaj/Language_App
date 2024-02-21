package com.stiven.languageapp.view.initialTour

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stiven.languageapp.R
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import java.util.Locale

@Composable
fun DictionaryGuide(
    screenSize: Int,
    context: Context,
    textToSpeechViewModel: TextToSpeechViewModel,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
){
    val expanded = remember {
        mutableStateOf(false)
    }
    val expansionArrangement = remember {
        mutableStateOf(false)
    }
    val searchAlpha = remember {
        mutableFloatStateOf(1f)
    }
    val cardAlpha = remember {
        mutableFloatStateOf(0.3f)
    }
    val wordAlpha = remember {
        mutableFloatStateOf(0.3f)
    }
    val listenIconAlpha = remember {
        mutableFloatStateOf(0.3f)
    }
    val expandedAlpha = remember {
        mutableFloatStateOf(0.3f)
    }
    val alphaIndex = remember {
        mutableIntStateOf(0)
    }
    Spacer(modifier = Modifier.height((screenSize/6 - 20).dp))
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .alpha(searchAlpha.floatValue)
    ){
        TextField(
            modifier = Modifier
                .width((screenSize - 10).dp)
                .clip(RoundedCornerShape(30.dp))
                .border(
                    BorderStroke(2.dp, MaterialTheme.colorScheme.inversePrimary),
                    RoundedCornerShape(30.dp)
                ),
            value = TextFieldValue(context.getString(R.string.search)),
            onValueChange = {},
            leadingIcon = {
                Icon(Icons.Rounded.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.inversePrimary)
            }
        )
    }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = (screenSize / 4).dp),
        horizontalArrangement = Arrangement.Center
    ){
        Card(
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 10.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .alpha(cardAlpha.floatValue)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if(expansionArrangement.value) Arrangement.Center else Arrangement.SpaceAround
            ){
                Text(
                    text = "Ciao",
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontStyle = FontStyle.Normal,
                    fontSize = (screenSize/6 - 40).sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(8.dp)
                        .alpha(wordAlpha.floatValue)
                )
                Spacer(modifier = Modifier.width(10.dp))
                IconButton(
                    modifier = Modifier.alpha(listenIconAlpha.floatValue),
                    onClick = {

                }) {
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = "Listen",
                        tint = MaterialTheme.colorScheme.inversePrimary
                    )
                }
            }
            if (expanded.value) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(expandedAlpha.floatValue),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = context.getString(R.string.english),
                            color = Color.Green,
                            fontSize = (screenSize / 6 - 45).sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Hello",
                                color = MaterialTheme.colorScheme.inversePrimary,
                                fontSize = (screenSize / 6 - 50).sp
                            )
                            IconButton(onClick = {
                                textToSpeechViewModel.customTextToSpeech(context, "Hello", Locale.US)
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.PlayArrow,
                                    contentDescription = "Listen",
                                    tint = MaterialTheme.colorScheme.inversePrimary
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width((screenSize / 6).dp))
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = context.getString(R.string.french),
                            color = Color.Green,
                            fontSize = (screenSize / 6 - 45).sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Bonjour",
                                color = MaterialTheme.colorScheme.inversePrimary,
                                fontSize = (screenSize / 6 - 50).sp
                            )
                            IconButton(
                                modifier = Modifier.alpha(listenIconAlpha.floatValue),
                                onClick = {
                                textToSpeechViewModel.customTextToSpeech(
                                    context,
                                    "Bonjour",
                                    Locale.FRENCH
                                )
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.PlayArrow,
                                    contentDescription = "Listen",
                                    tint = MaterialTheme.colorScheme.inversePrimary
                                )
                            }
                        }
                    }
                }
            }
        }
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
                when(alphaIndex.intValue){
                    0 -> {
                        onBackClick()
                    }
                    1 -> {
                        searchAlpha.floatValue = 1f
                        wordAlpha.floatValue = 0.3f
                        cardAlpha.floatValue = 0.3f
                    }
                    2 -> {
                        listenIconAlpha.floatValue = 0.3f
                        wordAlpha.floatValue = 1f
                    }
                    3 -> {
                        listenIconAlpha.floatValue = 1f
                        expandedAlpha.floatValue = 0.3f
                        expanded.value = false
                        expansionArrangement.value = false
                    }
                }
                alphaIndex.intValue--
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
                when(alphaIndex.intValue){
                    0 -> {
                        textToSpeechViewModel.stopTextToSpeech()
                        textToSpeechViewModel.customTextToSpeech(
                            context = context,
                            text = context.getString(R.string.searched_word),
                            language = Locale.forLanguageTag(Locale.getDefault().language)
                        )
                        searchAlpha.floatValue = 0.3f
                        cardAlpha.floatValue = 1f
                        wordAlpha.floatValue = 1f
                    }
                    1 -> {
                        textToSpeechViewModel.stopTextToSpeech()
                        textToSpeechViewModel.customTextToSpeech(
                            context = context,
                            text = context.getString(R.string.listen_word),
                            language = Locale.forLanguageTag(Locale.getDefault().language)
                        )
                        wordAlpha.floatValue = 0.3f
                        listenIconAlpha.floatValue = 1f
                    }
                    2 -> {
                        textToSpeechViewModel.stopTextToSpeech()
                        textToSpeechViewModel.customTextToSpeech(
                            context = context,
                            text = context.getString(R.string.translate_word),
                            language = Locale.forLanguageTag(Locale.getDefault().language)
                        )
                        wordAlpha.floatValue = 1f
                        expandedAlpha.floatValue = 1f
                        expanded.value = true
                        expansionArrangement.value = true
                    }
                    else -> {
                        onNextClick()
                    }
                }
                alphaIndex.intValue++
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