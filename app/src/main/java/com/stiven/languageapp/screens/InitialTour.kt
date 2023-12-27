package com.stiven.languageapp.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.outlined.FiberManualRecord
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.stiven.languageapp.R
import com.stiven.languageapp.model.Student
import com.stiven.languageapp.navigation.Graph
import com.stiven.languageapp.utils.Languages
import com.stiven.languageapp.utils.PreferencesManager
import kotlinx.coroutines.delay

/**
 * One time walkthrough guide for the user to get to know the application.
 * Clicking buttons as "Skip" or "Done" will discard the walkthrough and
 * it will not appear anymore until the user prompts it from settings screen.
 *
 * @param rootNavController navigation controller to go to the root of the app.
 * @param preferencesManager preferences manager used to specify the completion of the walkthrough.
 * */
@Composable
fun InitialTour(rootNavController: NavHostController, preferencesManager: PreferencesManager){
    val context = LocalContext.current
    val currentSize = LocalConfiguration.current.screenHeightDp / 20
    val currentPageIcon = Icons.Default.FiberManualRecord
    val otherPageIcon = Icons.Outlined.FiberManualRecord
    val currentPage = remember { mutableIntStateOf(0) }
    val doneWalkthrough = remember { mutableStateOf(false) }
    val numberOfPages = 4 //Starting counting from 0
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(currentPage.intValue){
            0 -> {
                //CHOOSE LANGUAGE PAGE
                ChooseLanguage(context, currentSize)
            }
            in 1 .. 3 -> {
                Spacer(modifier = Modifier.height((currentSize + 50).dp))
                //PAGE DESCRIPTION
                PageDescription(context, currentPage.intValue, currentSize)
                Spacer(modifier = Modifier.height((currentSize - 50).dp))
                //CLASSROOM BUTTON
                ClassroomButton(context, currentPage, currentSize)
                Spacer(modifier = Modifier.height((currentSize - 20).dp))
                //NEW COURSE BUTTON
                NewCourseButton(context, currentPage, currentSize)
                Spacer(modifier = Modifier.height((currentSize - 20).dp))
                //EMERGENCY BUTTON
                EmergencyButton(context, currentPage, currentSize)
            }
            4 -> {
                //CLASSROOM PAGE
                ClassroomPage(context, currentSize)
                Spacer(modifier = Modifier.height((currentSize + 150).dp))
            }
        }
        // NEXT, BACK, SKIP, FINISHED BUTTONS
        Spacer(modifier = Modifier.height((currentSize + 50).dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            if(currentPage.intValue != 0){
                OutlinedButton(
                    modifier = Modifier.width((currentSize + 60).dp),
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(2.dp,Color.White),
                    onClick = {
                        if(currentPage.intValue != 0){
                            currentPage.intValue--
                        }else{
                            preferencesManager.saveBoolean("DoneInitialTour", true)
                            doneWalkthrough.value = true
                        }
                    }
                ) {
                    Text(
                        text = context.getString(R.string.back),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            }else{
                Spacer(modifier = Modifier.width((currentSize + 60).dp))
            }
            Spacer(modifier = Modifier.width((currentSize - 10).dp))
            for(i in 0..4){
                Icon(
                    modifier = Modifier.size((currentSize-20).dp),
                    imageVector = if(currentPage.intValue == i) currentPageIcon else otherPageIcon,
                    contentDescription = "Page $i"
                )
            }
            Spacer(modifier = Modifier.width((currentSize - 10).dp))
            OutlinedButton(
                modifier = Modifier.width((currentSize + 60).dp),
                shape = RoundedCornerShape(50),
                border = BorderStroke(2.dp,Color.White),
                onClick = {
                    currentPage.intValue++
                    if(currentPage.intValue > numberOfPages){
                        preferencesManager.saveBoolean("DoneInitialTour", true)
                        doneWalkthrough.value = true
                    }
                }
            ) {
                Text(text = if(currentPage.intValue < numberOfPages) context.getString(R.string.next) else context.getString(R.string.done), style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary
                ))
            }
        }
    }
    if(doneWalkthrough.value){
        OutroSplash(rootNavController)
    }
}

/**
 * UI visuals for "Classroom" button.
 *
 * @param context application context.
 * @param currentPage current page where the visual appears.
 * @param currentSize static size for the current screen.
 * */
@Composable
private fun ClassroomButton(context: Context, currentPage: MutableIntState, currentSize: Int){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (currentPage.intValue == 1) 1.0f else 0.3f),
        horizontalArrangement = Arrangement.Center
    ){
        OutlinedButton(
            modifier = Modifier.width((currentSize + 250).dp),
            onClick = {

            },
            shape = RoundedCornerShape(50),
            border = BorderStroke(2.dp, Color.White)
        ) {
            Text(
                context.getString(R.string.classroom),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = (currentSize - 20).sp,
                    fontWeight = FontWeight.Bold)
            )
        }
    }
}

/**
 * UI visuals for "New Course" button.
 *
 * @param context application context.
 * @param currentPage current page where the visual appears.
 * @param currentSize static size for the current screen.
 * */
