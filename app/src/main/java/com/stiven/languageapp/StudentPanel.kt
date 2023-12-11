package com.stiven.languageapp

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StudentPanel(
    rootNavController: NavHostController,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    studentId: String
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            StudentBottomBar(navController = navController)
        }
    ) {
        StudentNavGraph(rootNavController, navController, studentViewModel, textToSpeechViewModel, studentId)
    }
}