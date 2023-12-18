package com.stiven.languageapp.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stiven.languageapp.R
import com.stiven.languageapp.navigation.Graph
import com.stiven.languageapp.utils.PreferencesManager

@Composable
fun InitialTour(rootNavController: NavHostController, preferencesManager: PreferencesManager){
    val context = LocalContext.current
    val currentSize = LocalConfiguration.current.screenWidthDp
    val currentPageIcon = Icons.Default.FiberManualRecord
    val otherPageIcon = Icons.Outlined.FiberManualRecord
    val currentPage = remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height((currentSize/12+70).dp))
        Row(
            modifier = Modifier.height((currentSize/12+150).dp).width((currentSize/12+250).dp)
        ) {
            PageDescription(currentPage.intValue)
        }
        Spacer(modifier = Modifier.height((currentSize/2-50).dp))
        //CLASSROOM BUTTON
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (currentPage.intValue == 0) 1.0f else 0.3f),
            horizontalArrangement = Arrangement.Center
        ) {
            ClassroomButton(context, currentSize)
        }
        Spacer(modifier = Modifier.height((currentSize/6-50).dp))
        //NEW COURSE BUTTON
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (currentPage.intValue == 1) 1.0f else 0.3f),
            horizontalArrangement = Arrangement.Center
        ) {
            NewCourseButton(context, currentSize)
        }
        Spacer(modifier = Modifier.height((currentSize/6-50).dp))
        //EMERGENCY BUTTON
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (currentPage.intValue == 2) 1.0f else 0.3f),
            horizontalArrangement = Arrangement.Center
        ) {
            EmergencyButton(context, currentSize)
        }
        Spacer(modifier = Modifier.height((currentSize/2-70).dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            OutlinedButton(
                modifier = Modifier.width((currentSize/6+33).dp),
                shape = RoundedCornerShape(50),
                border = BorderStroke(2.dp,Color.White),
                onClick = {
                    if(currentPage.intValue != 0){
                        currentPage.intValue--
                    }else{
                        preferencesManager.saveBoolean("DoneInitialTour", true)
                        rootNavController.navigate(Graph.INITIAL)
                    }
                }
            ) {
                Text(
                    text = if(currentPage.intValue != 0) "Back" else "Skip",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            }
            Spacer(modifier = Modifier.width((currentSize/12+33).dp))
            Icon(imageVector = if(currentPage.intValue == 0) currentPageIcon else otherPageIcon, contentDescription = "Current page")
            Icon(imageVector = if(currentPage.intValue == 1) currentPageIcon else otherPageIcon, contentDescription = "Current page")
            Icon(imageVector = if(currentPage.intValue == 2) currentPageIcon else otherPageIcon, contentDescription = "Current page")
            Spacer(modifier = Modifier.width((currentSize/12+30).dp))
            OutlinedButton(
                modifier = Modifier.width((currentSize/6+30).dp),
                shape = RoundedCornerShape(50),
                border = BorderStroke(2.dp,Color.White),
                onClick = {
                    currentPage.intValue++
                    if(currentPage.intValue > 2){
                        preferencesManager.saveBoolean("DoneInitialTour", true)
                        rootNavController.navigate(Graph.INITIAL)
                    }
                }
            ) {
                Text(text = if(currentPage.intValue < 2) "Next" else "Finish", style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary
                ))
            }
        }
    }
}

@Composable
fun ClassroomButton(context: Context, currentSize: Int){
    OutlinedButton(
        modifier = Modifier.width((LocalConfiguration.current.screenWidthDp - 110).dp),
        onClick = {

        },
        shape = RoundedCornerShape(50),
        border = BorderStroke(2.dp, Color.White)
    ) {
        Text(
            context.getString(R.string.classroom),
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = (currentSize/12-12).sp,
                fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun NewCourseButton(context: Context, currentSize: Int){
    OutlinedButton(
        modifier = Modifier.width((LocalConfiguration.current.screenWidthDp - 110).dp),
        onClick = {

        },
        shape = RoundedCornerShape(50),
        border = BorderStroke(2.dp, Color.White)
    ) {
        Text(
            context.getString(R.string.new_course),
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = (currentSize/12-12).sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun EmergencyButton(context: Context, currentSize: Int){
    OutlinedButton(
        modifier = Modifier.width((LocalConfiguration.current.screenWidthDp - 110).dp),
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
                fontSize = (currentSize/12-12).sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun PageDescription(pageIndex: Int){
    val description = when(pageIndex){
        0 -> "Click this button in order to go where the classroom is and where all students are. If you created a student for yourself you will find it here. Clicking it will get you to your personalized portfolio."
        1 -> "Click this button in order to go and create a student for yourself with a language you want to learn."
        2 -> "Click this button in order to use the dictionary for emergency cases."
        else -> {
            "TrioLingo"
        }
    }
    Text(
        text = description,
        style = TextStyle(
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            fontSize = (LocalConfiguration.current.screenWidthDp/12-10).sp
        )
    )
}