package com.stiven.languageapp.view

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.GridLayout.LayoutParams
import android.widget.LinearLayout
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.stiven.languageapp.R
import java.util.concurrent.Executor


/**
 * Function that opens the camera and holds the captured image.
 *
 * @param onPhotoCaptured function that handles the captured photo.
 * */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Camera(onPhotoCaptured: (Bitmap) -> Unit){
    val capturedImage: MutableState<Bitmap?> = remember {
        mutableStateOf(null)
    }
    val cameraPermissionState: PermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    CameraView(
        cameraPermissionState.status.isGranted,
        onPhotoCaptured = {
            capturedImage.value = it
            onPhotoCaptured(it)
        },
        onRequestPermission = cameraPermissionState::launchPermissionRequest
    )
}

/**
 * Composable with a Scaffold that will contain the camera content
 * and a central button used to take a photo. The function checks if
 * there are granted permissions.
 *
 * @param hasPermission boolean to check if camera permission has been granted.
 * @param onPhotoCaptured function that handles the captured photo.
 * @param onRequestPermission function that handles permission requests if hasPermission is false.
 * */
@Composable
private fun CameraView(
    hasPermission: Boolean,
    onPhotoCaptured: (Bitmap) -> Unit,
    onRequestPermission: () -> Unit
) {
    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController = remember { LifecycleCameraController(context) }

    Scaffold(
        modifier = Modifier
            .height(300.dp)
            .width(300.dp)
            .clip(RoundedCornerShape(20.dp)),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = context.getString(R.string.check)) },
                onClick = { capturePhoto(context, cameraController, onPhotoCaptured) },
                icon = { Icon(imageVector = Icons.Rounded.CameraAlt, contentDescription = "Camera capture icon") }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues: PaddingValues ->
        if(!hasPermission){
            RequestCameraPermission(onRequestPermission)
        }else{
            Box(modifier = Modifier.fillMaxSize()) {
                AndroidView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    factory = { context ->
                        PreviewView(context).apply {
                            layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
                            setBackgroundColor(Color.Black.hashCode())
                            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                            scaleType = PreviewView.ScaleType.FILL_START
                        }.also { previewView ->
                            previewView.controller = cameraController
                            cameraController.bindToLifecycle(lifecycleOwner)
                        }
                    }
                )
            }
        }
    }
}

/**
 * Function that captures the actual view of the camera.
 *
 * @param context current context.
 * @param cameraController camera's controller.
 * @param onPhotoCaptured function that handles the captured photo.
 * */
private fun capturePhoto(
    context: Context,
    cameraController: LifecycleCameraController,
    onPhotoCaptured: (Bitmap) -> Unit
) {
    val mainExecutor: Executor = ContextCompat.getMainExecutor(context)

    cameraController.takePicture(mainExecutor, object : ImageCapture.OnImageCapturedCallback() {
        override fun onCaptureSuccess(image: ImageProxy) {
            val correctedBitmap: Bitmap = image.toBitmap()
            onPhotoCaptured(correctedBitmap)
            image.close()
        }

        override fun onError(exception: ImageCaptureException) {
            Log.e("CameraContent", "Error capturing image", exception)
        }
    })
}