package com.stiven.languageapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel

@Composable
fun Lessons(
    rootNavController: NavHostController,
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    studentId: String
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val id = studentId.toInt()
        val student = studentViewModel.dataList.value!!.find { it.id == id }
        val screenSize = LocalConfiguration.current.screenWidthDp
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
        Spacer(modifier = Modifier.height((screenSize/12-10).dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = student!!.picture),
                        contentDescription = "Student memoji",
                        modifier = Modifier.size((LocalConfiguration.current.screenWidthDp/4+20).dp)
                    )
                }
                Spacer(modifier = Modifier.height((screenSize/12-60).dp))
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        student!!.name,
                        fontSize = (screenSize/12-5).sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.inversePrimary
                    )
                }
            }
        }
    }
}