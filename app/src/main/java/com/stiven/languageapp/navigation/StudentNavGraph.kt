package com.stiven.languageapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stiven.languageapp.screens.Lessons
import com.stiven.languageapp.model.BottomBarScreens
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
 * */
@Composable
fun StudentNavGraph(
    rootNavController: NavHostController,
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    studentId: String
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreens.Lessons.route
    ){
        composable(route = BottomBarScreens.Lessons.route){
            Lessons(navController, studentViewModel, textToSpeechViewModel, studentId)
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