package com.stiven.languageapp.screens.secondCloud

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stiven.languageapp.R
import com.stiven.languageapp.navigation.SecondCloudNavGraph
import com.stiven.languageapp.view.LogoBanner
import com.stiven.languageapp.viewmodels.StudentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeName(
    studentId: String,
    studentViewModel: StudentViewModel,
    navController: NavHostController
){
    val screenSize = LocalConfiguration.current.screenWidthDp
    val context = LocalContext.current
    var newName by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val onError = remember {
        mutableStateOf(false)
    }
    Column (
        modifier = Modifier.fillMaxWidth()
    ){
        LogoBanner(screenSize = screenSize)
        Spacer(modifier = Modifier.height((screenSize/6).dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = context.getString(R.string.insert_name),
                fontSize = (screenSize/6 - 45).sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Spacer(modifier = Modifier.height((screenSize/6 + 10).dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            TextField(
                modifier = Modifier
                    .width((screenSize - 10).dp)
                    .clip(RoundedCornerShape(30.dp))
                    .border(
                        BorderStroke(2.dp, if(!onError.value) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.error),
                        RoundedCornerShape(30.dp)
                    ),
                value = newName,
                onValueChange = {
                    onError.value = false
                    newName = it
                },
                placeholder = {
                    Text(
                        text = context.getString(R.string.name),
                        color = MaterialTheme.colorScheme.inversePrimary
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.inversePrimary,
                    errorPlaceholderColor = MaterialTheme.colorScheme.error
                ),
                isError = onError.value
            )
        }
        Spacer(modifier = Modifier.height((screenSize/2 + 50).dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                modifier = Modifier.size((screenSize/6 - 10).dp),
                onClick = {
                    if(newName.text.isNotEmpty()){
                        studentViewModel.updateName(studentId, newName.text)
                        navController.navigate(SecondCloudNavGraph.FINISHED_CLOUD)
                    }else {
                        onError.value = true
                    }
                }
            ) {
                Icon(
                    modifier = Modifier
                        .rotate(90f)
                        .size((screenSize / 6 - 10).dp),
                    imageVector = Icons.Rounded.ArrowUpward,
                    contentDescription = "Next"
                )
            }
        }
    }
}