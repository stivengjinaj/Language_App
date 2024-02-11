package com.stiven.languageapp.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.stiven.languageapp.view.StudentBottomBar
import com.stiven.languageapp.viewmodels.LetterViewModel
import com.stiven.languageapp.viewmodels.LettersLearntViewModel
import com.stiven.languageapp.viewmodels.SpeechToTextViewModel
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

/**
 * Starting point of the personalized part of the student. Contains
 * a Scaffold with a bottom bar to navigate and a sub-navigation graph.
 *
 * @param rootNavController root's navigation controller used to go back when student logs out.
 * @param studentViewModel student's view-model.
 * @param textToSpeechViewModel text to speech view-model.
 * @param studentId id used to get student's information in order to personalize StudentPanel.
 * @param speechToTextViewModel speech to text view-model.
 * @param letterViewModel letter's view-model.
 * */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StudentPanel(
    rootNavController: NavHostController,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    studentId: String,
    speechToTextViewModel: SpeechToTextViewModel,
    letterViewModel: LetterViewModel,
    lettersLearntViewModel: LettersLearntViewModel
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            StudentBottomBar(navController = navController)
        }
    ) {
        StudentNavGraph(
            rootNavController = rootNavController,
            navController = navController,
            studentViewModel = studentViewModel,
            textToSpeechViewModel = textToSpeechViewModel,
            studentId = studentId,
            speechToTextViewModel = speechToTextViewModel,
            letterViewModel = letterViewModel,
            lettersLearntViewModel = lettersLearntViewModel
        )
    }
}