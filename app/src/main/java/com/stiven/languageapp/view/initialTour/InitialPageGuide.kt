package com.stiven.languageapp.view.initialTour

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stiven.languageapp.R
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import java.util.Locale

@Composable
fun InitialPageGuide(
    screenSize: Int,
    context: Context,
    textToSpeechViewModel: TextToSpeechViewModel,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val classroomAlpha = remember {
        mutableFloatStateOf(1f)
    }
    val newCourseAlpha = remember {
        mutableFloatStateOf(0.2f)
    }
    val emergencyAlpha = remember {
        mutableFloatStateOf(0.2f)
    }
    val alphaIndex = remember {
        mutableIntStateOf(0)
    }
    Spacer(modifier = Modifier.height((screenSize/2 + 50).dp))
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .alpha(classroomAlpha.floatValue),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        OutlinedButton(
            modifier = Modifier.width((screenSize - 30).dp),
            onClick = {

            },
            shape = RoundedCornerShape(50),
            border = BorderStroke(2.dp, Color.White)
        ) {
            Text(
                context.getString(R.string.classroom),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = (screenSize/6 - 40).sp,
                    fontWeight = FontWeight.Bold)
            )
        }
    }
    Spacer(modifier = Modifier.height((screenSize/6 - 10).dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(newCourseAlpha.floatValue),
        horizontalArrangement = Arrangement.Center
    ){
        OutlinedButton(
            modifier = Modifier.width((screenSize - 30).dp),
            onClick = {

            },
            shape = RoundedCornerShape(50),
            border = BorderStroke(2.dp, Color.White)
        ) {
            Text(
                context.getString(R.string.new_course),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = (screenSize/6 - 40).sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
    Spacer(modifier = Modifier.height((screenSize/6 - 10).dp))
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .alpha(emergencyAlpha.floatValue),
        horizontalArrangement = Arrangement.Center
    ){
        OutlinedButton(
            modifier = Modifier.width((screenSize - 30).dp),
            onClick = {

            },
            shape = RoundedCornerShape(50),
            border = BorderStroke(2.dp, Color.White),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
        ) {
            Text(
                context.getString(R.string.emergency),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = (screenSize/6 - 40).sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
    Spacer(modifier = Modifier.height((screenSize/2 - 10).dp))
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        IconButton(
            modifier = Modifier.width((screenSize/3).dp),
            onClick = {
                when(alphaIndex.intValue){
                    0 -> {
                        onBackClick()
                    }
                    1 -> {
                        textToSpeechViewModel.stopTextToSpeech()
                        textToSpeechViewModel.customTextToSpeech(
                            context = context,
                            text = context.getString(R.string.page_1),
                            language = Locale.forLanguageTag(Locale.getDefault().language)
                        )
                        classroomAlpha.floatValue = 1f
                        newCourseAlpha.floatValue = 0.2f
                    }
                    2 -> {
                        textToSpeechViewModel.stopTextToSpeech()
                        textToSpeechViewModel.customTextToSpeech(
                            context = context,
                            text = context.getString(R.string.page_2),
                            language = Locale.forLanguageTag(Locale.getDefault().language)
                        )
                        newCourseAlpha.floatValue = 1f
                        emergencyAlpha.floatValue = 0.2f
                    }
                }
                alphaIndex.intValue--
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        IconButton(
            modifier = Modifier.width((screenSize/3).dp),
            onClick = {
                when(alphaIndex.intValue){
                    0 -> {
                        textToSpeechViewModel.stopTextToSpeech()
                        textToSpeechViewModel.customTextToSpeech(
                            context = context,
                            text = context.getString(R.string.page_2),
                            language = Locale.forLanguageTag(Locale.getDefault().language)
                        )
                        classroomAlpha.floatValue = 0.2f
                        newCourseAlpha.floatValue = 1f
                    }
                    1 -> {
                        textToSpeechViewModel.stopTextToSpeech()
                        textToSpeechViewModel.customTextToSpeech(
                            context = context,
                            text = context.getString(R.string.page_3),
                            language = Locale.forLanguageTag(Locale.getDefault().language)
                        )
                        newCourseAlpha.floatValue = 0.2f
                        emergencyAlpha.floatValue = 1f
                    }
                    2 -> {
                        onNextClick()
                    }
                }
                alphaIndex.intValue++
            }
        ) {
            Icon(
                modifier = Modifier.rotate(180f),
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = "Next",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}