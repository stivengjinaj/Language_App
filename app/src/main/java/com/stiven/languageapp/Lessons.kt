package com.stiven.languageapp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

@Composable
fun Lessons(
    navController: NavController,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    studentId: String
) {
    Column {
        val id = studentId.toInt()
        val student = studentViewModel.dataList.value!!.find { it.id == id }

        Text(text = student!!.name)
    }
}