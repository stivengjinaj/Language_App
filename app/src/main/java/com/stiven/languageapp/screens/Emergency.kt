package com.stiven.languageapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Group
import androidx.compose.material.icons.rounded.LocalHospital
import androidx.compose.material.icons.rounded.LocalPolice
import androidx.compose.material.icons.rounded.School
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stiven.languageapp.R
import com.stiven.languageapp.model.EmergencyPhrase
import com.stiven.languageapp.utils.Languages
import com.stiven.languageapp.view.EmergencyPhraseView
import com.stiven.languageapp.viewmodels.EmergencyPhraseViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import java.util.Locale

/**
 * Composable for emergency page.
 *
 * @param emergencyPhraseViewModel emergency phrases ViewModel.
 * @param textToSpeechViewModel text to speech ViewModel.
 * */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Emergency(
    emergencyPhraseViewModel: EmergencyPhraseViewModel,
    textToSpeechViewModel: TextToSpeechViewModel
) {
    val selectedFilter = remember {
        mutableIntStateOf(0)
    }
    val selectedColor = if(isSystemInDarkTheme()) Color.White else Color.Green
    val conversationPhrases = filterPhrases(emergencyPhraseViewModel, "Interaction", selectedFilter.intValue)
    val schoolPhrases = filterPhrases(emergencyPhraseViewModel, "School", selectedFilter.intValue)
    val hospitalPhrases = filterPhrases(emergencyPhraseViewModel, "Hospital", selectedFilter.intValue)
    val policePhrases = filterPhrases(emergencyPhraseViewModel, "Police", selectedFilter.intValue)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        val screenSize = LocalConfiguration.current.screenWidthDp
        val context = LocalContext.current
        Spacer(modifier = Modifier.height((screenSize / 6).dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            OutlinedButton(
                onClick = { 
                    if(selectedFilter.intValue == 1){
                        selectedFilter.intValue = 0
                    }else{
                        selectedFilter.intValue = 1
                    } 
                },
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp,if(selectedFilter.intValue == 1) selectedColor else MaterialTheme.colorScheme.primary),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = context.getString(R.string.english),
                    modifier = Modifier.combinedClickable(
                        onClick = {},
                        onLongClick = {
                            textToSpeechViewModel.customTextToSpeech(
                                context,
                                context.getString(R.string.english_words),
                                Locale.getDefault()
                            )
                        }
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            OutlinedButton(
                onClick = {
                    if(selectedFilter.intValue == 2){
                        selectedFilter.intValue = 0
                    }else{
                        selectedFilter.intValue = 2
                    }
                },
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp,if(selectedFilter.intValue == 2) selectedColor else MaterialTheme.colorScheme.primary),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = context.getString(R.string.italian),
                    modifier = Modifier.combinedClickable(
                        onClick = {},
                        onLongClick = {
                            textToSpeechViewModel.customTextToSpeech(
                                context,
                                context.getString(R.string.italian_words),
                                Locale.getDefault()
                            )
                        }
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            OutlinedButton(
                onClick = {
                    if(selectedFilter.intValue == 3){
                        selectedFilter.intValue = 0
                    }else{
                        selectedFilter.intValue = 3
                    }
                },
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp,if(selectedFilter.intValue == 3) selectedColor else MaterialTheme.colorScheme.primary),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = context.getString(R.string.french),
                    modifier = Modifier.combinedClickable(
                        onClick = {},
                        onLongClick = {
                            textToSpeechViewModel.customTextToSpeech(
                                context,
                                context.getString(R.string.french_words),
                                Locale.getDefault()
                            )
                        }
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
        Spacer(modifier = Modifier.height((screenSize/6 - 40).dp))
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                modifier = Modifier.size((screenSize/6 + 40).dp).combinedClickable(
                    onClick = {},
                    onLongClick = {
                        textToSpeechViewModel.customTextToSpeech(
                            context,
                            context.getString(R.string.interaction_tts),
                            Locale.getDefault()
                        )
                    }
                ),
                imageVector = Icons.Rounded.Group,
                contentDescription = "Interaction"
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = context.getString(R.string.interaction),
                fontWeight = FontWeight.Bold,
                fontSize = (screenSize/6 - 40).sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6 - 20).dp))
        if (conversationPhrases != null) {
            for(phrase in conversationPhrases) {
                EmergencyPhraseView(
                    phrase = phrase,
                    screenSize = screenSize,
                    context = context,
                    textToSpeechViewModel = textToSpeechViewModel
                )
            }
        }
        Spacer(modifier = Modifier.height((screenSize/6 - 20).dp))
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                modifier = Modifier.size((screenSize/6 + 40).dp).combinedClickable(
                    onClick = {},
                    onLongClick = {
                        textToSpeechViewModel.customTextToSpeech(
                            context,
                            context.getString(R.string.hospital_tts),
                            Locale.getDefault()
                        )
                    }
                ),
                imageVector = Icons.Rounded.LocalHospital,
                contentDescription = "Hospital"
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = context.getString(R.string.hospital),
                fontWeight = FontWeight.Bold,
                fontSize = (screenSize/6 - 40).sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6 - 30).dp))
        if (hospitalPhrases != null) {
            for(phrase in hospitalPhrases) {
                EmergencyPhraseView(
                    phrase = phrase,
                    screenSize = screenSize,
                    context = context,
                    textToSpeechViewModel = textToSpeechViewModel
                )
            }
        }
        Spacer(modifier = Modifier.height((screenSize/6 - 20).dp))
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                modifier = Modifier.size((screenSize/6 + 40).dp).combinedClickable(
                    onClick = {},
                    onLongClick = {
                        textToSpeechViewModel.customTextToSpeech(
                            context,
                            context.getString(R.string.school_tts),
                            Locale.getDefault()
                        )
                    }
                ),
                imageVector = Icons.Rounded.School,
                contentDescription = "School"
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = context.getString(R.string.school),
                fontWeight = FontWeight.Bold,
                fontSize = (screenSize/6 - 40).sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6 - 20).dp))
        if (schoolPhrases != null) {
            for(phrase in schoolPhrases) {
                EmergencyPhraseView(
                    phrase = phrase,
                    screenSize = screenSize,
                    context = context,
                    textToSpeechViewModel = textToSpeechViewModel
                )
            }
        }
        Spacer(modifier = Modifier.height((screenSize/6 - 20).dp))
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                modifier = Modifier.size((screenSize/6 + 40).dp).combinedClickable(
                    onClick = {},
                    onLongClick = {
                        textToSpeechViewModel.customTextToSpeech(
                            context,
                            context.getString(R.string.police_tts),
                            Locale.getDefault()
                        )
                    }
                ),
                imageVector = Icons.Rounded.LocalPolice,
                contentDescription = "Police"
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = context.getString(R.string.police),
                fontWeight = FontWeight.Bold,
                fontSize = (screenSize/6 - 40).sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6 - 20).dp))
        if (policePhrases != null) {
            for(phrase in policePhrases) {
                EmergencyPhraseView(
                    phrase = phrase,
                    screenSize = screenSize,
                    context = context,
                    textToSpeechViewModel = textToSpeechViewModel
                )
            }
        }
        Spacer(modifier = Modifier.height((screenSize/6 + 20).dp))
    }
}
/**
 * Function that filters phrases based on
 * context and language.
 *
 * @param emergencyPhraseViewModel emergency phrases ViewModel.
 * @param context application context.
 * @param selectedFilter selected language.
 *
 * @return a list of filtered phrases.
 * */
fun filterPhrases(
    emergencyPhraseViewModel: EmergencyPhraseViewModel,
    context: String,
    selectedFilter: Int
): List<EmergencyPhrase>?{
    return when(selectedFilter) {
        1 -> emergencyPhraseViewModel.dataList.value?.filter { it.context == context && it.language == Languages.ENGLISH }
            ?.toList()

        2 -> emergencyPhraseViewModel.dataList.value?.filter { it.context == context && it.language == Languages.ITALIAN }
            ?.toList()

        3 -> emergencyPhraseViewModel.dataList.value?.filter { it.context == context && it.language == Languages.FRENCH }
            ?.toList()

        else -> {
            emergencyPhraseViewModel.dataList.value?.filter { it.context == context }
                ?.toList()
        }
    }
}