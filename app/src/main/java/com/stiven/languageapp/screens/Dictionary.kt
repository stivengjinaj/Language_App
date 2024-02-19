package com.stiven.languageapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stiven.languageapp.R
import com.stiven.languageapp.model.Word
import com.stiven.languageapp.view.LogoBanner
import com.stiven.languageapp.viewmodels.WordViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dictionary(
    wordViewModel: WordViewModel
) {
    val screenSize = LocalConfiguration.current.screenWidthDp
    val context = LocalContext.current
    val textToSearch by remember {
        mutableStateOf("")
    }
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
                        WordCard(word = word, screenSize = screenSize)
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height((screenSize/4).dp))
            }
        }
    }
}

@Composable
fun WordCard(word: Word, screenSize: Int){
    val expanded = remember { mutableStateOf (false) }

    Card(
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 20.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable(
                onClick = {
                    expanded.value = !expanded.value
                }
            )
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = word.italian.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                color = MaterialTheme.colorScheme.secondary,
                fontStyle = FontStyle.Normal,
                fontSize = (screenSize/6 - 35).sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }
        if (expanded.value) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "English",
                        color = Color.Green,
                        fontSize = (screenSize/6 - 45).sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = word.english,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = (screenSize/6 - 50).sp
                    )
                }
                Spacer(modifier = Modifier.width((screenSize/6).dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "French",
                        color = Color.Green,
                        fontSize = (screenSize/6 - 45).sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = word.french,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = (screenSize/6 - 50).sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

fun searchQuery(toSearch: String, wordViewModel: WordViewModel): List<Word> {
    val allWords = wordViewModel.dataList.value ?: return emptyList()

    val filteredWords = allWords.filter { word ->
        word.italian.lowercase().startsWith(toSearch.lowercase()) ||
                word.english.lowercase().startsWith(toSearch.lowercase()) ||
                word.french.lowercase().startsWith(toSearch.lowercase())
    }
    return filteredWords
}