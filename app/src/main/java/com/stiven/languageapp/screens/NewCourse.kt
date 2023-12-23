package com.stiven.languageapp.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
import java.util.Locale

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
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NewCourse(studentViewModel: StudentViewModel, textToSpeechViewModel: TextToSpeechViewModel, navController: NavHostController) {
    var studentName by rememberSaveable { mutableStateOf("") }
    var studentNameError by rememberSaveable { mutableStateOf(false) }
    val errorMessage = stringResource(R.string.errorMessage)
    var englishOption by rememberSaveable { mutableStateOf(true) }
    var italianOption by rememberSaveable { mutableStateOf(false) }
    var frenchOption by rememberSaveable { mutableStateOf(false) }
    var chosenCourse by rememberSaveable { mutableStateOf((Languages.ENGLISH)) }
    var existingStudentDialog by rememberSaveable { mutableStateOf(false) }
    var maxStudentDialog by rememberSaveable { mutableStateOf(false) }
    val screenHeight = LocalConfiguration.current.screenHeightDp / 20
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
    val selectedIcon = remember { mutableIntStateOf(iconsList[1]) }

    Column (
        modifier = Modifier
            .alpha(if (showDialog.value) 0.3f else 1.0f)
            .fillMaxWidth()
    ){
        Spacer(modifier = Modifier.height((screenHeight+50).dp))
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
                fontSize = (screenHeight-10).sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height((screenHeight).dp))
        //Row containing the text field for for the student's name
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .background(Color.Transparent)
                    .width((screenHeight+300).dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                textToSpeechViewModel.textToSpeech(
                                    context,
                                    context.getString(R.string.student_name_speech)
                                )
                            }
                        )
                    },
                value = studentName,
                onValueChange = {
                    studentName = it
                    if (studentName.isNotEmpty()) {
                        studentNameError = false
                    }
                },
                label = {
                    Text(
                        text = stringResource(R.string.student_name),
                        style = TextStyle(
                            color = if (studentNameError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.inversePrimary
                        ),
                        fontWeight = FontWeight.Bold
                    )
                },
                isError = studentNameError,
                supportingText = {
                    if (studentNameError) {
                        Text(text = errorMessage, style = TextStyle(color = MaterialTheme.colorScheme.error))
                    }
                },
                shape = RoundedCornerShape(50),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
        }
        Spacer(modifier = Modifier.height((screenHeight).dp))
        //Row containing the english button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                modifier = Modifier.width((screenHeight + 280).dp),
                onClick = {
                    frenchOption = false
                    englishOption = true
                    italianOption = false
                    chosenCourse = Languages.ENGLISH
                },
                shape = RoundedCornerShape(50),
                border = BorderStroke(
                    3.dp,
                    if (englishOption) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = {
                            frenchOption = false
                            englishOption = true
                            italianOption = false
                            chosenCourse = Languages.ENGLISH
                        },
                        onLongClick = {
                            textToSpeechViewModel.textToSpeech(
                                context, context.getString(
                                    R.string.english_button_speech
                                )
                            )
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size((screenHeight - 10).dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.gb),
                            "GB FLAG",
                            modifier = Modifier
                                .size((screenHeight - 10).dp)
                                .clip(RoundedCornerShape(20))
                        )
                    }
                    Text(
                        stringResource(R.string.english),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = (screenHeight - 17).sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding((screenHeight + 35).dp, 0.dp, 0.dp, 0.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height((screenHeight).dp))
        //Row containing the italian button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                modifier = Modifier.width((screenHeight + 280).dp),
                onClick = {
                    frenchOption = false
                    englishOption = false
                    italianOption = true
                    chosenCourse = Languages.ITALIAN
                },
                shape = RoundedCornerShape(50),
                border = BorderStroke(
                    3.dp,
                    if (italianOption) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = {
                            frenchOption = false
                            englishOption = false
                            italianOption = true
                            chosenCourse = Languages.ITALIAN
                        },
                        onLongClick = {
                            textToSpeechViewModel.textToSpeech(
                                context, context.getString(
                                    R.string.italian_button_speech
                                )
                            )
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size((screenHeight - 10).dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.it),
                            "ITALIAN FLAG",
                            modifier = Modifier
                                .size((screenHeight - 10).dp)
                                .clip(RoundedCornerShape(20))
                        )
                    }
                    Text(
                        stringResource(R.string.italian),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = (screenHeight - 17).sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding((screenHeight + 35).dp, 0.dp, 0.dp, 0.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height((screenHeight).dp))
        //Row containing the french button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                modifier = Modifier.width((screenHeight + 280).dp),
                onClick = {
                    frenchOption = true
                    englishOption = false
                    italianOption = false
                    chosenCourse = Languages.FRENCH
                },
                shape = RoundedCornerShape(50),
                border = BorderStroke(
                    3.dp,
                    if (frenchOption) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = {
                            frenchOption = true
                            englishOption = false
                            italianOption = false
                            chosenCourse = Languages.FRENCH
                        },
                        onLongClick = {
                            textToSpeechViewModel.textToSpeech(
                                context, context.getString(
                                    R.string.french_button_speech
                                )
                            )
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size((screenHeight - 10).dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.fr),
                            "FRENCH FLAG",
                            modifier = Modifier
                                .size((screenHeight - 10).dp)
                                .clip(RoundedCornerShape(20))
                        )
                    }
                    Text(
                        stringResource(R.string.french),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = (screenHeight - 17).sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding((screenHeight + 35).dp, 0.dp, 0.dp, 0.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(screenHeight.dp))
        //Row containing the list of icons for student's account
        Row {
            IconSelector(icons = iconsList) { icon ->
                selectedIcon.intValue = icon
            }
        }
        Spacer(modifier = Modifier.height((screenHeight-10).dp))
        //Row containing confirm button to create the account
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = {
                        if (studentName.isEmpty()) {
                            studentNameError = true
                        } else {
                            //STUDENT DATA
                            val studentToInsert = Student(
                                name = formatString(studentName),
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
            OutlinedButton(
                onClick = {
                    if (studentName.isEmpty()) {
                        studentNameError = true
                    } else {
                        //STUDENT DATA
                        val studentToInsert = Student(
                            name = formatString(studentName),
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
                },
                shape = RoundedCornerShape(50),
                border = BorderStroke(2.dp,
                    MaterialTheme.colorScheme.primary
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = stringResource(R.string.next),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = (screenHeight - 25).sp,
                        fontWeight = FontWeight.Bold
                    )
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
                                name = formatString(studentName),
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
                            name = formatString(studentName),
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
                        },
                        onLongClick = {
                            textToSpeechViewModel.textToSpeech(context,context.getString(R.string.confirm_button_speech))
                        }
                    ),
                    onClick = {
                        maxStudentDialog = false
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
 * Composable function that contains a LazyRow with icons to choose
 *
 * @param icons a list of icons to choose
 * @param onIconSelected callback function when the item is selected
 * */
@Composable
fun IconSelector(
    icons: List<Int>,
    onIconSelected: (Int) -> Unit
) {
    val currentSize = LocalConfiguration.current.screenWidthDp
    var selectedIcon by remember { mutableIntStateOf(icons[1]) }
    Row (
        modifier = Modifier
            .padding((currentSize/12).dp,0.dp)
    ){
        LazyRow(
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp)
        ) {
            items(icons) { icon ->
                val isSelected = icon == selectedIcon
                IconItem(
                    icon = icon,
                    isSelected = isSelected,
                    onIconSelected = {
                        selectedIcon = it
                        onIconSelected(it)
                    }
                )
            }
        }
    }
}

/**
 * Composable function that creates an icon profile picture for user
 *
 * @param icon icon path
 * @param isSelected whether the item is selected
 * @param onIconSelected callback function when item is selected
 * */
@Composable
fun IconItem(
    icon: Int,
    isSelected: Boolean,
    onIconSelected: (Int) -> Unit
) {
    val alpha = if (isSelected) 1f else 0.5f

    Image(
        painter = painterResource(id = icon),
        contentDescription = null,
        modifier = Modifier
            .padding(8.dp, 0.dp, 8.dp, 0.dp)
            .size((LocalConfiguration.current.screenWidthDp / 6).dp)
            .alpha(alpha)
            .clickable { onIconSelected(icon) }
    )
}

/**
 * Function that formats a string. It removes blank spaces
 * and it capitalizes the string
 *
 * @param string Unformatted string
 * @return a trimmed string without blank spaces
 * */
fun formatString(string: String): String {
    // Trim leading and trailing spaces and convert to lowercase
    val trimmedString = string.trim().lowercase(Locale.ROOT)

    return trimmedString.substring(0, 1).uppercase(Locale.ROOT) + trimmedString.substring(1)
}