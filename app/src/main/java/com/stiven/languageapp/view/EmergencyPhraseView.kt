package com.stiven.languageapp.view

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stiven.languageapp.model.EmergencyPhrase
import com.stiven.languageapp.utils.Languages
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import java.util.Locale

/**
 * View for an emergency phrase
 *
 * @param phrase phrase to view.
 * @param screenSize current screen width.
 * @param context context of the view.
 * @param textToSpeechViewModel text to speech ViewModel.
 * */
@Composable
fun EmergencyPhraseView(
    phrase: EmergencyPhrase,
    screenSize: Int,
    context: Context,
    textToSpeechViewModel: TextToSpeechViewModel
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box (
            modifier = Modifier.width((screenSize/2).dp)
        ){
            Text(
                text = phrase.phrase,
                fontWeight = FontWeight.Bold,
                fontSize = (screenSize/6 - 50).sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(
            modifier = Modifier.size((screenSize/6 - 40).dp),
            onClick = {
                textToSpeechViewModel.textToSpeech(context, "")
                when(phrase.language) {
                    Languages.ENGLISH -> {
                        textToSpeechViewModel.customTextToSpeech(
                            context,
                            phrase.phrase,
                            Locale.ENGLISH
                        )
                    }
                    Languages.ITALIAN -> {
                        textToSpeechViewModel.customTextToSpeech(
                            context,
                            phrase.phrase,
                            Locale.ITALIAN
                        )
                    }
                    Languages.FRENCH -> {
                        textToSpeechViewModel.customTextToSpeech(
                            context,
                            phrase.phrase,
                            Locale.FRENCH
                        )
                    }
                }
            }
        ) {
            Icon(
                modifier = Modifier.size((screenSize/6 - 40).dp),
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = "Play voice"
            )
        }

    }
    Spacer(modifier = Modifier.height((screenSize/6 - 30).dp))
}