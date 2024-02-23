package com.stiven.languageapp.screens.secondCloud

import android.media.MediaPlayer
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.stiven.languageapp.R
import com.stiven.languageapp.model.LetterLearnt
import com.stiven.languageapp.navigation.Graph
import com.stiven.languageapp.navigation.SecondCloudNavGraph
import com.stiven.languageapp.utils.LearningType
import com.stiven.languageapp.view.LogoBannerNavigation
import com.stiven.languageapp.view.WritingML
import com.stiven.languageapp.viewmodels.LettersLearntViewModel
import com.stiven.languageapp.viewmodels.StudentViewModel
import kotlinx.coroutines.Dispatchers

@Composable
fun AlphabetWriting(
    navController: NavHostController,
    rootNavController: NavHostController,
    studentViewModel: StudentViewModel,
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
            navController = navController,
            studentId = studentId,
            studentViewModel = studentViewModel,
            lettersLearntViewModel = lettersLearntViewModel,
            screenSize = screenSize
        )
    }
}

@Composable
fun WritingView(
    navController: NavHostController,
    studentId: String,
    studentViewModel: StudentViewModel,
    lettersLearntViewModel: LettersLearntViewModel,
    screenSize: Int
){
    val context = LocalContext.current
    val student = studentViewModel.dataList.value?.find { it.id.toString() == studentId }
    val letters = "ABCDEFGHILMNOPQRSTUVZ"
    val allLettersLearnt = remember {
        lettersLearntViewModel.dataList.value
    }
    val writtenLetters = allLettersLearnt?.filter {
        it.learningType == LearningType.WRITTEN &&
                it.studentId == studentId
    }?.toList()

    val currentLetterIndex = remember {
        mutableIntStateOf(writtenLetters?.size ?: 0)
    }
    val correctness = remember {
        mutableStateOf(false)
    }
    val currentLetter =
        if(currentLetterIndex.intValue < 21)
            letters[currentLetterIndex.intValue]
        else
            letters[20]

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
    if (allLettersLearnt != null) {
        if (writtenLetters != null) {
            if(writtenLetters.none { it.letterLearnt == currentLetter.toString() && currentLetterIndex.intValue < 21}){
                WritingML(
                    letterToDraw = currentLetter,
                    onCorrectWriting = {
                        if(student != null){
                            lettersLearntViewModel.insertLetterLearnt(
                                LetterLearnt(
                                    studentId = studentId,
                                    letterLearnt = currentLetter.uppercase(),
                                    learningType = LearningType.WRITTEN
                                )
                            )
                        }
                    },
                    correctness = correctness,
                    onIncorrectWriting = {
                        MediaPlayer.create(context, R.raw.incorrect).start()
                    }
                )
            }
        }
    }
    if(correctness.value){
        if(currentLetterIndex.intValue < 1){
            LaunchedEffect(Dispatchers.IO){
                MediaPlayer.create(context, R.raw.correct).start()
                currentLetterIndex.intValue = currentLetterIndex.intValue + 1
                correctness.value = false
            }
        }
        else {
            LaunchedEffect(Dispatchers.IO){
                MediaPlayer.create(context, R.raw.finish).start()
                navController.navigate(SecondCloudNavGraph.CHANGE_NAME)
            }
        }
    }
}











