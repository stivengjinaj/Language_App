package com.stiven.languageapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.stiven.languageapp.R
import com.stiven.languageapp.navigation.Graph
import com.stiven.languageapp.utils.PreferencesManager
import kotlinx.coroutines.delay

/**
 * The first UI screen to appear when opening the app. After 1.5 seconds
 * the screen disappears and disengages from the navigation graph.
 *
 * @param rootNavController root navigation controller.
 * @param preferencesManager manager for shared preferences.
 * */
@Composable
fun SplashScreen(rootNavController: NavHostController, preferencesManager: PreferencesManager) {
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(if (isSystemInDarkTheme()) R.raw.lottieanimationdark else R.raw.lottieanimation))
    val progress by animateLottieCompositionAsState(
        composition = composition.value,
        iterations = LottieConstants.IterateForever
    )

    LaunchedEffect(key1 = true){
        delay(1500L)

        rootNavController.popBackStack()
        if (!preferencesManager.getBooleanData("DoneInitialTour",false)){
            preferencesManager.saveBoolean("DoneInitialTour", true)
            rootNavController.navigate(Graph.TOUR)
        }else{
            rootNavController.navigate(Graph.INITIAL)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        LottieAnimation(modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth(), progress = progress, composition = composition.value)
    }
}