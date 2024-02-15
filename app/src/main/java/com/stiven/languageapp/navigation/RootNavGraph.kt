package com.stiven.languageapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stiven.languageapp.screens.InitialPage
import com.stiven.languageapp.screens.InitialTour
import com.stiven.languageapp.screens.SplashScreen
import com.stiven.languageapp.utils.PreferencesManager
import com.stiven.languageapp.viewmodels.BlankQuizViewModel
import com.stiven.languageapp.viewmodels.LetterViewModel
import com.stiven.languageapp.viewmodels.LettersLearntViewModel
import com.stiven.languageapp.viewmodels.QuizAnswerViewModel
import com.stiven.languageapp.viewmodels.QuizViewModel
import com.stiven.languageapp.viewmodels.SpeechToTextViewModel
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

/**
 * The root (starting point) navigation graph. It contains SplashScreen, The first
 * page that interacts with the user and 2 sub-navigation graphs: MainPanel and StudentPanel.
 *
 * @param navController the navigation controller.
 * @param studentViewModel view-model that handles operations in Students database.
 * @param textToSpeechViewModel view-model that handles text-to-speech operations.
 * @param preferencesManager manager for shared preferences.
 * @param speechToTextViewModel speech to text view-model.
 * @param letterViewModel letter's ViewModel.
 * @param lettersLearntViewModel letter's learnt ViewModel.
 * */
@Composable
fun RootNavGraph(
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    preferencesManager: PreferencesManager,
    speechToTextViewModel: SpeechToTextViewModel,
    letterViewModel: LetterViewModel,
    lettersLearntViewModel: LettersLearntViewModel,
    quizViewModel: QuizViewModel,
    quizAnswerViewModel: QuizAnswerViewModel,
    blankQuizViewModel: BlankQuizViewModel
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.SPLASH
    ){
        composable(Graph.SPLASH){
            SplashScreen(
                rootNavController = navController,
                preferencesManager = preferencesManager
            )
        }
        composable(Graph.TOUR){
            InitialTour(
                rootNavController = navController,
                preferencesManager = preferencesManager
            )
        }
        composable(Graph.INITIAL){
            InitialPage(
                navController = navController,
                textToSpeechViewModel = textToSpeechViewModel
            )
        }
        composable(Graph.MAIN+"/{screen}"){backStackEntry ->
            backStackEntry.arguments!!.getString("screen")
                ?.let {
                    MainPanel(
                        rootNavController = navController,
                        studentViewModel = studentViewModel,
                        textToSpeechViewModel = textToSpeechViewModel,
                        speechToTextViewModel = speechToTextViewModel,
                        startingScreen = it
                    )
                }
        }
        composable(Graph.LESSONS+"/{studentId}"){backStackEntry ->
            backStackEntry.arguments!!.getString("studentId")
                ?.let {
                    StudentPanel(
                        rootNavController = navController,
                        studentViewModel = studentViewModel,
                        textToSpeechViewModel = textToSpeechViewModel,
                        studentId = it,
                        speechToTextViewModel = speechToTextViewModel,
                        letterViewModel = letterViewModel,
                        lettersLearntViewModel = lettersLearntViewModel,
                        quizViewModel = quizViewModel,
                        quizAnswerViewModel = quizAnswerViewModel,
                        blankQuizViewModel = blankQuizViewModel
                    )
                }
        }
    }
}
/**
 * Object (enum) containing the destinations of each screen/sub-navigation graph
 * */
object Graph{
    const val ROOT = "root"
    const val SPLASH = "splash_screen"
    const val INITIAL = "initial"
    const val MAIN = "main"
    const val LESSONS = "lessons"
    const val TOUR = "tour"
}
