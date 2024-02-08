package com.stiven.languageapp.screens

import android.graphics.RectF
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stiven.languageapp.R
import com.stiven.languageapp.model.Cloud
import com.stiven.languageapp.utils.Clouds
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Composable that contains the Roadmap an its logics.
 *
 * @param rootNavController Root navigation controller of the application.
 * @param navController Navigation controller for the student section.
 * @param studentViewModel ViewModel of the current student.
 * @param textToSpeechViewModel ViewModel that handles text-to-speech options.
 * @param studentId Current student's id.
 * */
@Composable
fun Lessons(
    rootNavController: NavHostController,
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    studentId: String
) {
    val id = studentId.toInt()
    val student = studentViewModel.dataList.value!!.find { it.id == id }
    val screenSize = LocalConfiguration.current.screenWidthDp
    val background = if(isSystemInDarkTheme()) R.drawable.cloudybackgrounddark else R.drawable.cloudybackgroundlight
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //Row containing the app title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .height((screenSize / 6).dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "TrioLingo",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = (screenSize / 12).sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = background),
                    contentScale = ContentScale.FillHeight
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /*Image(
                painter = painterResource(id = student!!.picture),
                contentDescription = "Student memoji",
                modifier = Modifier.size((LocalConfiguration.current.screenWidthDp/4+20).dp)
            )*/
            Spacer(modifier = Modifier.height((screenSize/6+30).dp))
            RoadMap(screenSize, student!!.picture)
        }
    }
}

/**
 * The core of the Roadmap graphics.
 *
 * @param screenSize The width of the screen used to scale the view.
 * */
