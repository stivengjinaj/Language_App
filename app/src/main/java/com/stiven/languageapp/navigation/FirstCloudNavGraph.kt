package com.stiven.languageapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stiven.languageapp.screens.firstCloud.AlphabetPronouncing
import com.stiven.languageapp.screens.firstCloud.AlphabetWriting
import com.stiven.languageapp.screens.firstCloud.Introduction
import com.stiven.languageapp.screens.firstCloud.Exercises
import com.stiven.languageapp.viewmodels.SpeechToTextViewModel
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

/**
 * Navigation graph when the student click the first cloud.
 *
 * @param studentId Student's id.
 * @param rootNavController Root navigation controller.
 * @param studentViewModel Student's ViewModel.
 * @param navController Navigation Controller of the current cloud.
 * @param textToSpeechViewModel Text-to-speech ViewModel.
 * @param speechToTextViewModel Speech-to-text ViewModel.
 * */
@Composable
fun FirstCloudNavGraph(
    studentId: String,
    rootNavController: NavHostController,
    studentViewModel: StudentViewModel,
    navController: NavHostController,
    textToSpeechViewModel: TextToSpeechViewModel,
    speechToTextViewModel: SpeechToTextViewModel
){
    NavHost(
        navController = navController,
        startDestination = FirstCloudGraph.INTRODUCTION
    ){
        composable(route = FirstCloudGraph.INTRODUCTION){
            Introduction(navController, rootNavController, studentId)
        }
        composable(route = FirstCloudGraph.ALPHABET_PRONOUNCING){
            AlphabetPronouncing(
                navController = navController,
                rootNavController = rootNavController,
                studentViewModel = studentViewModel,
                speechToTextViewModel = speechToTextViewModel,
                textToSpeechViewModel = textToSpeechViewModel,
                studentId = studentId
            )
        }
        composable(route = FirstCloudGraph.ALPHABET_WRITING){
            AlphabetWriting(navController)
        }
        composable(route = FirstCloudGraph.EXERCISES){
            Exercises(navController)
        }
    }
}

/**
 * Destination routes names.
 * */
object FirstCloudGraph {
    const val FIRST_CLOUD = "firstCloud"
    const val INTRODUCTION = "firstCloudIntro"
    const val ALPHABET_PRONOUNCING = "alphabetPronouncing"
    const val ALPHABET_WRITING = "alphabetWriting"
    const val EXERCISES = "firstCloudExercises"
}