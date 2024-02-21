package com.stiven.languageapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.stiven.languageapp.R
import com.stiven.languageapp.navigation.Graph
import com.stiven.languageapp.utils.PreferencesManager
import com.stiven.languageapp.view.initialTour.ChangeLanguage
import com.stiven.languageapp.view.initialTour.ClassroomGuide
import com.stiven.languageapp.view.initialTour.DictionaryGuide
import com.stiven.languageapp.view.initialTour.InitialPageGuide
import com.stiven.languageapp.view.initialTour.RoadmapTour
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import kotlinx.coroutines.delay
import java.util.Locale

/**
 * One time walkthrough guide for the user to get to know the application.
 * Clicking buttons as "Skip" or "Done" will discard the walkthrough and
 * it will not appear anymore until the user prompts it from settings screen.
 *
 * @param rootNavController navigation controller to go to the root of the app.
 * @param preferencesManager preferences manager used to specify the completion of the walkthrough.
 * */
@Composable
fun InitialTour(
    rootNavController: NavHostController,
    preferencesManager: PreferencesManager,
    textToSpeechViewModel: TextToSpeechViewModel
){
    preferencesManager.saveBoolean("DoneInitialTour", false)
    val context = LocalContext.current
    val screenSize = LocalConfiguration.current.screenWidthDp
    val currentPage = rememberSaveable { mutableIntStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        when(currentPage.intValue){
            0 -> {
                ChangeLanguage(
                    screenSize = screenSize,
                    context = context,
                    textToSpeechViewModel = textToSpeechViewModel,
                    onNextClick = {
                        currentPage.intValue++
                    }
                )
            }
            1 -> {
                textToSpeechViewModel.customTextToSpeech(
                    context = context,
                    text = context.getString(R.string.page_1),
                    language = Locale.forLanguageTag(Locale.getDefault().language)
                )
                InitialPageGuide(
                    screenSize = screenSize,
                    context = context,
                    textToSpeechViewModel = textToSpeechViewModel,
                    onBackClick = {
                        currentPage.intValue--
                    },
                    onNextClick = {
                        currentPage.intValue++
                    }
                )
            }
            2 -> {
                textToSpeechViewModel.stopTextToSpeech()
                textToSpeechViewModel.customTextToSpeech(
                    context = context,
                    text = context.getString(R.string.classroom_guide),
                    language = Locale.forLanguageTag(Locale.getDefault().language)
                )
                ClassroomGuide(
                    screenSize = screenSize,
                    context = context,
                    onBackClick = {
                        currentPage.intValue--
                    },
                    onNextClick = {
                        currentPage.intValue++
                    }
                )
            }
            3 -> {
                textToSpeechViewModel.stopTextToSpeech()
                textToSpeechViewModel.customTextToSpeech(
                    context = context,
                    text = context.getString(R.string.roadmap_guide),
                    language = Locale.forLanguageTag(Locale.getDefault().language)
                )
                RoadmapTour(
                    screenSize = screenSize,
                    onBackClick = {
                        currentPage.intValue--
                    },
                    onNextClick = {
                        currentPage.intValue++
                    }
                )
            }
            4 -> {
                textToSpeechViewModel.stopTextToSpeech()
                textToSpeechViewModel.customTextToSpeech(
                    context = context,
                    text = context.getString(R.string.dictionary_guide),
                    language = Locale.forLanguageTag(Locale.getDefault().language)
                )
                DictionaryGuide(
                    screenSize = screenSize,
                    context = context,
                    textToSpeechViewModel = textToSpeechViewModel,
                    onNextClick = {
                        currentPage.intValue++
                    },
                    onBackClick = {
                        currentPage.intValue--
                    }
                )
            }
            else -> {
                preferencesManager.saveBoolean("DoneInitialTour", true)
                OutroSplash(rootNavController = rootNavController)
            }
        }
    }
}

@Composable
private fun OutroSplash(rootNavController: NavHostController){
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(if (isSystemInDarkTheme()) R.raw.walkthrough_outro_dark else R.raw.walkthrough_outro))
    val progress by animateLottieCompositionAsState(
        composition = composition.value,
        iterations = LottieConstants.IterateForever
    )

    LaunchedEffect(key1 = true){
        delay(1000L)

        rootNavController.popBackStack()
        rootNavController.navigate(Graph.INITIAL)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
    ) {
        LottieAnimation(
            modifier = Modifier
                .fillMaxSize()
                .scale(1.5f),
            progress = progress,
            composition = composition.value
        )
    }
}