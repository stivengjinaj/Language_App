package com.stiven.languageapp.screens

import android.graphics.RectF
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.stiven.languageapp.R
import com.stiven.languageapp.model.Cloud
import com.stiven.languageapp.navigation.FirstCloudRoutes
import com.stiven.languageapp.navigation.FourthCloudNavGraph
import com.stiven.languageapp.navigation.SecondCloudNavGraph
import com.stiven.languageapp.navigation.ThirdCloudNavGraph
import com.stiven.languageapp.utils.CloudType
import com.stiven.languageapp.utils.LearningType
import com.stiven.languageapp.utils.QuestionType
import com.stiven.languageapp.view.LogoBanner
import com.stiven.languageapp.viewmodels.LettersLearntViewModel
import com.stiven.languageapp.viewmodels.QuizAnswerViewModel
import com.stiven.languageapp.viewmodels.StudentViewModel
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.random.Random

/**
 * Composable that contains the Roadmap an its logics.
 *
 * @param navController Navigation controller for the student section.
 * @param studentViewModel ViewModel of the current student.
 * @param studentId Current student's id.
 * */
@Composable
fun Lessons(
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    studentId: String,
    studentPoints: Int
) {
    val student = studentViewModel.dataList.value!!.find { it.id == studentId.toInt() }
    val screenSize = LocalConfiguration.current.screenWidthDp
    val background = if(isSystemInDarkTheme()) R.drawable.cloudybackgrounddark else R.drawable.cloudybackgroundlight
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //Row containing the app title
        LogoBanner(screenSize)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = background),
                    contentScale = ContentScale.FillHeight
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height((screenSize/6+30).dp))

            RoadMap(
                screenSize = screenSize,
                picture = student!!.picture,
                navController = navController,
                studentPoints = student.points
            )
        }
    }
}

/**
 * The core of the Roadmap graphics. This composable contains a list of roads with offsets,
 * clouds and the avatar that are drawn, animated and updated.
 *
 * @param screenSize The width of the screen used to scale the view.
 * @param picture Avatar image.
 * @param navController Cloud navigation controller.
 * @param studentPoints Student's points.
 *
 * */
