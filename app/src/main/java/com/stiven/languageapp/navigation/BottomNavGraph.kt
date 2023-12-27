package com.stiven.languageapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stiven.languageapp.model.BottomBarScreens
import com.stiven.languageapp.screens.Classroom
import com.stiven.languageapp.screens.Emergency
import com.stiven.languageapp.screens.NewCourse
import com.stiven.languageapp.screens.Settings
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

/**
 * A navigation graph inside the root navigation graph containing the general
 * destinations of the app, such as: Classroom, NewCourse, Settings
 * and Emergency Dictionary.
 *
 * @param rootNavController root navHost to shift navigation graph.
 * @param navController navigation Host Controller.
 * @param studentViewModel view-model that handles operations in Students database.
 * @param textToSpeechViewModel view-model that handles text-to-speech operations.
 * @param startingScreen the first screen selected in bottom navigation bar after user chooses it in Initial Screen.
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
        composable(
            route = BottomBarScreens.Classroom.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            }
        ){
            Classroom(rootNavController, navController, studentViewModel, textToSpeechViewModel)
        }

        composable(
            route = BottomBarScreens.NewCourse.route,
            enterTransition = {
                slideIntoContainer(
                    towards =
                    if(initialState.destination.route == BottomBarScreens.Classroom.route)
                        AnimatedContentTransitionScope.SlideDirection.Left
                    else
                        AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = if(initialState.destination.route == BottomBarScreens.Classroom.route)
                        AnimatedContentTransitionScope.SlideDirection.Left
                    else
                        AnimatedContentTransitionScope.SlideDirection.Right,
                )
            }
        ){
            NewCourse(studentViewModel, textToSpeechViewModel, navController)
        }

        composable(
            route = BottomBarScreens.Settings.route,
            enterTransition = {
                slideIntoContainer(
                    towards =
                    if(initialState.destination.route == BottomBarScreens.Emergency.route)
                        AnimatedContentTransitionScope.SlideDirection.Right
                    else
                        AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = if(initialState.destination.route == BottomBarScreens.Emergency.route)
                        AnimatedContentTransitionScope.SlideDirection.Right
                    else
                        AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            }
        ){
            Settings(rootNavController, studentViewModel, textToSpeechViewModel)
        }

        composable(
            route = BottomBarScreens.Emergency.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }
        ){
            Emergency()
        }
    }
}