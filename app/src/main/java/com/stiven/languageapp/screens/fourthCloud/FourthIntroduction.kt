package com.stiven.languageapp.screens.fourthCloud

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.DoubleArrow
import androidx.compose.material.icons.rounded.RadioButtonUnchecked
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
import com.stiven.languageapp.navigation.FourthCloudNavGraph
import com.stiven.languageapp.navigation.Graph
import com.stiven.languageapp.view.LogoBannerNavigation
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import kotlinx.coroutines.Dispatchers

/**
 * Introduction to the blank questions taught in this section.
 *
 * @param studentId Student's id.
 * @param navController Current cloud navigation controller.
 * @param rootNavController Root navigation controller.
 * @param textToSpeechViewModel Text-to-Speech ViewModel.
 * */
@Composable
fun FourthIntroduction(
    studentId: String,
    navController: NavHostController,
    rootNavController: NavHostController,
    textToSpeechViewModel: TextToSpeechViewModel
) {
    val screenSize = LocalConfiguration.current.screenWidthDp
    val context = LocalContext.current
    val questionAlpha = remember {
        mutableFloatStateOf(1f)
    }
    val optionsAlpha = remember {
        mutableFloatStateOf(0.3f)
    }
    val alphaIndex = remember {
        mutableIntStateOf(0)
    }
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        LogoBannerNavigation(
            screenSize = screenSize,
            onBackButtonClick = {
                rootNavController.navigate(Graph.LESSONS+"/$studentId")
            }
        )
        Spacer(modifier = Modifier.height((screenSize / 6).dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier.alpha(questionAlpha.floatValue),
                text = "Ciao, ___ domani.",
                color = MaterialTheme.colorScheme.inversePrimary,
                fontWeight = FontWeight.Bold,
                fontSize = (screenSize / 6 - 40).sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6).dp))
        Row(
            modifier = Modifier.fillMaxWidth().alpha(optionsAlpha.floatValue),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
            Icon(
                imageVector = Icons.Filled.Circle,
                contentDescription = "Option1",
                tint = MaterialTheme.colorScheme.inversePrimary
            )
            Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
            Text(
                text = "a",
                color = MaterialTheme.colorScheme.inversePrimary,
                fontWeight = FontWeight.Bold,
                fontSize = (screenSize / 6 - 40).sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6).dp))
        Row(
            modifier = Modifier.fillMaxWidth().alpha(optionsAlpha.floatValue),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
            Icon(
                imageVector = Icons.Rounded.RadioButtonUnchecked,
                contentDescription = "Option2",
                tint = MaterialTheme.colorScheme.inversePrimary
            )
            Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
            Text(
                text = "di",
                color = MaterialTheme.colorScheme.inversePrimary,
                fontWeight = FontWeight.Bold,
                fontSize = (screenSize / 6 - 40).sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6).dp))
        Row(
            modifier = Modifier.fillMaxWidth().alpha(optionsAlpha.floatValue),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
            Icon(
                imageVector = Icons.Rounded.RadioButtonUnchecked,
                contentDescription = "Option3",
                tint = MaterialTheme.colorScheme.inversePrimary
            )
            Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
            Text(
                text = "con",
                color = MaterialTheme.colorScheme.inversePrimary,
                fontWeight = FontWeight.Bold,
                fontSize = (screenSize / 6 - 40).sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6).dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            IconButton(
                onClick = {
                    if(alphaIndex.intValue == 0){
                        textToSpeechViewModel.stopTextToSpeech()
                        textToSpeechViewModel.textToSpeech(context, context.getString(R.string.selectedOption))
                        questionAlpha.floatValue = 0.3f
                        optionsAlpha.floatValue = 1f
                        alphaIndex.intValue++
                    }else {
                        navController.navigate(FourthCloudNavGraph.BLANK_QUIZ)
                    }
                }
            ) {
                Icon(
                    modifier = Modifier
                        .size((screenSize / 5).dp)
                        .rotate(-90f),
                    imageVector = Icons.Rounded.ArrowDownward,
                    contentDescription = "Validate",
                    tint = MaterialTheme.colorScheme.inversePrimary
                )
            }
        }
        Spacer(modifier = Modifier.height((screenSize/6).dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            IconButton(
                onClick = {
                    navController.navigate(FourthCloudNavGraph.BLANK_QUIZ)
                }
            ) {
                Icon(
                    modifier = Modifier.size((screenSize / 5).dp),
                    imageVector = Icons.Rounded.DoubleArrow,
                    contentDescription = "Validate",
                    tint = MaterialTheme.colorScheme.inversePrimary
                )
            }
        }
    }
    LaunchedEffect(Dispatchers.IO){
        if(alphaIndex.intValue == 0){
            textToSpeechViewModel.stopTextToSpeech()
            textToSpeechViewModel.textToSpeech(context, context.getString(R.string.fill_blank))
        }
    }
}