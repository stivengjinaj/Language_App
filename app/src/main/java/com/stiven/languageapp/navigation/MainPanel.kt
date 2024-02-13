package com.stiven.languageapp.navigation

import android.annotation.SuppressLint
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.stiven.languageapp.view.BottomBarView
import com.stiven.languageapp.viewmodels.SpeechToTextViewModel
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
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainPanel(
    rootNavController: NavHostController,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    speechToTextViewModel: SpeechToTextViewModel,
    startingScreen: String,
) {
    val navController = rememberNavController()
    val screenSize = LocalConfiguration.current.screenWidthDp
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Triolingo",
                        style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontSize = (screenSize/12).sp,
                                    textAlign = TextAlign.Center
                                )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            BottomBarView(navController = navController)
        }
    ) {
        BottomNavGraph(
            rootNavController = rootNavController,
            navController = navController,
            studentViewModel = studentViewModel,
            textToSpeechViewModel = textToSpeechViewModel,
            speechToTextViewModel = speechToTextViewModel,
            startingScreen = startingScreen
        )
    }
}