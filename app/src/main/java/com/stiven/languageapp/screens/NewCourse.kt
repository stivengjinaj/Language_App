package com.stiven.languageapp.screens

import android.content.Context
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.stiven.languageapp.R
import com.stiven.languageapp.model.BottomBarScreens
import com.stiven.languageapp.model.Student
import com.stiven.languageapp.utils.Languages
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import kotlinx.coroutines.delay
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin

/**
 * Displays the page that prompts the user to start
 * a new course. In this page the user has to input
 * the student's name and choose the course to start.
 * If the student name already exists but not with the
 * same course the student will be registered if there
 * are less than 4 student registered. Otherwise the app
 * will notify the user.
 *
 * NOTE: Long pressing in any component of this page will
 *       trigger text-to-speech that will explain the user
 *       the propose of the component.
 *
 * @param studentViewModel view-model that handles student's data
 * @param textToSpeechViewModel view-model for text-to-speech accessibility
 * */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewCourse(
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    navController: NavHostController
) {
    var studentName by rememberSaveable { mutableStateOf("") }
    var studentNameError by rememberSaveable { mutableStateOf(false) }
    val noAvatarError = remember {
        mutableStateOf(false)
    }
    val chosenCourse by rememberSaveable { mutableStateOf((Languages.ITALIAN)) }
    var existingStudentDialog by rememberSaveable { mutableStateOf(false) }
    var maxStudentDialog by rememberSaveable { mutableStateOf(false) }
    val screenSize = LocalConfiguration.current.screenWidthDp
    val context = LocalContext.current
    val iconsList = listOf(
        R.raw.bella,
        R.raw.krishna,
        R.raw.karim,
        R.raw.chris,
        R.raw.helen,
        R.raw.ishanvi,
        R.raw.kim,
        R.raw.mattew,
        R.raw.priyanka,
        R.raw.usman,
        R.raw.kulthum,
        R.raw.ezra,
        R.raw.justin
    )
    val showDialog = remember { mutableStateOf(false) }
    // Default selected icon
    val selectedIcon = remember { mutableIntStateOf(-1) }
    Column (
        modifier = Modifier
            .alpha(if (showDialog.value) 0.3f else 1.0f)
            .fillMaxWidth()
    ){
        Spacer(modifier = Modifier.height((screenSize/6 + 20).dp))
        //Row containing the title of the page
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.pointerInput(Unit){
                    detectTapGestures(
                        onLongPress = {
                            textToSpeechViewModel.textToSpeech(context,context.getString(R.string.new_course_title))
                        }
                    )
                },
                text = stringResource(R.string.new_course_title),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.inversePrimary),
                fontSize = (screenSize/6 - 30).sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6 - 30).dp))
        //Row containing the names for the students.
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ){
            StudentButton(
                name = "Tiffany",
                screenSize = screenSize,
                borderColor = if(studentNameError)
                    MaterialTheme.colorScheme.error
                else if(studentName == "Tiffany") Color.Green else MaterialTheme.colorScheme.primary,
                context = context,
                textToSpeechViewModel = textToSpeechViewModel,
                onClick = {
                    studentName = "Tiffany"
                    studentNameError = false
                }
            )
            StudentButton(
                name = "Bella",
                screenSize = screenSize,
                borderColor = if(studentNameError)
                    MaterialTheme.colorScheme.error
                else if(studentName == "Bella") Color.Green else MaterialTheme.colorScheme.primary,
                context = context,
                textToSpeechViewModel = textToSpeechViewModel,
                onClick = {
                    studentName = "Bella"
                    studentNameError = false
                }
            )
            StudentButton(
                name = "Priyanka",
                screenSize = screenSize,
                borderColor = if(studentNameError)
                    MaterialTheme.colorScheme.error
                else if(studentName == "Priyanka") Color.Green else MaterialTheme.colorScheme.primary,
                context = context,
                textToSpeechViewModel = textToSpeechViewModel,
                onClick = {
                    studentName = "Priyanka"
                    studentNameError = false
                }
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6 - 60).dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StudentButton(
                name = "Chris",
                screenSize = screenSize,
                borderColor = if(studentNameError)
                    MaterialTheme.colorScheme.error
                else if(studentName == "Chris") Color.Green else MaterialTheme.colorScheme.primary,
                context = context,
                textToSpeechViewModel = textToSpeechViewModel,
                onClick = {
                    studentName = "Chris"
                    studentNameError = false
                }
            )
            StudentButton(
                name = "Mattew",
                screenSize = screenSize,
                borderColor = if(studentNameError)
                    MaterialTheme.colorScheme.error
                else if(studentName == "Mattew") Color.Green else MaterialTheme.colorScheme.primary,
                context = context,
                textToSpeechViewModel = textToSpeechViewModel,
                onClick = {
                    studentName = "Mattew"
                    studentNameError = false
                }
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6 + 50).dp))
        //Row containing the list of icons for student's account
        RotatingAvatars(
            avatars = iconsList.shuffled(),
            screenSize = screenSize,
            triggerError = noAvatarError
        ) {
            selectedIcon.intValue = it
        }
        Spacer(modifier = Modifier.height(((screenSize/3)).dp))
        //Row containing confirm button to create the account
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = {
                        if (studentName.isEmpty()) {
                            studentNameError = true
                        }
                        if (selectedIcon.intValue == -1) {
                            noAvatarError.value = true
                        } else {
                            //STUDENT DATA
                            val studentToInsert = Student(
                                name = studentName,
                                course = chosenCourse,
                                picture = selectedIcon.intValue,
                                points = 0
                            )
                            //CHECK IF STUDENT IS PRESENT AND IS ALREADY TAKING A COURSE IN THE SELECTED LANGUAGE
                            if (studentViewModel.dataList.value?.size == 4) {
                                maxStudentDialog = true
                            } else if (!studentViewModel.userCourseExists(studentToInsert)) {
                                Log.d("STUDENT", "STUDENT INSERTED")
                                studentViewModel.insertStudent(studentToInsert)
                                studentName = ""
                                navController.navigate(BottomBarScreens.Classroom.route)
                            } else {
                                existingStudentDialog = true
                            }
                        }
                    },
                    onLongClick = {
                        textToSpeechViewModel.textToSpeech(
                            context,
                            context.getString(R.string.next_button_speech)
                        )
                    }
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                modifier = Modifier.size((screenSize/6).dp),
                onClick = {
                    if (studentName.isEmpty()) {
                        studentNameError = true
                    }
                    if (selectedIcon.intValue < 0){
                        Log.d("NO AVATAR", "YES")
                        noAvatarError.value = true
                    }
                    else {
                        //STUDENT DATA
                        val studentToInsert = Student(
                            name = studentName,
                            course = chosenCourse,
                            picture = selectedIcon.intValue,
                            points = 0
                        )
                        //CHECK IF STUDENT IS PRESENT AND IS ALREADY TAKING A COURSE IN THE SELECTED LANGUAGE
                        if (studentViewModel.dataList.value?.size  == 4) {
                            maxStudentDialog = true
                        }else if (!studentViewModel.userCourseExists(studentToInsert)) {
                            Log.d("STUDENT","STUDENT INSERTED")
                            studentViewModel.insertStudent(studentToInsert)
                            navController.navigate(BottomBarScreens.Classroom.route)
                        }else{
                            existingStudentDialog = true
                        }
                    }
                }
            ) {
                Icon(
                    modifier = Modifier
                        .rotate(90f)
                        .size((screenSize / 6 - 10).dp)
                        .combinedClickable(
                            onClick = {},
                            onLongClick = {
                                textToSpeechViewModel.customTextToSpeech(
                                    context,
                                    context.getString(R.string.next),
                                    Locale.getDefault()
                                )
                            }
                        ),
                    imageVector = Icons.Rounded.ArrowUpward,
                    contentDescription = "Next",
                    tint = MaterialTheme.colorScheme.inversePrimary
                )
            }
        }
    }
    //Dialog box that appears if the student is already taking a course
    if (existingStudentDialog) {
        showDialog.value = true
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = {
                existingStudentDialog = false
            },
            title = {
                Text(text = context.getString(R.string.student_course_exists))
            },
            text = {
                Text(text = context.getString(R.string.confirmation_dialog))
            },
            confirmButton = {
                OutlinedButton(
                    modifier = Modifier.combinedClickable(
                        onClick = {
                            existingStudentDialog = false
                            val studentToInsert = Student(
                                name = studentName,
                                course = chosenCourse,
                                picture = selectedIcon.intValue,
                                points = 0
                            )
                            studentViewModel.deleteStudent(studentToInsert.name, studentToInsert.course)
                            studentViewModel.insertStudent(studentToInsert)
                            navController.navigate(BottomBarScreens.Classroom.route)
                        },
                        onLongClick = {
                            textToSpeechViewModel.textToSpeech(context,context.getString(R.string.confirm_button_speech))
                        }
                    ),
                    onClick = {
                        existingStudentDialog = false
                        val studentToInsert = Student(
                            name = studentName,
                            course = chosenCourse,
                            picture = selectedIcon.intValue,
                            points = 0
                        )
                        studentViewModel.deleteStudent(studentToInsert.name, studentToInsert.course)
                        studentViewModel.insertStudent(studentToInsert)
                        navController.navigate(BottomBarScreens.Classroom.route)
                    },
                    border = BorderStroke(2.dp,
                        MaterialTheme.colorScheme.error
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(text = context.getString(R.string.confirm), color = MaterialTheme.colorScheme.secondary)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        showDialog.value = false
                        existingStudentDialog = false
                    },
                    border = BorderStroke(2.dp,
                        MaterialTheme.colorScheme.primary
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )

                ) {
                    Text(text = context.getString(R.string.cancel), color = MaterialTheme.colorScheme.secondary)
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            )
        )
    }
    //Dialog box that appears if there are already 4 students registered
    if (maxStudentDialog){
        showDialog.value = true
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = {
                maxStudentDialog = false
            },
            title = {
                Text(text = context.getString(R.string.classroom_full))
            },
            text = {
                Text(text = context.getString(R.string.classroom_max_students))
            },
            confirmButton = {
                OutlinedButton(
                    modifier = Modifier.combinedClickable(
                        onClick = {
                            maxStudentDialog = false
                            showDialog.value = false
                        },
                        onLongClick = {
                            textToSpeechViewModel.textToSpeech(context,context.getString(R.string.confirm_button_speech))
                        }
                    ),
                    onClick = {
                        maxStudentDialog = false
                        showDialog.value = false
                    },
                    border = BorderStroke(2.dp,
                        MaterialTheme.colorScheme.primary
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = context.getString(R.string.confirm), color = MaterialTheme.colorScheme.secondary)
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            )
        )
    }
}
/**
 * Composable view that creates the rotation effect.
 *
 * @param avatars list of avatars.
 * @param screenSize screen size.
 * @param triggerError boolean that triggers an error effect.
 * @param onClick function callback that to invoke when an avatar is clicked.
 * */
