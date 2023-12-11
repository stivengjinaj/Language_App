package com.stiven.languageapp.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stiven.languageapp.BottomBarScreens
import com.stiven.languageapp.Classroom
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
    rootNavController: NavHostController,
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    startingScreen: String
) {
    val startingDestination = when(startingScreen){
        "classroom" -> BottomBarScreens.Classroom
        "new_course" -> BottomBarScreens.NewCourse
        else -> {
            BottomBarScreens.Emergency
        }
    }
    NavHost(
        navController = navController,
        startDestination = startingDestination.route
    ){
        composable(route = BottomBarScreens.Classroom.route){
            Classroom(rootNavController, navController, studentViewModel, textToSpeechViewModel)
        }

        composable(route = BottomBarScreens.NewCourse.route){
            NewCourse(studentViewModel, textToSpeechViewModel, navController)
        }

        composable(route = BottomBarScreens.Settings.route){

        }

        composable(route = BottomBarScreens.Emergency.route){

        }
    }
}