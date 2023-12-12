package com.stiven.languageapp.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.stiven.languageapp.view.BottomBarView
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

/**
 * Function that creates a Scaffold with a bottom bar and a navigation host
 * containing the pages in BottomNavGraph a.k.a the starting point of BottomNavGraph.
 *
 * @param rootNavController root navigation controller to shift to another navigation host.
 * @param studentViewModel student's view-model.
 * @param textToSpeechViewModel text to speech view-model.
 * @param startingScreen the first screen to appear in BottomNavGraph based on user's choice.
 * */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainPanel(
    rootNavController: NavHostController,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    startingScreen: String
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBarView(navController = navController)
        }
    ) {
        BottomNavGraph(rootNavController, navController, studentViewModel, textToSpeechViewModel, startingScreen)
    }
}