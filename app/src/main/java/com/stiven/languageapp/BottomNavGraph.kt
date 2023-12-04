package com.stiven.languageapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

/**
 * Function that set the routes of the NavigationGraph with BottomBar
 *
 * @param navController Navigation Host Controller
 * */
@Composable
fun BottomNavGraph(navController: NavHostController, studentViewModel: StudentViewModel, textToSpeechViewModel: TextToSpeechViewModel) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.MyCourses.route
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