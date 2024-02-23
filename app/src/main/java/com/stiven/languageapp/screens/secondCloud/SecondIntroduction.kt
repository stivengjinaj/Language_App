package com.stiven.languageapp.screens.secondCloud

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.DoubleArrow
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stiven.languageapp.R
import com.stiven.languageapp.navigation.Graph
import com.stiven.languageapp.navigation.SecondCloudNavGraph
import com.stiven.languageapp.view.LogoBannerNavigation
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

/**
 * Introduction to the exercises taught cloud 2.
 *
 * @param navController Current cloud navigation controller.
 * @param rootNavController Root navigation controller.
 * @param textToSpeechViewModel Text-to-Speech ViewModel.
 * @param studentId Student's id.
 * */
@Composable
fun SecondIntroduction (
    navController: NavHostController,
    rootNavController: NavHostController,
    textToSpeechViewModel: TextToSpeechViewModel,
    studentId: String
){
    val screenSize = LocalConfiguration.current.screenWidthDp
    val context = LocalContext.current
    val textAlpha = remember {
        mutableFloatStateOf(1f)
    }
    val canvasAlpha = remember {
        mutableFloatStateOf(0.2f)
    }
    val cancelAlpha = remember {
        mutableFloatStateOf(0.2f)
    }
    val validationAlpha = remember {
        mutableFloatStateOf(0.2f)
    }
    val cameraAlpha = remember {
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
        Spacer(modifier = Modifier.height((screenSize / 6 - 20).dp))
        Row (
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
                fontSize = (screenSize / 4).sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Box (
                modifier = Modifier
                    .alpha(canvasAlpha.floatValue)
                    .width((screenSize / 2 + 30).dp)
                    .height((screenSize / 2 + 30).dp)
                    .clip(RoundedCornerShape(20))
                    .border(BorderStroke(1.dp, MaterialTheme.colorScheme.inversePrimary))
                    .background(Color.White)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Icon(
                modifier = Modifier.alpha(cancelAlpha.floatValue),
                imageVector = Icons.Rounded.Cancel,
                contentDescription = "Erase all",
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
            Box(
                modifier = Modifier
                    .alpha(validationAlpha.floatValue)
                    .clip(RoundedCornerShape(50))
                    .size((screenSize / 6 - 20).dp)
                    .background(Color.Green),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier.size((screenSize / 6 - 20).dp),
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Validate",
                    tint = MaterialTheme.colorScheme.background
                )
            }
            Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
            Icon(
                modifier = Modifier.alpha(cameraAlpha.floatValue),
                imageVector = Icons.Rounded.CameraAlt,
                contentDescription = "CAMERA CHECK",
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6).dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            IconButton(
                modifier = Modifier.size((screenSize/5).dp),
                onClick = {
                    when(focusIndex.intValue){
                        0 -> {
                            textToSpeechViewModel.stopTextToSpeech()
                            textAlpha.floatValue = 0.2f
                            canvasAlpha.floatValue = 1f
                            textToSpeechViewModel.textToSpeech(context, context.getString(R.string.canvasIntro))
                        }
                        1 -> {
                            textToSpeechViewModel.stopTextToSpeech()
                            canvasAlpha.floatValue = 0.2f
                            cancelAlpha.floatValue = 1f
                            textToSpeechViewModel.textToSpeech(context, context.getString(R.string.cancelIntro))
                        }
                        2 -> {
                            textToSpeechViewModel.stopTextToSpeech()
                            cancelAlpha.floatValue = 0.2f
                            validationAlpha.floatValue = 1f
                            textToSpeechViewModel.textToSpeech(context, context.getString(R.string.validationIntro))
                        }
                        3 -> {
                            textToSpeechViewModel.stopTextToSpeech()
                            validationAlpha.floatValue = 0.2f
                            cameraAlpha.floatValue = 1f
                            textToSpeechViewModel.textToSpeech(context, context.getString(R.string.cameraWritingIntro))
                        }
                        4 -> {
                            textToSpeechViewModel.stopTextToSpeech()
                            navController.navigate(SecondCloudNavGraph.ALPHABET_WRITING)
                        }
                    }
                    focusIndex.intValue += 1
                }
            ) {
                Icon(
                    modifier = Modifier
                        .size((screenSize / 5).dp)
                        .rotate(-90f),
                    imageVector = Icons.Rounded.ArrowDownward,
                    contentDescription = "Next page.",
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
                    navController.navigate(SecondCloudNavGraph.ALPHABET_WRITING)
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
                textToSpeechViewModel.stopTextToSpeech()
                textToSpeechViewModel.textToSpeech(context,context.getString(R.string.letterWritingText))
            }
        }
    }
}