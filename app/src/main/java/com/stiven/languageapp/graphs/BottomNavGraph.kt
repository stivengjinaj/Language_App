package com.stiven.languageapp.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stiven.languageapp.BottomBarScreen
import com.stiven.languageapp.NewCourse
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

/**
 * Function that set the routes of the NavigationGraph with BottomBar
 *
 * @param navController navigation Host Controller
 * @param studentViewModel view-model that handles operations in Students database
 * @param textToSpeechViewModel view-model that handles text-to-speech operations
 * @param startingScreen the first screen selected in bottom navigation bar after user chooses it in Initial Screen
 * */
@Composable
fun BottomNavGraph(
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    startingScreen: String
) {
    val startingDestination = when(startingScreen){
        "my_courses" -> BottomBarScreen.MyCourses
        "new_course" -> BottomBarScreen.NewCourse
        else -> {
            BottomBarScreen.Emergency
        }
    }
    NavHost(
        navController = navController,
        startDestination = startingDestination.route
    ){
        composable(route = BottomBarScreen.MyCourses.route){

        }

        composable(route = BottomBarScreen.NewCourse.route){
            NewCourse(studentViewModel, textToSpeechViewModel)
        }

        composable(route = BottomBarScreen.Settings.route){

        }

        composable(route = BottomBarScreen.Emergency.route){

        }
    }
}