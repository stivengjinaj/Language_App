package com.stiven.languageapp.screens.firstCloud

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.DoubleArrow
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stiven.languageapp.R
import com.stiven.languageapp.navigation.FirstCloudRoutes
import com.stiven.languageapp.navigation.Graph
import com.stiven.languageapp.view.LogoBannerNavigation
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

/**
 * Introduction to the exercises taught in this section.
 *
 * @param navController Current cloud navigation controller.
 * @param rootNavController Root navigation controller.
 * @param textToSpeechViewModel Text-to-Speech ViewModel.
 * @param studentId Student's id.
 * */
@Composable
fun FirstIntroduction(
    navController: NavHostController,
    rootNavController: NavHostController,
    textToSpeechViewModel: TextToSpeechViewModel,
    studentId: String
){
    val context = LocalContext.current
    val screenSize = LocalConfiguration.current.screenWidthDp
    val textAlpha = remember {
        mutableFloatStateOf(1f)
    }
    val playAlpha = remember {
        mutableFloatStateOf(0.2f)
    }
    val micAlpha = remember {
        mutableFloatStateOf(0.2f)
    }
    val focusIndex = remember {
        mutableIntStateOf(0)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LogoBannerNavigation(
            screenSize = screenSize,
            onBackButtonClick = {
                rootNavController.navigate(Graph.LESSONS+"/$studentId")
            }
        )
        Spacer(modifier = Modifier.height((screenSize / 6).dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(textAlpha.floatValue),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "A",
                color = MaterialTheme.colorScheme.inversePrimary,
                fontWeight = FontWeight.Bold,
                fontSize = (screenSize / 2).sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                modifier = Modifier
                    .size((screenSize / 8).dp)
                    .alpha(playAlpha.floatValue),
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = "Listen Letter",
                tint = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.width((screenSize / 6 - 10).dp))
            Icon(
                modifier = Modifier
                    .size((screenSize / 8).dp)
                    .alpha(micAlpha.floatValue),
                imageVector = Icons.Rounded.Mic,
                contentDescription = "Pronounce Letter",
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
        Spacer(modifier = Modifier.height((screenSize / 6 - 10).dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                modifier = Modifier.size((screenSize/6).dp),
                onClick = {
                    when(focusIndex.intValue){
                        0 -> {
                            textToSpeechViewModel.stopTextToSpeech()
                            textToSpeechViewModel.textToSpeech(context, context.getString(R.string.letterPronouncingPlay))
                            textAlpha.floatValue = 0.2f
                            playAlpha.floatValue = 1f
                            focusIndex.intValue += 1
                        }
                        1 -> {
                            textToSpeechViewModel.stopTextToSpeech()
                            textToSpeechViewModel.textToSpeech(context, context.getString(R.string.letterPronouncingMic))
                            textAlpha.floatValue = 0.2f
                            playAlpha.floatValue = 0.2f
                            micAlpha.floatValue = 1f
                            focusIndex.intValue += 1
                        }
                        2 -> {
                            navController.navigate(FirstCloudRoutes.ALPHABET_PRONOUNCING)
                        }
                    }
            }) {
                Icon(
                    modifier = Modifier
                        .size((screenSize / 6).dp)
                        .rotate(-90f),
                    imageVector = Icons.Rounded.ArrowDownward,
                    contentDescription = "Next page",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
        Spacer(modifier = Modifier.height((screenSize / 6 - 20).dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                modifier = Modifier.size((screenSize/6 - 10).dp),
                onClick = {
                    navController.navigate(FirstCloudRoutes.ALPHABET_PRONOUNCING)
                }
            ) {
                Icon(
                    modifier = Modifier.size((screenSize / 6).dp),
                    imageVector = Icons.Rounded.DoubleArrow,
                    contentDescription = "Skip intro",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
        LaunchedEffect(Dispatchers.IO){
            if(focusIndex.intValue == 0){
                delay(500)
                textToSpeechViewModel.textToSpeech(context, context.getString(R.string.letterPronouncingText))
            }
        }
    }
}