package com.stiven.languageapp.graphs

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.stiven.languageapp.BottomBar
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    startingScreen: String
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        BottomNavGraph(navController = navController, studentViewModel, textToSpeechViewModel, startingScreen)
    }
}