@Composable
fun RoadMap(screenSize: Int, picture: Int ) {
    val roadColor = MaterialTheme.colorScheme.primary
    val undoneCloud = painterResource(id = R.drawable.undone_checkpoint)
    val doneCloud = painterResource(id = R.drawable.done_checkpoint)
    val avatar = painterResource(id = picture)
    val cloudCoordinates = listOf(
        Cloud(Clouds.CLOUD1, Pair(screenSize * 2f, screenSize * 0.01f), checkCloudStatus()),
        Cloud(Clouds.CLOUD2, Pair(screenSize * 0.7f, screenSize * 0.75f), checkCloudStatus()),
        Cloud(Clouds.CLOUD3, Pair(screenSize * 1.27f, screenSize * 1.3f), checkCloudStatus()),
        Cloud(Clouds.CLOUD4, Pair(screenSize * 0.55f, screenSize * 2.1f), checkCloudStatus())
    )
    val cloudOffsets = remember {
        cloudCoordinates.map { Animatable(it.position.second) }
    }
    val roadCoordinates = listOf(
        //ROAD 1
        Offset(screenSize * 0.3f, screenSize * 0.2f),
        Offset(screenSize * 1.9f, screenSize * 0.2f),

        //CLOUD

        //ROAD 2
        Offset(screenSize * 2.2f, screenSize * 0.45f),
        Offset(screenSize * 2.2f, screenSize * 0.9f),
        //ROAD 3
        Offset(screenSize * 2.2f, screenSize * 0.9f),
        Offset(screenSize * 1.2f, screenSize * 0.9f),

        //CLOUD

        //ROAD 4
        Offset(screenSize * 0.65f, screenSize * 0.9f),
        Offset(screenSize * 0.3f, screenSize * 0.9f),
        //ROAD 5
        Offset(screenSize * 0.3f, screenSize * 0.9f),
        Offset(screenSize * 0.3f, screenSize * 1.5f),
        //ROAD 6
        Offset(screenSize * 0.3f, screenSize * 1.5f),
        Offset(screenSize * 1.2f, screenSize * 1.5f),

        //CLOUD

        //ROAD 7
        Offset(screenSize * 1.75f, screenSize * 1.5f),
        Offset(screenSize * 2.2f, screenSize * 1.5f),

        //ROAD 8
        Offset(screenSize * 2.2f, screenSize * 1.5f),
        Offset(screenSize * 2.2f, screenSize * 2.3f),
        //ROAD 9
        Offset(screenSize * 2.2f, screenSize * 2.3f),
        Offset(screenSize * 1f, screenSize * 2.3f),

        //CLOUD

        //ROAD 10
        Offset(screenSize * 0.5f, screenSize * 2.3f),
        Offset(screenSize * 0.3f, screenSize * 2.3f),
        //ROAD 11
        Offset(screenSize * 0.3f, screenSize * 2.3f),
        Offset(screenSize * 0.3f, screenSize * 3f),
        //ROAD 12
        Offset(screenSize * 0.3f, screenSize * 3f),
        Offset(screenSize * 5f, screenSize * 3f)
    )
    val avatarCoordinates = listOf(
        Pair(roadCoordinates[0].x - 70f, roadCoordinates[0].y - 100)
    )
    val avatarOffsets = remember {
        avatarCoordinates.map { Animatable(it.second) }
    }
    //val avatarProgress = pointsToRoadMap(20, avatarCoordinates[0],roadCoordinates, cloudCoordinates.map { it.position })
    LaunchedEffect(Unit) {
        cloudOffsets.forEachIndexed { index, animatable ->
            launch {
                while (true) {
                    val targetValue = if (animatable.value < cloudCoordinates[index].position.second) cloudCoordinates[index].position.second + Random.nextDouble(
                        5.0, 10.0
                    ).toFloat()
                    else cloudCoordinates[index].position.second - Random.nextDouble(
                        5.0, 10.0
                    ).toFloat()
                    animatable.animateTo(
                        targetValue = targetValue,
                        animationSpec = tween(durationMillis = Random.nextInt(500,1000), easing = LinearEasing)
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        avatarOffsets.forEachIndexed { _, animatable ->
            launch {
                while(true){
                    val avatarTargetValue = if (animatable.value < avatarCoordinates[0].second) avatarCoordinates[0].second + Random.nextDouble(5.0,10.0).toFloat()
                    else avatarCoordinates[0].second - Random.nextDouble(5.0,10.0).toFloat()
                    animatable.animateTo(
                        targetValue = avatarTargetValue,
                        animationSpec = tween(durationMillis = Random.nextInt(500,1000), easing = LinearEasing)
                    )
                }
            }
        }
    }

    Canvas(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures {
                // Handle clicks on the canvas
                // Calculate the click position relative to each cloud's position
                val clickOffset = Offset(it.x, it.y)
                cloudCoordinates.forEachIndexed { _, (cloud, position) ->
                    val cloudRect = Offset(position.first, position.second).toRect()
                    if (cloudRect.contains(clickOffset.x, clickOffset.y)) {
                        println("Cloud $cloud clicked!")
                    }
                }
            }
        }
    ) {
        // Draw the road lines
        drawPoints(
            points = roadCoordinates,
            pointMode = PointMode.Lines,
            color = roadColor,
            strokeWidth = 50f,
            cap = StrokeCap.Round
        )

        cloudOffsets.forEachIndexed{ index, offset ->
            if(cloudCoordinates[index].status){
                with(doneCloud){
                    translate(left = cloudCoordinates[index].position.first, top = offset.value) {
                        draw(size = Size(160f, 130f))
                    }
                }
            }else{
                with(undoneCloud){
                    translate(left = cloudCoordinates[index].position.first, top = offset.value) {
                        draw(size = Size(160f, 130f))
                    }
                }
            }
        }
        avatarOffsets.forEachIndexed{ _, offset ->
            with(avatar){
                translate(left = avatarCoordinates[0].first, top = offset.value - 100f){
                    draw(size = Size(200f, 200f))
                }
            }
        }
    }
}
/**
 * TODO. Function that checks if the lesson of the cloud has been learnt.
 *
 * @return true if the student has learnt the lesson, false otherwise.
 * */
private fun checkCloudStatus(): Boolean{

    return false
}

/**
 * Function that handles the coordinates of the touching area for the clouds.
 * */
private fun Offset.toRect(size: Size = Size(160f, 130f)) = RectF(
    this.x,
    this.y,
    this.x + size.width,
    this.y + size.height
)

/**
 * TODO: Function tha determines the position of the avatar base on student's points.
 *
 * @param points student's points.
 * @param avatarCoordinates avatar coordinates.
 * @param roadCoordinates road coordinates.
 * @param cloudCoordinates cloud coordinates.
 *
 * @return Pair<Float,Float> the new position of the avatar.
 * */
private fun pointsToRoadMap(points: Int, avatarCoordinates: Pair<Float, Float>, roadCoordinates: List<Offset>, cloudCoordinates: List<Pair<Float,Float>>): Pair<Float,Float>{
    val currentRoadCoordinates = Pair(avatarCoordinates.first+70f,avatarCoordinates.second+100f)
    val step = currentRoadCoordinates.first / 50f
    return Pair(currentRoadCoordinates.first + step, currentRoadCoordinates.second)
}