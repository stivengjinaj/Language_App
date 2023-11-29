package com.stiven.languageapp

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stiven.languageapp.entities.Word
import com.stiven.languageapp.viewmodels.WordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun InitialPage(wordViewModel: WordViewModel) {
    val lazyWords = mutableStateListOf<Word>()
    var wordToSearch by remember { mutableStateOf(TextFieldValue("")) }
    var wordState by remember { mutableStateOf<Word?>(null) }
    Column(
        Modifier.background(Color(android.graphics.Color.parseColor("#1DAEFC"))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Row (horizontalArrangement = Arrangement.Center) {
            Text("Welcome to TrioLingo", style = TextStyle(color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold))
        }
        Spacer(modifier = Modifier.height(150.dp))
        Row (modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
            OutlinedButton(
                modifier = Modifier.width((LocalConfiguration.current.screenWidthDp - 130).dp),
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(50),
                border = BorderStroke(2.dp,Color.White)
            ) {
                Text("My courses", style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold))
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OutlinedButton(
                modifier = Modifier.width((LocalConfiguration.current.screenWidthDp - 130).dp),
                onClick = {
                    val wordList = wordViewModel.dataList
                    for (word in wordList.value!!){
                        word.learnt = 0
                    }
                    if(wordList.value != null){
                        wordViewModel.updateAll(wordList.value!!)
                    }
                },
                shape = RoundedCornerShape(50),
                border = BorderStroke(2.dp,Color.White)
            ) {
                Text("New Course", style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold))
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OutlinedButton(
                modifier = Modifier.width((LocalConfiguration.current.screenWidthDp - 130).dp),
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(50),
                border = BorderStroke(2.dp,Color.White),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text("Emergency", style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold))
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        OutlinedTextField(
            value = wordToSearch,
            onValueChange = {
                wordToSearch = it
            },
            label = { Text(text = "Enter word to search ") }
        )
        Spacer(modifier = Modifier.height(40.dp))
        LazyColumn{
            items(items = lazyWords){item ->
                Words(word = item)
            }
        }
    }
}

@Composable
fun Words(word: Word) {
    Text(text = "Word: "+word.word+" Language: "+word.language+" Learnt: "+word.learnt)
}
