package com.stiven.languageapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stiven.languageapp.R
import com.stiven.languageapp.view.LetterView
import com.stiven.languageapp.viewmodels.LetterViewModel
import com.stiven.languageapp.viewmodels.SpeechToTextViewModel

/**
 * Screen containing all letters of alphabet.
 *
 * @param letterViewModel view-model that handles Letter's table operations.
 * @param speechToTextViewModel speech-to-text viewModel.
 * */
@Composable
fun Alphabet(letterViewModel: LetterViewModel, speechToTextViewModel: SpeechToTextViewModel) {
    val screenSize = LocalConfiguration.current.screenHeightDp / 20
    val context = LocalContext.current
    val letterModels = letterViewModel.dataList
    val alphabet = getAlphabet()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height((screenSize).dp))
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = context.getString(R.string.alphabet),
                fontSize = (screenSize).sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.inversePrimary
            )
        }
        Spacer(modifier = Modifier.height((screenSize).dp))
        for(letter in alphabet){
            LetterView(letter = letter, speechToTextViewModel, letterModels)
            Spacer(modifier = Modifier.height(30.dp))
        }
        Spacer(modifier = Modifier.height((screenSize + 10).dp))
    }
}

/**
 * Function that creates a list containing each letter of the alphabet.
 *
 * @return list of string with all letters.
 * */
fun getAlphabet(): List<String> {
    return ('A'..'Z').map { it.toString() }
}
