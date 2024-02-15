package com.stiven.languageapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stiven.languageapp.screens.FinishedCloud
import com.stiven.languageapp.screens.fourthCloud.BlankQuizQuestions
import com.stiven.languageapp.screens.fourthCloud.FourthIntroduction
import com.stiven.languageapp.viewmodels.BlankQuizViewModel
import com.stiven.languageapp.viewmodels.QuizAnswerViewModel
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
/**
 * Navigation graph when the student clicks the fourth cloud.
 *
 * @param studentId Student's id.
 * @param rootNavController Root navigation controller.
 * @param studentViewModel Student's ViewModel.
 * @param navController Navigation Controller of the current cloud.
 * @param textToSpeechViewModel Text-to-speech ViewModel.
 * @param blankQuizViewModel Blank questions ViewModel.
 * @param quizAnswerViewModel Quiz answers ViewModel.
 * */
@Composable
fun FourthCloudNavGraph(
    studentId: String,
    rootNavController: NavHostController,
    studentViewModel: StudentViewModel,
    navController: NavHostController,
    textToSpeechViewModel: TextToSpeechViewModel,
    blankQuizViewModel: BlankQuizViewModel,
    quizAnswerViewModel: QuizAnswerViewModel
) {
    val student = studentViewModel.dataList.value?.find { it.id.toString() == studentId }
    NavHost(
        navController = navController,
        startDestination = FourthCloudNavGraph.FOURTH_INTRODUCTION
    ){
        composable(route = FourthCloudNavGraph.FOURTH_INTRODUCTION){
            FourthIntroduction(
                studentId = studentId,
                navController = navController,
                rootNavController = rootNavController,
                textToSpeechViewModel = textToSpeechViewModel
            )
        }
        composable(route = FourthCloudNavGraph.BLANK_QUIZ){
            BlankQuizQuestions(
                studentId = studentId,
                navController = navController,
                rootNavController = rootNavController,
                studentViewModel = studentViewModel,
                textToSpeechViewModel = textToSpeechViewModel,
                quizAnswerViewModel = quizAnswerViewModel,
                blankQuizViewModel = blankQuizViewModel
            )
        }
        composable(route = FourthCloudNavGraph.FINISHED_CLOUD){
            if(student != null){
                FinishedCloud(
                    studentPicture = student.picture,
                    studentPoints = student.points,
                    studentId = studentId,
                    rootNavController = rootNavController
                )
            }
        }
    }
}
/**
 * Destination routes names.
 * */
object FourthCloudNavGraph{
    const val FOURTH_CLOUD = "fourthCloud"
    const val FOURTH_INTRODUCTION = "fourthIntro"
    const val BLANK_QUIZ = "blankQuiz"
    const val FINISHED_CLOUD = "finishedCloud"
}