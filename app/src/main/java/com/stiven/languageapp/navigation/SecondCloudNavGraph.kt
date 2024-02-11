package com.stiven.languageapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stiven.languageapp.screens.secondCloud.AlphabetWriting
import com.stiven.languageapp.screens.secondCloud.SecondIntroduction
import com.stiven.languageapp.viewmodels.LettersLearntViewModel
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

/**
 * Navigation graph when the student clicks the second cloud.
 *
 * @param studentId Student's id.
 * @param rootNavController Root navigation controller.
 * @param studentViewModel Student's ViewModel.
 * @param navController Navigation Controller of the current cloud.
 * @param textToSpeechViewModel Text-to-speech ViewModel.
 * */
@Composable
fun SecondCloudNavGraph(
    studentId: String,
    rootNavController: NavHostController,
    studentViewModel: StudentViewModel,
    navController: NavHostController,
    textToSpeechViewModel: TextToSpeechViewModel,
    lettersLearntViewModel: LettersLearntViewModel
){
    NavHost(
        navController = navController,
        startDestination = SecondCloudNavGraph.SECOND_INTRODUCTION
    ){
        composable(route = SecondCloudNavGraph.SECOND_INTRODUCTION){
            SecondIntroduction(
                navController = navController,
                rootNavController = rootNavController,
                textToSpeechViewModel = textToSpeechViewModel,
                studentId = studentId
            )
        }
        composable(route = SecondCloudNavGraph.ALPHABET_WRITING){
            AlphabetWriting(
                navController = navController,
                rootNavController = rootNavController,
                studentViewModel = studentViewModel,
                textToSpeechViewModel = textToSpeechViewModel,
                lettersLearntViewModel = lettersLearntViewModel,
                studentId = studentId
            )
        }
        composable(route = SecondCloudNavGraph.EXERCISES){

        }
    }
}

/**
 * Destination routes names.
 * */
object SecondCloudNavGraph {
    const val SECOND_CLOUD = "secondCloud"
    const val SECOND_INTRODUCTION = "secondCloudIntro"
    const val ALPHABET_WRITING = "alphabetWriting"
    const val EXERCISES = "secondCloudExercises"
}