@Composable
fun RoadMap(
    screenSize: Int,
    picture: Int,
    navController: NavHostController,
    studentPoints: Int
) {
    val cloudPoints = remember {
        mutableIntStateOf(0)
    }
    cloudPoints.intValue = studentPoints
    val roadColor = MaterialTheme.colorScheme.primary
    val completedRoadColor = MaterialTheme.colorScheme.tertiary
    val undoneCloud = painterResource(id = R.drawable.undone_checkpoint)
    val doneCloud = painterResource(id = R.drawable.done_checkpoint)
    val crown = when(studentPoints) {
        in 50..99 -> {
            painterResource(id = R.drawable.firstcrown)
        }
        in 100..149 -> {
            painterResource(id = R.drawable.secondcrown)
        }
        in 150..199 -> {
            painterResource(id = R.drawable.thirdcrown)
        }
        else -> {
            painterResource(id = R.drawable.firstcrown)
        }
    }
    val avatar = painterResource(id = picture)
    val cloudCoordinates = listOf(
        Cloud(CloudType.CLOUD1, Pair(screenSize * 2f, screenSize * 0.01f), checkCloudStatus(studentPoints, CloudType.CLOUD1), 50),
        Cloud(CloudType.CLOUD2, Pair(screenSize * 0.7f, screenSize * 0.75f), checkCloudStatus(studentPoints, CloudType.CLOUD2), 100),
        Cloud(CloudType.CLOUD3, Pair(screenSize * 1.27f, screenSize * 1.3f), checkCloudStatus(studentPoints, CloudType.CLOUD3), 150),
        Cloud(CloudType.CLOUD4, Pair(screenSize * 0.55f, screenSize * 2.1f), checkCloudStatus(studentPoints, CloudType.CLOUD4), 200)
    )
    val cloudOffsets = remember {
        cloudCoordinates.map { Animatable(it.position.second) }
    }
    val roadCoordinates = listOf(
        //ROAD 1
        Pair(Offset(screenSize * 0.3f, screenSize * 0.2f), Offset(screenSize * 1.9f, screenSize * 0.2f)),
        //-----------------------------------------CLOUD------------------------------------------------------------
        //ROAD 2
        Pair(Offset(screenSize * 2.2f, screenSize * 0.45f), Offset(screenSize * 2.2f, screenSize * 0.9f)),
        //ROAD 3
        Pair(Offset(screenSize * 2.2f, screenSize * 0.9f), Offset(screenSize * 1.2f, screenSize * 0.9f)),
        //-----------------------------------------CLOUD------------------------------------------------------------
        //ROAD 4
        Pair(Offset(screenSize * 0.65f, screenSize * 0.9f), Offset(screenSize * 0.3f, screenSize * 0.9f)),
        //ROAD 5
        Pair(Offset(screenSize * 0.3f, screenSize * 0.9f), Offset(screenSize * 0.3f, screenSize * 1.5f)),
        //ROAD 6
        Pair(Offset(screenSize * 0.3f, screenSize * 1.5f), Offset(screenSize * 1.2f, screenSize * 1.5f)),
        //-----------------------------------------CLOUD------------------------------------------------------------
        //ROAD 7
        Pair(Offset(screenSize * 1.75f, screenSize * 1.5f), Offset(screenSize * 2.2f, screenSize * 1.5f)),
        //ROAD 8
        Pair(Offset(screenSize * 2.2f, screenSize * 1.5f), Offset(screenSize * 2.2f, screenSize * 2.3f)),
        //ROAD 9
        Pair(Offset(screenSize * 2.2f, screenSize * 2.3f), Offset(screenSize * 1f, screenSize * 2.3f)),
        //-----------------------------------------CLOUD------------------------------------------------------------
        //ROAD 10
        Pair(Offset(screenSize * 0.5f, screenSize * 2.3f), Offset(screenSize * 0.3f, screenSize * 2.3f)),
        //ROAD 11
        Pair(Offset(screenSize * 0.3f, screenSize * 2.3f), Offset(screenSize * 0.3f, screenSize * 3f)),
        //ROAD 12
        Pair(Offset(screenSize * 0.3f, screenSize * 3f), Offset(screenSize * 5f, screenSize * 3f))
    )
    val pointsToPosition = pointsToPosition(cloudPoints.intValue, roadCoordinates, cloudCoordinates)
    val avatarCoordinates = remember {
        mutableListOf(pointsToPosition.second)
    }
    val avatarOffsets = remember {
        mutableListOf(
            Animatable(avatarCoordinates[0].second)
        )
    }
    avatarCoordinates[0] = pointsToPosition(cloudPoints.intValue, roadCoordinates, cloudCoordinates).second
    val completedRoads = pointsToPosition.first
    val completedRoadsList = roadCoordinates
        .subList(0, completedRoads)
        .toList()
    //Calculates the current position of the avatar and draws the road until it reaches the avatar
    val partialCompletedRoad = partiallyCompletedRoad(completedRoads, roadCoordinates, cloudCoordinates, avatarCoordinates[0])

    //Clouds animation
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
    //Avatar animation
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
                Log.d("POINTS", studentPoints.toString())
                val clickOffset = Offset(it.x, it.y)
                cloudCoordinates.forEachIndexed { _, (cloud, position) ->
                    val cloudRect = Offset(position.first, position.second).toRect()
                    if (cloudRect.contains(clickOffset.x, clickOffset.y)) {
                        when (cloud) {
                            CloudType.CLOUD1 -> {
                                navController.navigate(FirstCloudRoutes.FIRST_CLOUD)
                            }

                            CloudType.CLOUD2 -> {
                                if (cloudPoints.intValue >= 50) {
                                    navController.navigate(SecondCloudNavGraph.SECOND_CLOUD)
                                }
                            }

                            CloudType.CLOUD3 -> {
                                if (cloudPoints.intValue >= 100) {
                                    navController.navigate(ThirdCloudNavGraph.THIRD_CLOUD)
                                }
                            }

                            CloudType.CLOUD4 -> {
                                if (cloudPoints.intValue >= 150) {
                                    navController.navigate(FourthCloudNavGraph.FOURTH_CLOUD)
                                }
                            }
                        }
                    }
                }
            }
        }
    ) {
        // Draw the road lines
        customDraw(this, roadCoordinates.flatMap { listOf(it.first, it.second) }.toList(), roadColor)
        customDraw(this, completedRoadsList.flatMap { listOf(it.first, it.second).toList() }, completedRoadColor)
        customDraw(this, partialCompletedRoad.flatMap { listOf(it.first, it.second) }.toList(), completedRoadColor)
        // Draw clouds
        cloudOffsets.forEachIndexed{ index, offset ->
            //If cloud status is true (lesson finished), change cloud color.
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
        //Draw avatar
        avatarOffsets.forEachIndexed{ _, offset ->
            with(avatar){
                translate(left = avatarCoordinates[0].first, top = offset.value - 100f){
                    draw(size = Size(200f, 200f))
                }
            }
            if(studentPoints >= 50){
                with(crown){
                    translate (left = avatarCoordinates[0].first + 45, top = offset.value - 130f) {
                        draw(size = Size(120f, 100f))
                    }
                }
            }
        }
    }
}

