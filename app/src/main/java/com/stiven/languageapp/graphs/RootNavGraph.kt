package com.stiven.languageapp.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stiven.languageapp.InitialPage
import com.stiven.languageapp.MainScreen
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

@Composable
fun RootNavGraph(navController: NavHostController,studentViewModel: StudentViewModel, textToSpeechViewModel: TextToSpeechViewModel) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.INITIAL
    ){
        composable(route = Graph.INITIAL){
            InitialPage(navController)
        }
        composable(route = Graph.MAIN){
            MainScreen(studentViewModel, textToSpeechViewModel)
        }
    }
}

object Graph{
    const val ROOT = "root"
    const val INITIAL = "INITIAL"
    const val MAIN = "main"
    const val LESSONS = "lessons"
}