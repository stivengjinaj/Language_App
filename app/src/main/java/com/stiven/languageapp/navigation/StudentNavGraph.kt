package com.stiven.languageapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stiven.languageapp.screens.Lessons
import com.stiven.languageapp.model.BottomBarScreens
import com.stiven.languageapp.viewmodels.LetterViewModel
import com.stiven.languageapp.viewmodels.LettersLearntViewModel
import com.stiven.languageapp.viewmodels.QuizViewModel
import com.stiven.languageapp.viewmodels.SpeechToTextViewModel
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

/**
 * Navigation graph for StudentPanel. Contains pages like: Lessons,
 * Exercises, Dictionary and Logout.
 *
 * @param rootNavController root's navigation controller used to go back when student logs out.
 * @param navController student panel's navigation controller.
 * @param studentViewModel student's view-model.
 * @param textToSpeechViewModel text to speech view-model.
 * @param studentId id used to get student's information in order to personalize StudentPanel.
 * @param speechToTextViewModel speech to text view-model.
 * @param letterViewModel letter's view-model.
 * @param lettersLearntViewModel Letters learnt ViewModel.
 * */
@Composable
fun StudentNavGraph(
    rootNavController: NavHostController,
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    studentId: String,
    speechToTextViewModel: SpeechToTextViewModel,
    letterViewModel: LetterViewModel,
    lettersLearntViewModel: LettersLearntViewModel,
    quizViewModel: QuizViewModel
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreens.Lessons.route
    ){
        composable(route = BottomBarScreens.Lessons.route){
            Lessons(rootNavController, navController, studentViewModel, textToSpeechViewModel, speechToTextViewModel, studentId)
        }
        composable(route = FirstCloudRoutes.FIRST_CLOUD){
            FirstCloudNavGraph(
                studentId = studentId,
                rootNavController = rootNavController,
                studentViewModel = studentViewModel ,
                navController = rememberNavController(),
                textToSpeechViewModel = textToSpeechViewModel,
                speechToTextViewModel = speechToTextViewModel,
                lettersLearntViewModel = lettersLearntViewModel
            )
        }
        composable(route = SecondCloudNavGraph.SECOND_CLOUD){
            SecondCloudNavGraph(
                studentId = studentId,
                rootNavController = rootNavController,
                studentViewModel = studentViewModel,
                navController = rememberNavController(),
                textToSpeechViewModel = textToSpeechViewModel,
                lettersLearntViewModel = lettersLearntViewModel
            )
        }
        composable(route = ThirdCloudNavGraph.THIRD_CLOUD){
            ThirdCloudNavGraph(
                studentId = studentId,
                rootNavController = rootNavController,
                studentViewModel = studentViewModel,
                navController = rememberNavController(),
                textToSpeechViewModel = textToSpeechViewModel,
                quizViewModel = quizViewModel
            )
        }
        composable(route = BottomBarScreens.Exercises.route){

        }

        composable(route = BottomBarScreens.Dictionary.route){

        }

        composable(route = BottomBarScreens.Logout.route){
            rootNavController.popBackStack()
            rootNavController.navigate(Graph.MAIN+"/classroom")
        }
    }
}