/**
 * Function that calculates the position of the avatar and the road he has
 * completed. If the avatar has not completed the entire road, it returns the
 * position of a new road that starts at beginning of the current road and it
 * ends in the avatar's position, creating the a "following road" effect.
 *
 * @param completedRoads Number of completed roads.
 * @param roadCoordinates Coordinates of all roads.
 * @param cloudCoordinates Coordinates of all clouds.
 * @param avatarCoordinates Coordinated of avatar.
 *
 * @return Coordinates of the following road.
 * */
fun partiallyCompletedRoad(
    completedRoads: Int,
    roadCoordinates: List<Pair<Offset, Offset>>,
    cloudCoordinates: List<Cloud>,
    avatarCoordinates: Pair<Float, Float>
): List<Pair<Offset, Offset>>{

    //Checks the road orientation
    return listOf(
        if(calculateRoadLength(Pair(
                roadCoordinates[completedRoads].first,
                Offset(avatarCoordinates.first,avatarCoordinates.second))
            ).first == 0f) {
            //Road is vertical, takes the last completed road and the offset goes to the avatar's position
            Pair(
                Offset(roadCoordinates[completedRoads].first.x,roadCoordinates[completedRoads].first.y),
                Offset(roadCoordinates[completedRoads].first.y, avatarCoordinates.second)
            )
        }else {
            //In case of horizontal road, if the avatar has reached a cloud the road drawing stops to the last road
            //before the cloud.
            if(cloudCoordinates.flatMap { listOf( it.position ) }.toList().contains(avatarCoordinates)){
                Pair(
                    Offset(roadCoordinates[completedRoads-1].first.x,roadCoordinates[completedRoads-1].first.y),
                    Offset(roadCoordinates[completedRoads-1].second.x, roadCoordinates[completedRoads-1].second.y)
                )
            }else{
                Pair(
                    Offset(roadCoordinates[completedRoads].first.x,roadCoordinates[completedRoads].first.y),
                    Offset(avatarCoordinates.first + 70, avatarCoordinates.second+100)
                )
            }
        }
    )
}



/**
 * Function tha determines the position of the avatar base on student's points.
 *
 * @param points student's points.
 * @param roadCoordinates road coordinates.
 * @param clouds cloud coordinates.
 *
 * @return Pair<Int,Pair<Float,Float>> the new position of the avatar.
 * */
private fun pointsToPosition(
    points: Int,
    roadCoordinates: List<Pair<Offset, Offset>>,
    clouds: List<Cloud>
): Pair<Int,Pair<Float,Float>>{
    val pointsRange: Pair<Int, Int>
    val segmentIndex: Int
    val completedRoads: Int
    when (points) {
        in 0..50 -> {
            segmentIndex = 0
            completedRoads = when (points) {
                in 0..49 -> 0
                else -> 1
            }
            pointsRange = Pair(0,50)
        }
        in 51..75 -> {
            segmentIndex = 1
            completedRoads = when (points) {
                in 51..74 -> 1
                else -> 2
            }
            pointsRange = Pair(51,75)
        }
        in 76..100 -> {
            segmentIndex = 2
            completedRoads = when (points) {
                in 76..99 -> 2
                else -> 3
            }
            pointsRange = Pair(76,100)
        }
        in 101..117 -> {
            segmentIndex = 3
            completedRoads = when (points) {
                in 101..116 -> 3
                else -> 4
            }
            pointsRange = Pair(101,117)
        }
        in 118..134 -> {
            segmentIndex = 4
            completedRoads = when (points) {
                in 118..133 -> 4
                else -> 5
            }
            pointsRange = Pair(118,134)
        }
        in 134..150 -> {
            segmentIndex = 5
            completedRoads = when (points) {
                in 134..149 -> 5
                else -> 6
            }
            pointsRange = Pair(134,150)
        }
        in 151..167 -> {
            segmentIndex = 6
            completedRoads = when (points) {
                in 151..166 -> 6
                else -> 7
            }
            pointsRange = Pair(151,167)
        }
        in 168..184 -> {
            segmentIndex = 7
            completedRoads = when (points) {
                in 168..183 -> 7
                else -> 8
            }
            pointsRange = Pair(168,184)
        }
        in 185..200 -> {
            segmentIndex = 8
            completedRoads = when (points) {
                in 185..199 -> 8
                else -> 9
            }
            pointsRange = Pair(185,200)
        }
        else -> {
            segmentIndex = 8
            completedRoads = 9
            pointsRange = Pair(200,200)
        }
    }
    val cloudIndex = when (segmentIndex){
        0 -> 0
        in 1..2 -> 1
        in 3..5 -> 2
        in 6..8 -> 3
        else -> {3}
    }
    if(points == clouds[cloudIndex].cloudThreshold){
        return Pair(completedRoads, Pair(clouds[cloudIndex].position.first,clouds[cloudIndex].position.second))
    }
    val roadSegment = roadCoordinates[segmentIndex]
    val segmentLength = calculateRoadLength(roadSegment)

    //The road is horizontal, otherwise vertical.
    return if(segmentLength.second == 0f){
        val xOffset = mapValue(points, pointsRange, Pair(roadSegment.first.x,roadSegment.second.x))
        Pair(completedRoads, Pair(xOffset - 70f, roadSegment.second.y - 100f))
    } else{
        val yOffset = mapValue(points, pointsRange, Pair(roadSegment.first.y,roadSegment.second.y))
        Pair(completedRoads, Pair(roadSegment.second.x - 70f, yOffset - 100f))
    }
}

