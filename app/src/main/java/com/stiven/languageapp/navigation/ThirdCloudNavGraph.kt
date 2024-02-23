package com.stiven.languageapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stiven.languageapp.screens.FinishedCloud
import com.stiven.languageapp.screens.thirdCloud.QuizQuestions
import com.stiven.languageapp.screens.thirdCloud.ThirdIntroduction
import com.stiven.languageapp.viewmodels.QuizAnswerViewModel
import com.stiven.languageapp.viewmodels.QuizViewModel
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

/**
 * Navigation graph inside of the third cloud.
 *
 * @param studentId student's id.
 * @param rootNavController root navigation controller.
 * @param studentViewModel student's view-model.
 * @param navController navigation controller of the current graph.
 * @param textToSpeechViewModel text-to-speech view-model.
 * @param quizViewModel quiz questions view-model.
 * */
@Composable
fun ThirdCloudNavGraph(
    studentId: String,
    rootNavController: NavHostController,
    studentViewModel: StudentViewModel,
    navController: NavHostController,
    textToSpeechViewModel: TextToSpeechViewModel,
    quizViewModel: QuizViewModel,
    quizAnswerViewModel: QuizAnswerViewModel
) {
    val student = studentViewModel.dataList.value?.find { it.id.toString() == studentId }
    NavHost(
        navController = navController,
        startDestination = ThirdCloudNavGraph.THIRD_INTRODUCTION
    ){
        composable(route = ThirdCloudNavGraph.THIRD_INTRODUCTION){
            ThirdIntroduction(
                studentId = studentId,
                rootNavController = rootNavController,
                navController = navController,
                textToSpeechViewModel = textToSpeechViewModel
            )
        }
        composable(route = ThirdCloudNavGraph.QUIZ_QUESTIONS){
            QuizQuestions(
                studentId = studentId,
                navController = navController,
                rootNavController = rootNavController,
                quizViewModel = quizViewModel,
                quizAnswerViewModel = quizAnswerViewModel
            )
        }
        composable(route = ThirdCloudNavGraph.FINISHED_CLOUD){
            if (student != null) {
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
object ThirdCloudNavGraph {
    const val THIRD_CLOUD = "thirdCloud"
    const val THIRD_INTRODUCTION = "thirdIntroduction"
    const val QUIZ_QUESTIONS = "quizQuestions"
    const val FINISHED_CLOUD = "finishedCloud"
}