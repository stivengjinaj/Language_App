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
import com.stiven.languageapp.StudentPanel
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import kotlinx.coroutines.delay

@Composable
fun RootNavGraph(navController: NavHostController,studentViewModel: StudentViewModel, textToSpeechViewModel: TextToSpeechViewModel) {
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
                ?.let { MainScreen(navController,studentViewModel, textToSpeechViewModel, it) }
        }
        composable(Graph.LESSONS+"/{studentId}"){backStackEntry ->
            backStackEntry.arguments!!.getString("studentId")
                ?.let { StudentPanel(navController, studentViewModel, textToSpeechViewModel, it) }
        }
    }
}

object Graph{
    const val ROOT = "root"
    const val SPLASH = "splash_screen"
    const val INITIAL = "initial"
    const val MAIN = "main"
    const val LESSONS = "lessons"
}

@Composable
fun SplashScreen(navController: NavHostController){
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(if (isSystemInDarkTheme()) R.raw.lottieanimationdark else R.raw.lottieanimation))
    val progress by animateLottieCompositionAsState(
        composition = composition.value,
        iterations = LottieConstants.IterateForever
    )


    LaunchedEffect(key1 = true){
        delay(1500L)

        navController.popBackStack()
        navController.navigate(Graph.INITIAL)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        LottieAnimation(modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth(), progress = progress, composition = composition.value)
    }
}