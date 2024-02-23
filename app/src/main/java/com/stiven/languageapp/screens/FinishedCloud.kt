package com.stiven.languageapp.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.stiven.languageapp.R
import com.stiven.languageapp.navigation.Graph
import com.stiven.languageapp.view.LogoBannerNavigation
import kotlinx.coroutines.delay

@Composable
fun FinishedCloud(
    studentPicture: Int,
    studentPoints: Int,
    studentId: String,
    rootNavController: NavHostController
){
    val studentAvatar = painterResource(id = studentPicture)
    val corona = painterResource(id = getCoronaPicture(studentPoints))
    val screenSize = LocalConfiguration.current.screenWidthDp
    LocalConfiguration.current.screenHeightDp
    val avatarOffset = Offset((screenSize - 150).toFloat(), screenSize.toFloat())
    val fadeAlpha = remember {
        mutableFloatStateOf(0f)
    }
    LaunchedEffect(Unit) {
        // Wait for 1 second before starting the fade-in animation
        delay(1000)
        for(i in 1..100){
            fadeAlpha.floatValue += 0.01f
            delay(10)
        }

    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoBannerNavigation(
            screenSize = screenSize,
            onBackButtonClick = {
                rootNavController.navigate(Graph.LESSONS+"/$studentId")
            }
        )
        Canvas(
            modifier = Modifier.fillMaxSize()
        ){
            with(studentAvatar){
                translate (left = avatarOffset.x, top = avatarOffset.y) {
                    draw(
                        size = Size((screenSize+150).toFloat(), (screenSize+150).toFloat())
                    )
                }
            }
            with(corona) {
                translate(left = avatarOffset.x + 100, top = avatarOffset.y - 150) {
                    draw(
                        size = Size((screenSize - 50).toFloat(), (screenSize - 50).toFloat()),
                        alpha = fadeAlpha.floatValue
                    )
                }
            }
        }
    }
}

fun getCoronaPicture(studentPoints: Int): Int{
    return when(studentPoints) {
        in 0..49 -> {
            R.drawable.firstcrown
        }
        in 50..99 -> {
            R.drawable.secondcrown
        }
        in 100..149 -> {
            R.drawable.thirdcrown
        }
        else -> R.drawable.firstcrown
    }
}