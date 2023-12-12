package com.stiven.languageapp.graphs

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.stiven.languageapp.InitialPage
import com.stiven.languageapp.R
import com.stiven.languageapp.SplashScreen
import com.stiven.languageapp.StudentPanel
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import kotlinx.coroutines.delay

/**
 * The root (starting point) navigation graph. It contains SplashScreen, The first
 * page that interacts with the user and 2 sub-navigation graphs: MainPanel and StudentPanel.
 *
 * @param navController the navigation controller.
 * @param studentViewModel view-model that handles operations in Students database.
 * @param textToSpeechViewModel view-model that handles text-to-speech operations.
 * */
@Composable
fun RootNavGraph(navController: NavHostController, studentViewModel: StudentViewModel, textToSpeechViewModel: TextToSpeechViewModel) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.SPLASH
    ){
        composable(Graph.SPLASH){
            SplashScreen(navController)
        }
        composable(Graph.INITIAL){
            InitialPage(navController)
        }
        composable(Graph.MAIN+"/{screen}"){backStackEntry ->
            backStackEntry.arguments!!.getString("screen")
                ?.let { MainPanel(navController,studentViewModel, textToSpeechViewModel, it) }
        }
        composable(Graph.LESSONS+"/{studentId}"){backStackEntry ->
            backStackEntry.arguments!!.getString("studentId")
                ?.let { StudentPanel(navController, studentViewModel, textToSpeechViewModel, it) }
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
}
