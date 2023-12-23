package com.stiven.languageapp.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stiven.languageapp.R
import com.stiven.languageapp.model.Student
import com.stiven.languageapp.navigation.Graph
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

/**
 * Composable function acting as a row containing an image, a column,
 * that contains the student's name and the language he/she is learning,
 * and the points acquired from the student.
 *
 * @param rootNavController root navHost to shift navigation graph.
 * @param student student entity.
 * @param studentViewModel view-model that handles student's data
 * @param textToSpeechViewModel view-model for text-to-speech accessibility.
 * */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StudentView(rootNavController: NavHostController, student: Student, studentViewModel: StudentViewModel, textToSpeechViewModel: TextToSpeechViewModel) {
    val screenSize = LocalConfiguration.current.screenWidthDp
    val context = LocalContext.current
    Row (
        modifier = Modifier.combinedClickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {
                rootNavController.navigate(Graph.LESSONS+"/${student.id}")
            },
            onLongClick = {
                textToSpeechViewModel.textToSpeech(context, context.getString(R.string.student_account))
            }
        ),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(id = student.picture),
            contentDescription = "Student memoji",
            modifier = Modifier.size((LocalConfiguration.current.screenWidthDp/4).dp)
        )
        Spacer(modifier = Modifier.width((screenSize/6-15).dp))
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ClickableText(
                onClick = {
                    rootNavController.navigate(Graph.LESSONS+"/${student.id}")
                },
                text = AnnotatedString(student.name),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontSize = (screenSize/12-10).sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = student.course.toString().replaceFirstChar(Char::titlecase),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontSize = (screenSize/12-18).sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(modifier = Modifier.width((screenSize/6-20).dp))
        Text(
            text = student.points.toString()+ " " + if(student.points == 1) context.getString(R.string.point) else context.getString(R.string.points),
            style = TextStyle(
                color = MaterialTheme.colorScheme.inversePrimary,
                fontSize = (screenSize/12-18).sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.width((screenSize/6-50).dp))
        Image(
            painter = painterResource(id = R.drawable.delete),
            contentDescription = "Student memoji",
            modifier = Modifier.size((LocalConfiguration.current.screenWidthDp/12-5).dp).combinedClickable(
                onClick = {
                    studentViewModel.deleteStudent(student.name, student.course)
                    rootNavController.navigate(Graph.MAIN+"/classroom")
                }
            )
        )

    }
    Spacer(modifier = Modifier.height((screenSize/12-20).dp))
}