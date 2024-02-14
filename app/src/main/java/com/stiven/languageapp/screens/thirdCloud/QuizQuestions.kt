package com.stiven.languageapp.screens.thirdCloud

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stiven.languageapp.R
import com.stiven.languageapp.model.Quiz
import com.stiven.languageapp.model.SingleQuestion
import com.stiven.languageapp.navigation.Graph
import com.stiven.languageapp.view.LogoBannerNavigation
import com.stiven.languageapp.viewmodels.QuizViewModel
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import java.util.Locale

/**
 * Composable that contains the page where the quiz is.
 *
 * @param studentId student's id.
 * @param rootNavController root navigation controller.
 * @param studentViewModel student's view-model.
 * @param textToSpeechViewModel text-to-speech view-model
 * @param quizViewModel quiz questions view-model.
 * */
@Composable
fun QuizQuestions(
    studentId: String,
    rootNavController: NavHostController,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    quizViewModel: QuizViewModel
){
    val screenSize = LocalConfiguration.current.screenWidthDp
    Column(
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
        QuestionView(
            studentId = studentId,
            quizViewModel = quizViewModel,
            studentViewModel = studentViewModel,
            screenSize = screenSize
        )
    }
}

/**
 * Composable containing the logics and the view behind every question.
 * @param studentId student's id.
 * @param studentViewModel student's view-model.
 * @param quizViewModel quiz questions view-model.
 * @param screenSize screen size.
 * */
@Composable
fun QuestionView(
    studentId: String,
    quizViewModel: QuizViewModel,
    studentViewModel: StudentViewModel,
    screenSize: Int
) {
    val context = LocalContext.current
    val student = studentViewModel.dataList.value?.find { it.id.toString() == studentId }
    val questions = remember {
        mutableStateOf(quizViewModel.dataList.value?.map { prepareQuestion(it) }?.shuffled())
    }
    val questionIndex = remember {
        mutableIntStateOf(0)
    }
    val selectedAnswer = remember {
        mutableIntStateOf(0)
    }
    val incorrectAnswers = remember {
        mutableListOf<SingleQuestion>()
    }
    val currentQuestion = if(questions.value?.isNotEmpty() == true || incorrectAnswers.isNotEmpty()){
        questions.value?.get(questionIndex.intValue)
    }else {
        null
    }
    if(currentQuestion != null){
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = currentQuestion.question,
                color = MaterialTheme.colorScheme.inversePrimary,
                fontWeight = FontWeight.Bold,
                fontSize = (screenSize / 6 - 20).sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6).dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
            IconButton(onClick = {
                selectedAnswer.intValue = 1
            }) {
                Icon(
                    imageVector = if(selectedAnswer.intValue == 1) Icons.Filled.Circle
                                    else Icons.Rounded.RadioButtonUnchecked,
                    contentDescription = "Option1",
                    tint = MaterialTheme.colorScheme.inversePrimary
                )
            }
            Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
            ClickableText(
                text = AnnotatedString(currentQuestion.options[0]),
                onClick = {
                    selectedAnswer.intValue = 1
                },
                style = TextStyle(
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = (screenSize / 6 - 40).sp,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.SansSerif
                )
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6 - 20).dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
            IconButton(onClick = {
                selectedAnswer.intValue = 2
            }) {
                Icon(
                    imageVector = if(selectedAnswer.intValue == 2) Icons.Filled.Circle
                                    else Icons.Rounded.RadioButtonUnchecked,
                    contentDescription = "Option2",
                    tint = MaterialTheme.colorScheme.inversePrimary
                )
            }
            Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
            ClickableText(
                text = AnnotatedString(currentQuestion.options[1]),
                onClick = {
                    selectedAnswer.intValue = 2
                },
                style = TextStyle(
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = (screenSize / 6 - 40).sp,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.SansSerif
                )
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6 - 20).dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
            IconButton(onClick = {
                selectedAnswer.intValue = 3
            }) {
                Icon(
                    imageVector = if(selectedAnswer.intValue == 3) Icons.Filled.Circle
                                    else Icons.Rounded.RadioButtonUnchecked,
                    contentDescription = "Option3",
                    tint = MaterialTheme.colorScheme.inversePrimary
                )
            }
            Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
            ClickableText(
                text = AnnotatedString(currentQuestion.options[2]),
                onClick = {
                    selectedAnswer.intValue = 3
                },
                style = TextStyle(
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = (screenSize / 6 - 40).sp,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.SansSerif
                )
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6).dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            IconButton(
                onClick = {
                    if(selectedAnswer.intValue == currentQuestion.answer){
                        MediaPlayer.create(context, R.raw.correct).start()
                        questionIndex.intValue += 1
                        selectedAnswer.intValue = 0
                        if(questionIndex.intValue >= questions.value?.size!!){
                            questionIndex.intValue = 0
                            questions.value = incorrectAnswers.map { it }.shuffled()
                            if(incorrectAnswers.isNotEmpty()){
                                incorrectAnswers.clear()
                            }else {
                                MediaPlayer.create(context, R.raw.finish).start()

                            }
                        }
                    }else{
                        incorrectAnswers.add(currentQuestion)
                        questionIndex.intValue += 1
                        selectedAnswer.intValue = 0
                    }
                    Log.d("INDEX", "${questionIndex.intValue}")
                    Log.d("QUESTIONS", "${questions.value!!.size}")
                    Log.d("INCORRECT", "${incorrectAnswers.size}")
                }
            ) {
                Icon(
                    modifier = Modifier
                        .size((screenSize / 5).dp)
                        .rotate(-90f),
                    imageVector = Icons.Rounded.ArrowDownward,
                    contentDescription = "Validate",
                    tint = MaterialTheme.colorScheme.inversePrimary
                )
            }
        }
    }
}

/**
 * Function that organized a question from database.
 *
 * @param quizQuestion question to organize.
 *
 * @return returns a SingleQuestion object.
 * */
fun prepareQuestion(quizQuestion: Quiz): SingleQuestion{
    when(Locale.getDefault()){
        Locale.ENGLISH -> {
            return SingleQuestion(
                quizQuestion.question,
                listOf(
                    quizQuestion.firstEn,
                    quizQuestion.secondEn,
                    quizQuestion.thirdEn
                ),
                getQuestionAnswerIndex(quizQuestion)
            )
        }
        Locale.FRENCH -> {
            return SingleQuestion(
                quizQuestion.question,
                listOf(
                    quizQuestion.firstFr,
                    quizQuestion.secondFr,
                    quizQuestion.thirdFr
                ),
                getQuestionAnswerIndex(quizQuestion)
            )
        }
        else -> {
            return SingleQuestion(
                quizQuestion.question,
                listOf(
                    quizQuestion.firstEn,
                    quizQuestion.secondEn,
                    quizQuestion.thirdEn
                ),
                getQuestionAnswerIndex(quizQuestion)
            )
        }
    }
}
/**
 * Function that maps the answer data from database to integer.
 *
 * @param quizQuestion question to check for answer.
 *
 * @return an integer representing the answer's index.
 * */
fun getQuestionAnswerIndex(quizQuestion: Quiz): Int{
    return when(quizQuestion.answer){
        "first" -> 1
        "second" -> 2
        "third" -> 3
        else -> 0
    }
}