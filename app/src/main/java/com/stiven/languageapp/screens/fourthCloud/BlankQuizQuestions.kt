package com.stiven.languageapp.screens.fourthCloud

import android.media.MediaPlayer
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
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.rounded.ArrowDownward
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stiven.languageapp.R
import com.stiven.languageapp.model.BlankQuiz
import com.stiven.languageapp.model.QuizAnswer
import com.stiven.languageapp.navigation.FourthCloudNavGraph
import com.stiven.languageapp.navigation.Graph
import com.stiven.languageapp.utils.QuestionType
import com.stiven.languageapp.view.LogoBannerNavigation
import com.stiven.languageapp.viewmodels.BlankQuizViewModel
import com.stiven.languageapp.viewmodels.QuizAnswerViewModel
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

/**
 * View function for blank question quiz.
 *
 * @param studentId Student's id.
 * @param navController Fourth cloud navigation controller.
 * @param rootNavController Root navigation controller.
 * @param studentViewModel Student's ViewModel.
 * @param textToSpeechViewModel Text-to-speech ViewModel.
 * @param quizAnswerViewModel Quiz answers ViewModel.
 * @param blankQuizViewModel Blank questions ViewModel.
 * */
@Composable
fun BlankQuizQuestions(
    studentId: String,
    navController: NavHostController,
    rootNavController: NavHostController,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    quizAnswerViewModel: QuizAnswerViewModel,
    blankQuizViewModel: BlankQuizViewModel
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
        BlankQuestionView(
            navController = navController,
            studentId = studentId,
            blankQuizViewModel = blankQuizViewModel,
            screenSize = screenSize,
            quizAnswerViewModel = quizAnswerViewModel
        )
    }
}

/**
 * View-logics for blank questions quiz.

 * @param navController Fourth cloud navigation controller.
 * @param studentId Student's id.
 * @param blankQuizViewModel Blank questions ViewModel.
 * @param screenSize Screen size.
 * @param quizAnswerViewModel Quiz answers ViewModel.
 * */
@Composable
fun BlankQuestionView(
    navController: NavHostController,
    studentId: String,
    blankQuizViewModel: BlankQuizViewModel,
    screenSize: Int,
    quizAnswerViewModel: QuizAnswerViewModel
){
    val context = LocalContext.current
    val questions = remember {
        mutableStateOf(blankQuizViewModel.dataList.value?.map { it }?.shuffled())
    }
    val questionIndex = remember {
        mutableIntStateOf(0)
    }
    val selectedAnswer = remember {
        mutableIntStateOf(0)
    }
    val incorrectAnswers = remember {
        mutableListOf<BlankQuiz>()
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
                text = formatQuestion(currentQuestion.question)+".",
                maxLines = 2,
                overflow = TextOverflow.Clip,
                color = MaterialTheme.colorScheme.inversePrimary,
                fontWeight = FontWeight.Bold,
                fontSize = (screenSize / 6 - 40).sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6).dp))
        Row(
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
                    else Icons.Filled.RadioButtonUnchecked,
                    contentDescription = "Option1",
                    tint = MaterialTheme.colorScheme.inversePrimary
                )
            }
            Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
            ClickableText(
                text = AnnotatedString(currentQuestion.first),
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
        Spacer(modifier = Modifier.height((screenSize/6).dp))
        Row(
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
                    else Icons.Filled.RadioButtonUnchecked,
                    contentDescription = "Option2",
                    tint = MaterialTheme.colorScheme.inversePrimary
                )
            }
            Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
            ClickableText(
                text = AnnotatedString(currentQuestion.second),
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
        Spacer(modifier = Modifier.height((screenSize/6).dp))
        Row(
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
                    else Icons.Filled.RadioButtonUnchecked,
                    contentDescription = "Option3",
                    tint = MaterialTheme.colorScheme.inversePrimary
                )
            }
            Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
            ClickableText(
                text = AnnotatedString(currentQuestion.third),
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
                    if(validation(currentQuestion, selectedAnswer.intValue)){
                        insertAnswer(quizAnswerViewModel, currentQuestion, studentId)
                        MediaPlayer.create(context, R.raw.correct).start()
                        questionIndex.intValue += 1
                    }else{
                        MediaPlayer.create(context, R.raw.incorrect).start()
                        incorrectAnswers.add(currentQuestion)
                        questionIndex.intValue += 1
                    }
                    if(questionIndex.intValue >= questions.value?.size!!){
                        questionIndex.intValue -= 1
                        if(incorrectAnswers.isNotEmpty()){
                            questionIndex.intValue = 0
                            questions.value = incorrectAnswers.map { it }.shuffled()
                            incorrectAnswers.clear()
                        }else{
                            navController.navigate(FourthCloudNavGraph.FINISHED_CLOUD)
                            MediaPlayer.create(context, R.raw.finish).start()
                        }
                    }
                    selectedAnswer.intValue = 0
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
 * Question answer validation.
 *
 * @param question Question to validate.
 * @param selectedAnswer Selected option.
 *
 * @return True if answer is correct, false otherwise.
 * */
private fun validation(question: BlankQuiz, selectedAnswer: Int): Boolean{
    return when(question.answer){
        "first" -> {
            selectedAnswer == 1
        }
        "second" -> {
            selectedAnswer == 2
        }
        "third" -> {
            selectedAnswer == 3
        }
        else -> {
            selectedAnswer == 0
        }
    }
}
/**
 * Question string formatter.
 *
 * @param question Question to format.
 *
 * @return A formatted string.
 * */
private fun formatQuestion(question: String): String{
    return question.replace("--","_____")
}
/**
 * Function that checks if student already answered this question.
 * If not, the answer is registered.
 *
 * @param quizAnswerViewModel Answered Question view-model-
 * @param currentQuestion Answered question.
 * @param studentId Student's id.
 * */
private fun insertAnswer(
    quizAnswerViewModel: QuizAnswerViewModel,
    currentQuestion: BlankQuiz,
    studentId: String
){
    if (quizAnswerViewModel.dataList.value?.find {
        it.questionType == QuestionType.FILL_BLANK && it.studentId == studentId && it.question ==  currentQuestion.question
    } == null) {
        quizAnswerViewModel.insertQuizAnswer(
            QuizAnswer(
                id = 0,
                studentId = studentId,
                question = currentQuestion.question,
                questionType = QuestionType.FILL_BLANK
            )
        )
    }
}