@Composable
private fun NewCourseButton(context: Context, currentPage: MutableIntState, currentSize: Int){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (currentPage.intValue == 2) 1.0f else 0.3f),
        horizontalArrangement = Arrangement.Center
    ){
        OutlinedButton(
            modifier = Modifier.width((currentSize + 250).dp),
            onClick = {

            },
            shape = RoundedCornerShape(50),
            border = BorderStroke(2.dp, Color.White)
        ) {
            Text(
                context.getString(R.string.new_course),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = (currentSize - 20).sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

/**
 * UI visuals for "Emergency" button.
 *
 * @param context application context.
 * @param currentPage current page where the visual appears.
 * @param currentSize static size for the current screen.
 * */
@Composable
private fun EmergencyButton(context: Context, currentPage: MutableIntState, currentSize: Int){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (currentPage.intValue == 3) 1.0f else 0.3f),
        horizontalArrangement = Arrangement.Center
    ){
        OutlinedButton(
            modifier = Modifier.width((currentSize + 250).dp),
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
                    fontSize = (currentSize - 20).sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

/**
 * UI visuals for "Classroom" content.
 *
 * @param context application context.
 * @param currentSize static size for the current screen.
 * */
@Composable
private fun ClassroomPage(context: Context, currentSize: Int){
    val student = Student(name = "Tiffany", course = Languages.ENGLISH, picture = R.raw.helen, points = 50)
    Spacer(modifier = Modifier.height((currentSize + 50).dp))
    Row(
        modifier = Modifier
            .height((currentSize + 200).dp)
            .width((currentSize + 300).dp),
        horizontalArrangement = Arrangement.Center
    ) {
        PageDescription(context, currentPage = 4, currentSize)
    }
    Row (
        modifier = Modifier
            .clip(RoundedCornerShape(30))
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = student.picture),
            contentDescription = "Student memoji",
            modifier = Modifier.size((currentSize + 55).dp)
        )
        Spacer(modifier = Modifier.width((currentSize).dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = AnnotatedString(student.name),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontSize = (currentSize - 20).sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = student.course.toString().replaceFirstChar(Char::titlecase),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontSize = (currentSize - 25).sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(modifier = Modifier.width((currentSize).dp))
        Text(
            text = student.points.toString() + " " + if (student.points == 1) context.getString(R.string.point) else context.getString(R.string.points),
            style = TextStyle(
                color = MaterialTheme.colorScheme.inversePrimary,
                fontSize = (currentSize - 20).sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.width((currentSize - 20).dp))
        Image(
            painter = painterResource(id = R.drawable.delete),
            contentDescription = "Student memoji",
            modifier = Modifier.size((currentSize - 10).dp)
        )
    }
}

/**
 * Prompt to change the language.
 *
 * @param context application context.
 * @param currentSize static size for the current screen.
 * */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ChooseLanguage(context: Context, currentSize: Int){
    Spacer(modifier = Modifier.height((currentSize + 50).dp))
    Row(
        modifier = Modifier
            .height((currentSize + 100).dp)
            .width((currentSize + 300).dp),
        horizontalArrangement = Arrangement.Center
    ) {
        PageDescription(context, currentPage = 0, currentSize)
    }
    Row(
        modifier = Modifier.combinedClickable(
            onClick = {
                changeLanguage(Languages.ENGLISH, context)
            }
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.gb),
            contentDescription = "GB FLAG",
            modifier = Modifier.size((currentSize + 88).dp)
        )
    }
    Row(
        modifier = Modifier.combinedClickable(
            onClick = {
                changeLanguage(Languages.ITALIAN, context)
            }
        ),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.it),
            contentDescription = "GB FLAG",
            modifier = Modifier.size((currentSize + 88).dp)
        )
    }
    Row(
        modifier = Modifier.combinedClickable(
            onClick = {
                changeLanguage(Languages.FRENCH, context)
            }
        )
    ){
        Image(
            painter = painterResource(id = R.drawable.fr),
            contentDescription = "GB FLAG",
            modifier = Modifier.size((currentSize + 88).dp)
        )
    }
}

/**
 * The description of each page.
 *
 * @param context application context.
 * @param currentPage current page where the visual appears.
 * @param currentSize static size for the current screen.
 * */
@Composable
private fun PageDescription(context: Context, currentPage: Int, currentSize: Int){
    val description = when(currentPage){
        0 -> context.getString(R.string.change_language)
        1 -> context.getString(R.string.page_1)
        2 -> context.getString(R.string.page_2)
        3 -> context.getString(R.string.page_3)
        4 -> context.getString(R.string.page_4)
        else -> {
            "TrioLingo"
        }
    }
    Row(
        modifier = Modifier
            .height((currentSize + 300).dp)
            .width((currentSize + 300).dp),
        horizontalArrangement = Arrangement.Center
    ){
        Text(
            text = description,
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                fontSize = (LocalConfiguration.current.screenWidthDp/12-10).sp,
                fontWeight = FontWeight.Bold
            )
        )
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