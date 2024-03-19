package com.crystal2033.qrextractor.core.camera_for_photos

import android.util.Log
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    Log.i(LOG_TAG_NAMES.INFO_TAG, "CREATED CAM")
    AndroidView(
        factory = {
            val previewView = PreviewView(it)

            previewView.controller = controller
            previewView.implementationMode = PreviewView.ImplementationMode.PERFORMANCE


            try {
                controller.bindToLifecycle(lifecycleOwner)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            previewView
        },
        modifier = modifier
    )
}