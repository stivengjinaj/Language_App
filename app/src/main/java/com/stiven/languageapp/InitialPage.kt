package com.stiven.languageapp

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stiven.languageapp.graphs.Graph

/**
 * The first page to show when the user opens the app. The user chooses the destination.
 *
 * @param navController navigation host controller.
 * */
@SuppressLint("UnrememberedMutableState")
@Composable
fun InitialPage(navController: NavHostController) {
    val currentSize = LocalConfiguration.current.screenWidthDp
    val context = LocalContext.current
    Column(
        Modifier.background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height((currentSize/12+30).dp))
        //APP LOGO
        Image(
            painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.logo_dark else R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier.size((currentSize/6+70).dp)
        )

        Spacer(modifier = Modifier.height((currentSize/12+30).dp))
        //WELCOME TEXT
        Row (horizontalArrangement = Arrangement.Center) {
            Text(
                context.getString(R.string.welcome_message),
                style = TextStyle(color = MaterialTheme.colorScheme.secondary,
                    fontSize = (currentSize/12).sp,
                    fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height((currentSize/6+20).dp))
        //MY COURSES BUTTON
        Row (modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
            OutlinedButton(
                modifier = Modifier.width((LocalConfiguration.current.screenWidthDp - 110).dp),
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.MAIN+"/classroom")
                },
                shape = RoundedCornerShape(50),
                border = BorderStroke(2.dp,Color.White)
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

        Spacer(modifier = Modifier.height((currentSize/6-20).dp))
        //NEW COURSE BUTTON
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OutlinedButton(
                modifier = Modifier.width((LocalConfiguration.current.screenWidthDp - 110).dp),
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.MAIN+"/new_course")
                },
                shape = RoundedCornerShape(50),
                border = BorderStroke(2.dp,Color.White)
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

        Spacer(modifier = Modifier.height((currentSize/6-20).dp))
        //EMERGENCY BUTTON
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OutlinedButton(
                modifier = Modifier.width((LocalConfiguration.current.screenWidthDp - 110).dp),
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.MAIN+"/emergency")
                },
                shape = RoundedCornerShape(50),
                border = BorderStroke(2.dp,Color.White),
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
    }
}