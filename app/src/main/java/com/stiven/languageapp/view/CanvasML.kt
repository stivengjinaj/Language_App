package com.stiven.languageapp.view

import android.graphics.Bitmap
import android.graphics.Picture
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions
import kotlinx.coroutines.delay

/**
 * Canvas where user can draw a letter and when the green check button
 * is clicked, a tflite model recognizes the letter.
 * */
@Composable
fun DrawingCanvas(letterToDraw: Char){
    val letterRecognizer = LocalModel.Builder()
        .setAssetFilePath("lettersModel.tflite")
        .build()

    val customImageLabelerOptions = CustomImageLabelerOptions.Builder(letterRecognizer)
        .setConfidenceThreshold(0.5f)
        .setMaxResultCount(3)
        .build()
    val labeler = ImageLabeling.getClient(customImageLabelerOptions)

    val lines = remember {
        mutableStateListOf<Line>()
    }
    val bitmapDrawing: MutableState<Bitmap?> = remember {
        mutableStateOf(null)
    }
    val letterRecognized = remember {
        mutableStateOf(false)
    }
    val picture = remember { Picture() }
    if(!letterRecognized.value){
        Row(
            modifier = Modifier
                .width(300.dp)
                .height(300.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
        ){
            Canvas(
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp)
                    .background(Color.White)
                    .clipToBounds()
                    .drawWithCache {
                        val width = 1000
                        val height = 1000
                        onDrawWithContent {
                            val pictureCanvas = androidx.compose.ui.graphics.Canvas(
                                picture.beginRecording(
                                    width,
                                    height
                                )
                            )
                            draw(this, this.layoutDirection, pictureCanvas, this.size) {
                                this@onDrawWithContent.drawContent()
                            }
                            picture.endRecording()

                            drawIntoCanvas { canvas -> canvas.nativeCanvas.drawPicture(picture) }
                        }
                    }
                    .pointerInput(true) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            val line = Line(
                                start = change.position - dragAmount,
                                end = change.position
                            )
                            lines.add(line)
                        }
                    }
            ) {
                lines.forEach { line ->
                    drawLine(
                        color = line.color,
                        start = line.start,
                        end = line.end,
                        strokeWidth = line.strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
        }
        //CANVAS CONTROL BUTTONS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //ERASE BUTTON
            IconButton(onClick = {
                lines.clear()
            }) {
                Icon(imageVector = Icons.Rounded.Cancel, contentDescription = "Erase all", tint = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.width(20.dp))
            //VALIDATION BUTTON
            IconButton(onClick = {
                bitmapDrawing.value = createBitmapFromPicture(picture)
                if(bitmapDrawing.value != null){
                    val imageToProcess = InputImage.fromBitmap(bitmapDrawing.value!!, 0)
                    labeler.process(imageToProcess)
                        .addOnSuccessListener { labels ->
                            for (label in labels) {
                                if (label.confidence >= 0.8f) {
                                    Log.d("LABEL", labelMap(label.index).toString())
                                    if(labelMap(label.index) == letterToDraw){
                                        letterRecognized.value = true
                                        break
                                    }
                                }
                            }
                        }
                        .addOnFailureListener { e ->
                            Log.d("ML ERROR", e.message.toString())
                        }
                }
            }) {
                Icon(imageVector = Icons.Rounded.Check, contentDescription = "Validate", tint = Color.Green)
            }
        }
    }else{
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Rounded.Check, contentDescription = "CORRECT", tint = Color.Green, modifier = Modifier.width(500.dp).height(500.dp).clip(
                RoundedCornerShape(50)
            ))
        }
        LaunchedEffect(letterRecognized.value) {
            if (letterRecognized.value) {
                // Wait for 1 second
                delay(1000)
                // Reset letterRecognized value to false after the delay
                letterRecognized.value = false
            }
        }
    }
}

/**
 * Function that maps tflite number indexes to their letters.
 *
 * @param labelIndex Index of label.
 * @return The letter in the index.
 * */
private fun labelMap(labelIndex: Int): Char{
    require(labelIndex in 0..20) { "Number should be between 0 and 20 inclusive" }

    val alphabet = "ABCDEFGHILMNOPQRSTUVXYZ"

    return alphabet[labelIndex]
}

/**
 * Function that converts a picture (screenshot) into a bitmap.
 * Bitmap is fed to the tflite model for faster recognition.
 *
 * @param picture The screenshot to be converted
 * @return Bitmap image.
 * */
private fun createBitmapFromPicture(picture: Picture): Bitmap {
    val bitmap = Bitmap.createBitmap(
        picture.width,
        picture.height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(bitmap)
    canvas.drawColor(android.graphics.Color.WHITE)
    canvas.drawPicture(picture)
    return bitmap
}

/**
 * Data class that represents the lines drawn in canvas.
 * */
data class Line(
    val start: Offset,
    val end: Offset,
    val color: Color = Color.Black,
    val strokeWidth: Dp = 1.dp
)