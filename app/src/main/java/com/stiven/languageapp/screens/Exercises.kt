package com.stiven.languageapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Draw
import androidx.compose.material.icons.rounded.Quiz
import androidx.compose.material.icons.rounded.RecordVoiceOver
import androidx.compose.material.icons.rounded.Translate
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stiven.languageapp.R
import com.stiven.languageapp.navigation.FirstCloudRoutes
import com.stiven.languageapp.navigation.SecondCloudNavGraph
import com.stiven.languageapp.navigation.ThirdCloudNavGraph
import com.stiven.languageapp.view.ExercisesCard
import com.stiven.languageapp.view.LogoBanner

/**
 * Composable view that shows all the exercises present.
 *
 * @param studentNavController navController to navigate.
 * */
@Composable
fun Exercises(
    studentNavController: NavHostController
){
    val screenSize = LocalConfiguration.current.screenWidthDp
    val context = LocalContext.current
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        LogoBanner(screenSize = screenSize)
        Spacer(modifier = Modifier.height((screenSize/6 - 40).dp))
        Row {
            Text(
                text = context.getString(R.string.allExercises),
                color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = (screenSize/6 - 40).sp
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6 - 40).dp))
        ExercisesCard(
            exercise = context.getString(R.string.alphabetPronouncing),
            trailingIcon = Icons.Rounded.RecordVoiceOver,
            onClick = {
                studentNavController.navigate(FirstCloudRoutes.FIRST_CLOUD)
            }
        )
        Spacer(modifier = Modifier.height((screenSize/6 - 40).dp))
        ExercisesCard(
            exercise = context.getString(R.string.alphabetWriting),
            trailingIcon = Icons.Rounded.Draw,
            onClick = {
                studentNavController.navigate(SecondCloudNavGraph.SECOND_CLOUD)
            }
        )
        Spacer(modifier = Modifier.height((screenSize/6 - 40).dp))
        ExercisesCard(
            exercise = context.getString(R.string.translationQuiz),
            trailingIcon = Icons.Rounded.Translate,
            onClick = {
                studentNavController.navigate(ThirdCloudNavGraph.THIRD_CLOUD)
            }
        )
        Spacer(modifier = Modifier.height((screenSize/6 - 40).dp))
        ExercisesCard(
            exercise = context.getString(R.string.fillInQuiz),
            trailingIcon = Icons.Rounded.Quiz,
            onClick = {
                studentNavController.navigate(FirstCloudRoutes.FIRST_CLOUD)
            }
        )
    }
}