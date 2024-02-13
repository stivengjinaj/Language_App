package com.stiven.languageapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.stiven.languageapp.navigation.RootNavGraph
import com.stiven.languageapp.ui.theme.LanguageAppTheme
import com.stiven.languageapp.utils.PreferencesManager
import com.stiven.languageapp.model.SpeechToTextImpl
import com.stiven.languageapp.viewmodels.LetterViewModel
import com.stiven.languageapp.viewmodels.LettersLearntViewModel
import com.stiven.languageapp.viewmodels.QuizViewModel
import com.stiven.languageapp.viewmodels.SpeechToTextViewModel
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

/**
 * The starting point of the application.
 * */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val studentViewModel : StudentViewModel by viewModels()
        val textToSpeechViewModel: TextToSpeechViewModel by viewModels()
        val letterViewModel : LetterViewModel by viewModels()
        val lettersLetterViewModel: LettersLearntViewModel by viewModels()
        val quizViewModel: QuizViewModel by viewModels()
        val preferencesManager = PreferencesManager(this)
        setContent {
            var permission by remember {
                mutableStateOf(
                    ContextCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.RECORD_AUDIO
                    ) == PackageManager.PERMISSION_GRANTED
                )
            }

            val speechToTextViewModel = viewModel {
                val app = get(APPLICATION_KEY)!!
                val stt = SpeechToTextImpl(app.applicationContext)
                SpeechToTextViewModel(stt)
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ){ granted ->
                permission = granted
            }
            LanguageAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if(!permission){
                        SideEffect {
                            launcher.launch(Manifest.permission.RECORD_AUDIO)
                        }
                    }
                    RootNavGraph(
                        navController = rememberNavController(),
                        studentViewModel = studentViewModel,
                        textToSpeechViewModel = textToSpeechViewModel,
                        preferencesManager = preferencesManager,
                        speechToTextViewModel = speechToTextViewModel,
                        letterViewModel = letterViewModel,
                        lettersLearntViewModel = lettersLetterViewModel,
                        quizViewModel = quizViewModel
                    )
                }
            }
        }
    }
}