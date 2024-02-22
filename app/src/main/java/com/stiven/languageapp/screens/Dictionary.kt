package com.stiven.languageapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.stiven.languageapp.R
import com.stiven.languageapp.model.Word
import com.stiven.languageapp.view.LogoBanner
import com.stiven.languageapp.view.WordView
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import com.stiven.languageapp.viewmodels.WordViewModel
import java.util.Locale

/**
 * Composable screen for all the words present and their meanings
 * in english, french and italian.
 *
 * @param wordViewModel word's viewModel.
 * @param textToSpeechViewModel text-to-speech viewModel.
 * */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Dictionary(
    wordViewModel: WordViewModel,
    textToSpeechViewModel: TextToSpeechViewModel
) {
    val screenSize = LocalConfiguration.current.screenWidthDp
    val context = LocalContext.current
    val queryState = remember { mutableStateOf(TextFieldValue()) }
    val allWords = remember {
        mutableStateOf(wordViewModel.dataList.value)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoBanner(screenSize = screenSize)
        Spacer(modifier = Modifier.height((screenSize / 6 - 50).dp))
        TextField(
            modifier = Modifier
                .width((screenSize - 10).dp)
                .clip(RoundedCornerShape(30.dp))
                .border(
                    BorderStroke(2.dp, MaterialTheme.colorScheme.inversePrimary),
                    RoundedCornerShape(30.dp)
                ).combinedClickable(
                    onClick = {},
                    onLongClick = {
                        textToSpeechViewModel.customTextToSpeech(
                            context,
                            context.getString(R.string.search_tts),
                            Locale.getDefault()
                        )
                    }
                ),
            value = queryState.value,
            onValueChange = {
                queryState.value = it
                allWords.value = searchQuery(it.text, wordViewModel)
            },
            placeholder = { Text(text = context.getString(R.string.search), color = MaterialTheme.colorScheme.inversePrimary) },
            leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.inversePrimary) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                focusedTextColor = MaterialTheme.colorScheme.inversePrimary
            )
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ){
            if(allWords.value != null){
                for (word in allWords.value!!) {
                    item{
                        WordView(
                            word = word,
                            screenSize = screenSize,
                            textToSpeechViewModel = textToSpeechViewModel
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height((screenSize/4).dp))
            }
        }
    }
}
/**
 * Query executed for searching.
 *
 * @param toSearch word to search.
 * @param wordViewModel all words.
 * @return a list with the result of the query.
 * */
fun searchQuery(toSearch: String, wordViewModel: WordViewModel): List<Word> {
    val allWords = wordViewModel.dataList.value ?: return emptyList()

    val filteredWords = allWords.filter { word ->
        word.italian.lowercase().startsWith(toSearch.lowercase()) ||
                word.english.lowercase().startsWith(toSearch.lowercase()) ||
                word.french.lowercase().startsWith(toSearch.lowercase())
    }
    return filteredWords
}