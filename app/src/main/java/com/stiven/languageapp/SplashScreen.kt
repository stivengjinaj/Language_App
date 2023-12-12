package com.stiven.languageapp

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
import com.stiven.languageapp.graphs.Graph
import kotlinx.coroutines.delay

/**
 * The first UI screen to appear when opening the app. After 1.5 seconds
 * the screen disappears and disengages from the navigation graph.
 *
 * @param rootNavController root navigation controller.
 * */
@Composable
fun SplashScreen(rootNavController: NavHostController) {
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(if (isSystemInDarkTheme()) R.raw.lottieanimationdark else R.raw.lottieanimation))
    val progress by animateLottieCompositionAsState(
        composition = composition.value,
        iterations = LottieConstants.IterateForever
    )


    LaunchedEffect(key1 = true){
        delay(1500L)

        rootNavController.popBackStack()
        rootNavController.navigate(Graph.INITIAL)
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