@Composable
fun RotatingAvatars(
    avatars: List<Int>,
    screenSize: Int,
    triggerError: MutableState<Boolean>,
    onClick: (Int) -> Unit
){
    val someAvatars = remember {
        mutableStateOf(avatars.shuffled().subList(0, 8))
    }
    val centerAvatar = remember {
        mutableIntStateOf(-1)
    }
    val radius = (screenSize/5 + 50).dp
    val rotationAnimation = rememberInfiniteTransition(label = "")
    val rotationAngle by rotationAnimation.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 30000,
                easing = LinearEasing
            )
        ),
        label = ""
    )
    val errorRotation by rotationAnimation.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearOutSlowInEasing
            )
        ),
        label = ""
    )
    LaunchedEffect(triggerError.value){
        if(triggerError.value){
            delay(2000)
            triggerError.value = false
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer(
                    rotationZ = if(triggerError.value) errorRotation else rotationAngle
                ),
            contentAlignment = Alignment.Center
        ) {
            val angleStep = 360f / someAvatars.value.size
            someAvatars.value.forEachIndexed { index, avatar ->
                val angle = angleStep * index
                val x = cos(Math.toRadians(angle.toDouble())) * radius.value
                val y = sin(Math.toRadians(angle.toDouble())) * radius.value

                Image(
                    painter = painterResource(id = avatar),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(x.dp, y.dp)
                        .rotate(-rotationAngle)
                        .size((screenSize / 6).dp)
                        .clickable {
                            centerAvatar.intValue = avatar
                            onClick(avatar)
                        },
                    contentScale = ContentScale.Crop
                )
                if(centerAvatar.intValue >= 0){
                    Image(
                        painter = painterResource(centerAvatar.intValue),
                        contentDescription = "",
                        modifier = Modifier
                            .size((screenSize / 6 + 50).dp)
                            .rotate(if(triggerError.value) -errorRotation else -rotationAngle),
                        contentScale = ContentScale.Crop
                    )
                }else{
                    Box(
                        modifier = Modifier.size((screenSize/6 + 50).dp)
                    )
                }
            }
        }
    }
}

/**
 * Customized outlined button containing student names.
 *
 * @param name Student name.
 * @param screenSize Screen size.
 * @param onClick function callback when clicked.
 * */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StudentButton(
    name: String,
    screenSize: Int,
    borderColor: Color,
    context: Context,
    textToSpeechViewModel: TextToSpeechViewModel,
    onClick: () -> Unit
){
    Button(
        border = BorderStroke(2.dp, borderColor),
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = name,
            modifier = Modifier.combinedClickable(
                onClick = onClick,
                onLongClick = {
                    textToSpeechViewModel.customTextToSpeech(
                        context,
                        context.getString(R.string.choose_name),
                        Locale.getDefault()
                    )
                }
            ),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = (screenSize/6 - 50).sp
        )
    }
}