/**
 * Function returns an Int value in an interval, mapped in another interval of floats. The output
 * is a float coordinate that will be used as offset from another starting point and it won't exceed
 * the toRange maximum.
 *
 * @param value Value to map.
 * @param fromRange Pair<Int,Int> range where value currently is.
 * @param toRange Pair<Float,Float> range where the value is mapped.
 *
 * @return Float-type offset.
 * */
private fun mapValue(value: Int, fromRange: Pair<Int, Int>, toRange: Pair<Float, Float>): Float {
    val (fromMin, fromMax) = fromRange
    val (toMin, toMax) = toRange

    val clampedValue = value.coerceIn(fromMin, fromMax)

    val ratio = (clampedValue - fromMin).toFloat() / (fromMax - fromMin)

    return toMin + ratio * (toMax - toMin)
}

/**
 * Function that calculates the horizontal and vertical length of the road.
 *
 * @param positions the coordinates of the road to calculate.
 * @return Pair of <Float, Float>. The first float is the horizontal distance, while the second
 *         one is the vertical distance. If the road is horizontal, the second float will be 0.
 *         Same thing for the first float in case of vertical road.
 * */
private fun calculateRoadLength(positions: Pair<Offset,Offset>): Pair<Float, Float>{
    return Pair(abs(positions.first.x - positions.second.x), abs(positions.first.y - positions.second.y))
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
 * DrawScope function that draws a list of offsets.
 *
 * @param drawScope DrawScope where to draw.
 * @param points Offsets to draw.
 * @param color Color of drawings.
 * */
fun customDraw(
    drawScope: DrawScope,
    points: List<Offset>,
    color: Color,
) {
    drawScope.drawPoints(
        points = points,
        pointMode = PointMode.Lines,
        color = color,
        strokeWidth = 50f,
        cap = StrokeCap.Round
    )
}

/**
 * Function that checks if the lesson of the cloud has been learnt.
 *
 * @return true if the student has learnt the lesson, false otherwise.
 * */
private fun checkCloudStatus(studentPoints: Int, cloudType: CloudType): Boolean{
    return when(cloudType){
        CloudType.CLOUD1 -> {
            studentPoints >= 50
        }
        CloudType.CLOUD2 -> {
            studentPoints >= 100
        }
        CloudType.CLOUD3 -> {
            studentPoints >= 150
        }
        CloudType.CLOUD4 -> {
            studentPoints >= 200
        }
    }
}

/**
 * Function demonstrate how points work without completely
 * completing the exercises.
 *
 * @param studentId student id to check.
 * @param lettersLearntViewModel letters learnt viewModel.
 * @param quizAnswerViewModel answered quiz viewModel.
 * @param studentViewModel student viewModel
 * */
fun demoInsertStudentPoints(
    studentId: String,
    lettersLearntViewModel: LettersLearntViewModel,
    quizAnswerViewModel: QuizAnswerViewModel,
    studentViewModel: StudentViewModel
){
    val lettersPronounced = lettersLearntViewModel.dataList.value?.filter { it.studentId == studentId && it.learningType == LearningType.PRONOUNCING }?.size
    val lettersWritten = lettersLearntViewModel.dataList.value?.filter { it.studentId == studentId && it.learningType == LearningType.WRITTEN }?.size
    val quizAnswered = quizAnswerViewModel.dataList.value?.filter { it.studentId == studentId && it.questionType == QuestionType.TRANSLATE }?.size
    val blankAnswered = quizAnswerViewModel.dataList.value?.filter { it.studentId == studentId && it.questionType == QuestionType.FILL_BLANK }?.size

    if(lettersPronounced != null && lettersWritten != null && quizAnswered != null && blankAnswered != null){
        val pronounced = if(lettersPronounced >= 1) 50 else 0
        val written = if(lettersWritten >= 1) 50 else 0
        val translations = if(quizAnswered >= 1) 50 else 0
        val blank = if(blankAnswered >= 1) 50 else 0
        studentViewModel.updateStudent(studentId, pronounced+written+translations+blank)
    }
}