package com.stiven.languageapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.stiven.languageapp.entities.Student
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

/**
 * Displays all the registered students and the languages
 * they are learning. In case there are no students, a button
 * will guide the user to navigate to NewCourse page. Clicking
 * the name of the user the, the either the course will start
 * or the user will continue the course he/she is already doing
 *
 * @param studentViewModel view-model that handles student's data
 * @param textToSpeechViewModel view-model for text-to-speech accessibility
 * @param navController navigation controller
 * */
@Composable
fun Classroom(
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    navController: NavHostController
){
    val screenSize = LocalConfiguration.current.screenWidthDp
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
        if(studentViewModel.dataList.value?.isEmpty() == true){
            NoStudentRegistered(navController)
        }else{
            val courseStudent = studentViewModel.dataList.value
            if (courseStudent != null) {
                CoursesList(courseStudent)
            }
        }
    }
}

/**
 * Displays the view if no student is registered.
 *
 * @param navController navigation controller
 * */
@Composable
fun NoStudentRegistered(navController: NavHostController){
    val screenSize = LocalConfiguration.current.screenWidthDp
    val context = LocalContext.current
    Spacer(modifier = Modifier.height((screenSize/2).dp))
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = context.getString(R.string.no_student_found),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = (screenSize/12).sp
            ),
            color = MaterialTheme.colorScheme.inversePrimary
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Row {
        OutlinedButton(
            onClick = {
                navController.navigate(BottomBarScreen.NewCourse.route)
            },
            shape = RoundedCornerShape(50.dp),
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.primary
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = context.getString(R.string.new_course),
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

/**
 * A column containing a row for each student learning a language
 *
 * @param courseStudent the list of the students present in database
 * */
@Composable
fun CoursesList(courseStudent: List<Student>) {
    val context = LocalContext.current
    val screenSize = LocalConfiguration.current.screenWidthDp
    Spacer(modifier = Modifier.height((screenSize/12).dp))
    Row{
        Text(
            text = context.getString(R.string.classroom),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = (screenSize/12).sp
            ),
            color = MaterialTheme.colorScheme.inversePrimary
        )
    }
    Spacer(modifier = Modifier.height(30.dp))
    for(student in courseStudent){
        StudentRow(student)
    }
}

/**
 * Composable function acting as a row containing an image, a column,
 * that contains the student's name and the language he/she is learning,
 * and the points acquired from the student
 *
 * @param student student entity
 * */
@Composable
fun StudentRow(student: Student){
    val screenSize = LocalConfiguration.current.screenWidthDp
    Row (
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(id = student.picture),
            contentDescription = "Student memoji",
            modifier = Modifier.size((LocalConfiguration.current.screenWidthDp / 4-5).dp)
        )
        Spacer(modifier = Modifier.width((screenSize/6-5).dp))
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ClickableText(
                onClick = { /*TODO*/ },
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
        Spacer(modifier = Modifier.width((screenSize/6-5).dp))
        Text(
            text = student.points.toString()+ " " + if(student.points == 1) "Point" else "Points",
            style = TextStyle(
                color = MaterialTheme.colorScheme.inversePrimary,
                fontSize = (screenSize/12-18).sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
    Spacer(modifier = Modifier.height((screenSize/12-20).dp))
}