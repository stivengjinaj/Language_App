package com.stiven.languageapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stiven.languageapp.screens.InitialPage
import com.stiven.languageapp.screens.InitialTour
import com.stiven.languageapp.screens.SplashScreen
import com.stiven.languageapp.utils.PreferencesManager
import com.stiven.languageapp.viewmodels.LetterViewModel
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
 * @param letterViewModel letter's view-model.
 * */
@Composable
fun RootNavGraph(
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    preferencesManager: PreferencesManager,
    speechToTextViewModel: SpeechToTextViewModel,
    letterViewModel: LetterViewModel
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.SPLASH
    ){
        composable(Graph.SPLASH){
            SplashScreen(navController, preferencesManager)
        }
        composable(Graph.TOUR){
            InitialTour(navController, preferencesManager)
        }
        composable(Graph.INITIAL){
            InitialPage(navController, textToSpeechViewModel)
        }
        composable(Graph.MAIN+"/{screen}"){backStackEntry ->
            backStackEntry.arguments!!.getString("screen")
                ?.let { MainPanel(navController, studentViewModel, textToSpeechViewModel, it) }
        }
        composable(Graph.LESSONS+"/{studentId}"){backStackEntry ->
            backStackEntry.arguments!!.getString("studentId")
                ?.let { StudentPanel(navController, studentViewModel, textToSpeechViewModel, it, speechToTextViewModel, letterViewModel) }
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
