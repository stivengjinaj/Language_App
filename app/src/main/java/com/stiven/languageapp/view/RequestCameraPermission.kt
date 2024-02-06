package com.stiven.languageapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Composable view that shows when requesting camera permissions.
 *
 * @param onRequestPermission function that handles the permission request.
 * */
@Composable
fun RequestCameraPermission(
    onRequestPermission: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Please grant the permission to use the camera",
            color = MaterialTheme.colorScheme.primary
        )
        OutlinedButton(
            onClick = onRequestPermission
        ) {
            Icon(imageVector = Icons.Default.CameraAlt, contentDescription = "Camera")
            Text(text = "Grant")
        }
    }
}