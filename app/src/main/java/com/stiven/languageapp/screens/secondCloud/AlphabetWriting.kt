package com.stiven.languageapp.screens.secondCloud

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.mlkit.common.model.LocalModel
import com.stiven.languageapp.navigation.Graph
import com.stiven.languageapp.view.LogoBannerNavigation
import com.stiven.languageapp.view.WritingML
import com.stiven.languageapp.viewmodels.LettersLearntViewModel
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

@Composable
fun AlphabetWriting(
    navController: NavHostController,
    rootNavController: NavHostController,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    lettersLearntViewModel: LettersLearntViewModel,
    studentId: String
){
    val screenSize = LocalConfiguration.current.screenWidthDp
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoBannerNavigation(
            screenSize = screenSize,
            onBackButtonClick = {
                rootNavController.navigate(Graph.LESSONS+"/$studentId")
            }
        )
        Spacer(modifier = Modifier.height((screenSize / 6).dp))
        WritingView(
            studentId = studentId,
            studentViewModel = studentViewModel,
            textToSpeechViewModel = textToSpeechViewModel,
            lettersLearntViewModel = lettersLearntViewModel,
            screenSize = screenSize
        )
    }
}

@Composable
fun WritingView(
    studentId: String,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    lettersLearntViewModel: LettersLearntViewModel,
    screenSize: Int
){
    val context = LocalContext.current
    val letterRecognizer = LocalModel.Builder()
        .setAssetFilePath("lettersModel.tflite")
        .build()

    val letters = "ABCDEFGHILMNOPQRSTUVZ"
    val currentLetterIndex = remember {
        mutableIntStateOf(0)
    }
    val currentLetter = letters[currentLetterIndex.intValue]
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = currentLetter.uppercase(),
            color = MaterialTheme.colorScheme.inversePrimary,
            fontWeight = FontWeight.Bold,
            fontSize = (screenSize / 3).sp,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.SansSerif
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    WritingML(letterToDraw = 'A')
}











