package com.stiven.languageapp.screens.firstCloud


import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.stiven.languageapp.navigation.FirstCloudRoutes
import com.stiven.languageapp.navigation.Graph
import com.stiven.languageapp.utils.LearningType
import com.stiven.languageapp.view.LogoBannerNavigation
import com.stiven.languageapp.viewmodels.LetterViewModel
import com.stiven.languageapp.viewmodels.LettersLearntViewModel
import com.stiven.languageapp.viewmodels.SpeechActions
import com.stiven.languageapp.viewmodels.SpeechToTextViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

/**
 * Function that sets up letter pronunciation view.
 *
 * @param navController Current cloud navigation controller.
 * @param rootNavController Root navigation controller.
 * @param speechToTextViewModel Speech-to-text ViewModel.
 * @param textToSpeechViewModel Text-to-speech ViewModel.
 * @param lettersLearntViewModel Letters learnt ViewModel.
 * @param studentId Student's id.
 * */
@Composable
fun AlphabetPronouncing(
    navController: NavHostController,
    rootNavController: NavHostController,
    speechToTextViewModel: SpeechToTextViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    lettersLearntViewModel: LettersLearntViewModel,
    letterViewModel: LetterViewModel,
    studentId: String
){
    val screenSize = LocalConfiguration.current.screenWidthDp
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        LogoBannerNavigation(
            screenSize = screenSize,
            onBackButtonClick = {
                rootNavController.navigate(Graph.LESSONS+"/$studentId")
            }
        )
        Spacer(modifier = Modifier.height((screenSize / 6).dp))
        LetterView(
            studentId = studentId,
            navController = navController,
            textToSpeechViewModel = textToSpeechViewModel,
            speechToTextViewModel = speechToTextViewModel,
            lettersLearntViewModel = lettersLearntViewModel,
            letterViewModel = letterViewModel,
            screenSize = screenSize
        )
    }
}

/**
 * Function that connects the logics and the view of letter controller.
 *
 * @param studentId Student's id.
 * @param navController First cloud navigation controller.
 * @param textToSpeechViewModel Text-to-speech ViewModel.
 * @param speechToTextViewModel Speech-to-text ViewModel.
 * @param lettersLearntViewModel Letters learnt ViewModel.
 * @param letterViewModel Letters similarities ViewModel.
 * @param screenSize Screen width.
 * */
@Composable
fun LetterView(
    studentId: String,
    navController: NavHostController,
    textToSpeechViewModel: TextToSpeechViewModel,
    speechToTextViewModel: SpeechToTextViewModel,
    lettersLearntViewModel: LettersLearntViewModel,
    letterViewModel: LetterViewModel,
    screenSize: Int
){
    val context = LocalContext.current
    val letters = "abcdefghilmnopqrstuvz"
    val mappedLetters = remember {
        letterViewModel.dataList.value
    }
    val recording = remember {
        mutableStateOf(false)
    }
    val pronunciation = remember {
        mutableStateOf(speechToTextViewModel.state.value)
    }
    val currentLetterIndex = remember {
        mutableIntStateOf( 0)
    }
    val validation = remember {
        mutableStateOf(false)
    }
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        if(validation.value){
            Box (
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .size((screenSize / 2).dp)
                    .background(Color.Green)
            ){
                Icon(
                    modifier = Modifier.size((screenSize / 2 - 10).dp),
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Correct",
                    tint = Color.White
                )
            }
            LaunchedEffect(Dispatchers.IO){
                if(validation.value){
                    delay(1000)
                    validation.value = false
                }
            }
        }else{
            Text(
                text = letters[currentLetterIndex.intValue].uppercase(),
                color = MaterialTheme.colorScheme.inversePrimary,
                fontWeight = FontWeight.Bold,
                fontSize = (screenSize / 2).sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif
            )
        }
        LaunchedEffect(Dispatchers.IO){
            if(validation.value){
                delay(1000)
                validation.value = false
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            modifier = Modifier.size((screenSize/4).dp),
            onClick = {
                textToSpeechViewModel.italianTextToSpeech(context, letters[currentLetterIndex.intValue].toString())
            }
        ) {
            Icon(
                modifier = Modifier.size((screenSize/8).dp),
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = "Listen Letter",
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        IconButton(
            modifier = Modifier.size((screenSize/4).dp),
            onClick = {
                if(recording.value){
                    speechToTextViewModel.send(SpeechActions.EndRecord)
                    recording.value = false
                }else {
                    speechToTextViewModel.send(SpeechActions.StartRecord)
                    recording.value = true
                }
            }
        ) {
            Icon(
                modifier = Modifier.size((screenSize/8).dp),
                imageVector = if(recording.value) Icons.Rounded.Stop else Icons.Rounded.Mic,
                contentDescription = "Pronounce Letter",
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
    pronunciation.value = speechToTextViewModel.state.value
    if(mappedLetters?.isNotEmpty() == true){
        for(letter in mappedLetters){
            if(letter.similarTo.lowercase() == pronunciation.value.lowercase() && letter.letter.lowercase() == letters[currentLetterIndex.intValue].lowercase()
                || letters[currentLetterIndex.intValue].lowercase() == pronunciation.value.lowercase()){
                learntLetter(lettersLearntViewModel,studentId,letters[currentLetterIndex.intValue].uppercase())
                LaunchedEffect(Dispatchers.IO){
                    MediaPlayer.create(context, R.raw.correct).start()
                }
                validation.value = true
                if(currentLetterIndex.intValue == 20){
                    MediaPlayer.create(context, R.raw.finish).start()
                    navController.navigate(FirstCloudRoutes.FINISHED_CLOUD)
                }else{
                    currentLetterIndex.intValue += 1
                }
                break
            }
        }
    }
}
/**
 * Function that check if the letter has already been registered.
 *
 * @param lettersLearntViewModel learnt letters viewModel.
 * @param studentId student's id.
 * @param letter letter to check/insert.
 * */
fun learntLetter(
    lettersLearntViewModel: LettersLearntViewModel,
    studentId: String,
    letter: String
){
    if(lettersLearntViewModel.dataList.value?.find {
            it.letterLearnt.uppercase() == letter && it.learningType == LearningType.PRONOUNCING && it.studentId == studentId
        } == null
    ){
        lettersLearntViewModel.insertLetterLearnt(
            LetterLearnt(
                letterLearnt = letter,
                studentId = studentId,
                learningType = LearningType.PRONOUNCING
            )
        )
    }
}