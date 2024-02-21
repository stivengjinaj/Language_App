package com.stiven.languageapp.view.initialTour

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stiven.languageapp.R
import com.stiven.languageapp.model.Student
import com.stiven.languageapp.utils.Languages

@Composable
fun ClassroomGuide(
    screenSize: Int,
    context: Context,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val student = Student(name = "Tiffany", course = Languages.ENGLISH, picture = R.raw.helen, points = 50)
    Spacer(modifier = Modifier.height((screenSize/6 - 20).dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = context.getString(R.string.classroom_guide),
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = (screenSize/6 - 50).sp,
                fontWeight = FontWeight.Bold,
            ),
            textAlign = TextAlign.Center
        )
    }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30))
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(id = student.picture),
            contentDescription = "Avatar"
        )
        Text(
            text = AnnotatedString(student.name),
            style = TextStyle(
                color = MaterialTheme.colorScheme.inversePrimary,
                fontSize = (screenSize/6 - 45).sp,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = student.points.toString() + " " + if (student.points == 1) context.getString(R.string.point) else context.getString(R.string.points),
            style = TextStyle(
                color = MaterialTheme.colorScheme.inversePrimary,
                fontSize = (screenSize/6 - 50).sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.width((screenSize/6 - 20).dp))
        Image(
            painter = painterResource(id = R.drawable.delete),
            contentDescription = "Student memoji",
            modifier = Modifier.size((screenSize/6 - 40).dp)
        )
    }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        IconButton(
            modifier = Modifier.width((screenSize/3).dp),
            onClick = {
                onBackClick()
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        IconButton(
            modifier = Modifier.width((screenSize/3).dp),
            onClick = {
                onNextClick()
            }
        ) {
            Icon(
                modifier = Modifier.rotate(180f),
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = "Next",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}