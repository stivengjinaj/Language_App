package com.stiven.languageapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stiven.languageapp.entities.Student
import com.stiven.languageapp.utils.Languages
import com.stiven.languageapp.viewmodels.StudentViewModel


/**
 * Displays the page that prompts the user to start
 * a new course.
 *
 * @param studentViewModel The view-model for student's data
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCourse(studentViewModel: StudentViewModel) {
    var studentName by rememberSaveable { mutableStateOf("") }
    var studentNameError by rememberSaveable { mutableStateOf(false) }
    val errorMessage = stringResource(R.string.errorMessage)
    var englishOption by rememberSaveable { mutableStateOf(true) }
    var italianOption by rememberSaveable { mutableStateOf(false) }
    var frenchOption by rememberSaveable { mutableStateOf(false) }
    var chosenCourse by rememberSaveable { mutableStateOf((Languages.ENGLISH)) }
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .height((screenWidth / 6).dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "TrioLingo",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = (screenWidth / 12).sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(modifier = Modifier.height((screenWidth / 12 + 20).dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(R.string.new_course_title),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.inversePrimary),
                    fontSize = (screenWidth / 12).sp,
                    fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height((screenWidth / 12 + 10).dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OutlinedTextField(
                modifier = Modifier
                    .background(Color.Transparent)
                    .width((screenWidth - 110).dp),
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
                trailingIcon = {
                    if (studentNameError) {
                        Icon(Icons.Filled.Error, "Error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                shape = RoundedCornerShape(50),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.Characters
                )
            )
        }
        Spacer(modifier = Modifier.height((screenWidth / 12 + 10).dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                modifier = Modifier.width((screenWidth - 110).dp),
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
                Box(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.size((screenWidth / 12).dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.gb),
                            "GB FLAG",
                            modifier = Modifier
                                .size((screenWidth / 12).dp)
                                .clip(RoundedCornerShape(30))
                        )
                    }
                    Text(
                        stringResource(R.string.english),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = (screenWidth / 12 - 12).sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(((screenWidth - 110) / 3).dp, 0.dp, 0.dp, 0.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height((screenWidth / 12 + 10).dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                modifier = Modifier.width((screenWidth - 110).dp),
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
                Box(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.size((screenWidth / 12).dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.it),
                            "ITALIAN FLAG",
                            modifier = Modifier
                                .size((screenWidth / 12).dp)
                                .clip(RoundedCornerShape(30))
                        )
                    }
                    Text(
                        stringResource(R.string.italian),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = (screenWidth / 12 - 12).sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(((screenWidth - 110) / 3).dp, 0.dp, 0.dp, 0.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height((screenWidth / 12 + 10).dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                modifier = Modifier.width((screenWidth - 110).dp),
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
                Box(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.size((screenWidth / 12).dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.fr),
                            "FRENCH FLAG",
                            modifier = Modifier
                                .size((screenWidth / 12).dp)
                                .clip(RoundedCornerShape(30))
                        )
                    }
                    Text(
                        stringResource(R.string.french),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = (screenWidth / 12 - 12).sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(((screenWidth - 100) / 3).dp, 0.dp, 0.dp, 0.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height((screenWidth / 12 + 50).dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                modifier = Modifier.width((LocalConfiguration.current.screenWidthDp - 300).dp),
                onClick = {
                    if (studentName.isEmpty()) {
                        studentNameError = true
                    } else {
                        //STUDENT DATA
                        val studentToInsert = Student(
                            name = studentName,
                            course = chosenCourse,
                            picture = R.drawable.it,
                            points = 0
                        )
                        //CHECK IF STUDENT IS PRESENT AND IS ALREADY TAKING A COURSE IN THE SELECTED LANGUAGE
                        if (!studentViewModel.userCourseExists(studentToInsert)) {
                            studentViewModel.insertStudent(studentToInsert)
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
                        fontSize = (screenWidth / 12 - 16).sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}