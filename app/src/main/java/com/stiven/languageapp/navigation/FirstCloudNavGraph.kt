package com.stiven.languageapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stiven.languageapp.screens.firstCloud.AlphabetPronouncing
import com.stiven.languageapp.screens.firstCloud.FirstIntroduction
import com.stiven.languageapp.screens.firstCloud.Exercises
import com.stiven.languageapp.viewmodels.LettersLearntViewModel
import com.stiven.languageapp.viewmodels.SpeechToTextViewModel
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

/**
 * Navigation graph when the student clicks the first cloud.
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
    speechToTextViewModel: SpeechToTextViewModel,
    lettersLearntViewModel: LettersLearntViewModel
){
    NavHost(
        navController = navController,
        startDestination = FirstCloudRoutes.FIRST_INTRODUCTION
    ){
        composable(route = FirstCloudRoutes.FIRST_INTRODUCTION){
            FirstIntroduction(
                navController = navController,
                rootNavController = rootNavController,
                textToSpeechViewModel = textToSpeechViewModel,
                studentId = studentId
            )
        }
        composable(route = FirstCloudRoutes.ALPHABET_PRONOUNCING){
            AlphabetPronouncing(
                navController = navController,
                rootNavController = rootNavController,
                studentViewModel = studentViewModel,
                speechToTextViewModel = speechToTextViewModel,
                textToSpeechViewModel = textToSpeechViewModel,
                lettersLearntViewModel = lettersLearntViewModel,
                studentId = studentId
            )
        }
        composable(route = FirstCloudRoutes.EXERCISES){
            Exercises(navController)
        }
    }
}

/**
 * Destination routes names.
 * */
object FirstCloudRoutes {
    const val FIRST_CLOUD = "firstCloud"
    const val FIRST_INTRODUCTION = "firstCloudIntro"
    const val ALPHABET_PRONOUNCING = "alphabetPronouncing"
    const val EXERCISES = "firstCloudExercises"
}