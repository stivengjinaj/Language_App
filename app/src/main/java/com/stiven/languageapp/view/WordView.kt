package com.stiven.languageapp.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stiven.languageapp.R
import com.stiven.languageapp.model.Word
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import java.util.Locale

/**
 * Customized card view for a word.
 *
 * @param word word to show.
 * @param screenSize size of screen where the word is shown.
 * @param textToSpeechViewModel text-to-speech viewModel for pronouncing.
 * */
@Composable
fun WordView(
    word: Word,
    screenSize: Int,
    textToSpeechViewModel: TextToSpeechViewModel
){
    val expanded = remember { mutableStateOf (false) }
    val expansionArrangement = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable(
                onClick = {
                    expanded.value = !expanded.value
                    expansionArrangement.value = !expansionArrangement.value
                }
            )
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if(expansionArrangement.value) Arrangement.Center else Arrangement.SpaceAround
        ){
            Text(
                text = word.italian.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                color = MaterialTheme.colorScheme.secondary,
                fontStyle = FontStyle.Normal,
                fontSize = (screenSize/6 - 40).sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(onClick = {
                textToSpeechViewModel.textToSpeech(context, word.italian)
            }) {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = "Listen",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
        if (expanded.value) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                        fontSize = (screenSize/6 - 45).sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = word.english.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.getDefault()
                                ) else it.toString()
                            },
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = (screenSize/6 - 50).sp
                        )
                        IconButton(onClick = {
                            textToSpeechViewModel.customTextToSpeech(context, word.english, Locale.US)
                        }) {
                            Icon(imageVector = Icons.Rounded.PlayArrow, contentDescription = "Listen", tint = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }
                Spacer(modifier = Modifier.width((screenSize/6).dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = context.getString(R.string.french),
                        color = Color.Green,
                        fontSize = (screenSize/6 - 45).sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = word.french.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.ROOT
                                ) else it.toString()
                            },
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = (screenSize/6 - 50).sp
                        )
                        IconButton(onClick = {
                            textToSpeechViewModel.customTextToSpeech(context, word.french, Locale.FRENCH)
                        }) {
                            Icon(imageVector = Icons.Rounded.PlayArrow, contentDescription = "Listen", tint = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}