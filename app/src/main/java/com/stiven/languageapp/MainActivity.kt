package com.stiven.languageapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.stiven.languageapp.graphs.RootNavGraph
import com.stiven.languageapp.ui.theme.LanguageAppTheme
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

/**
 * The starting point of the application.
 * */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val db = AppDatabase.getDatabase(this)
        //val wordViewModel : WordViewModel by viewModels()
        val studentViewModel : StudentViewModel by viewModels()
        val textToSpeechViewModel: TextToSpeechViewModel by viewModels()
        setContent {
            LanguageAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootNavGraph(navController = rememberNavController(), studentViewModel = studentViewModel, textToSpeechViewModel = textToSpeechViewModel)
                }
            }
        }
    }
}

