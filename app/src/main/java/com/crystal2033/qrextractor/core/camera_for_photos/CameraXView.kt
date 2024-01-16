@file:OptIn(ExperimentalMaterialApi::class)

package com.crystal2033.qrextractor.core.camera_for_photos

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.crystal2033.qrextractor.R


@Composable
fun CameraXView(
    darkTheme: Boolean = isSystemInDarkTheme(),
    onImageChanged: (Bitmap?) -> Unit,
    onCloseButtonClicked: () -> Unit
) {

    val scaffoldState = rememberBottomSheetScaffoldState()
    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or CameraController.VIDEO_CAPTURE
            )
        }
    }

    val needToAcceptOrDeclinePhoto = remember {
        mutableStateOf(false)
    }

    val photoForAcception = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val image = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                image.value = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
                } else {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                }
                onImageChanged(image.value)
                onCloseButtonClicked()
            }

        }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        modifier = Modifier.size(450.dp),
        backgroundColor =
        if (darkTheme) Color(context.resources.getColor(R.color.dark_gray, context.theme))
        else Color(context.resources.getColor(R.color.white, context.theme)),
        sheetContent = {

        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
        ) {
            if (!needToAcceptOrDeclinePhoto.value) {
                CameraPreview(
                    controller = controller,
                    modifier = Modifier
                        .size(400.dp)
                        .align(Alignment.Center)
                )
                IconButton(
                    onClick =
                    {
                        onCloseButtonClicked()
                    },
                    modifier = Modifier
                        .offset(16.dp, 16.dp)
                        .align(Alignment.TopEnd)
                ) {

                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Close camera"
                    )
                }

                IconButton(
                    onClick =
                    {
                        controller.cameraSelector =
                            if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                                CameraSelector.DEFAULT_FRONT_CAMERA
                            } else CameraSelector.DEFAULT_BACK_CAMERA
                    },
                    modifier = Modifier
                        .offset(16.dp, 16.dp)
                        .align(Alignment.TopStart)
                ) {

                    Icon(
                        imageVector = Icons.Default.Cameraswitch,
                        contentDescription = "Switch"
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(
                        onClick = {
                            launcher.launch("image/*")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Photo,
                            contentDescription = "Open gallery"
                        )

                    }

                    IconButton(
                        onClick = {
                            takePhoto(
                                controller = controller,
                                onPhotoTaken = {
                                    photoForAcception.value = it
                                    needToAcceptOrDeclinePhoto.value = true
                                },
                                context = context
                            )

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = "Take photo"
                        )

                    }
                }
            } else {
                AcceptOrDeclinePhotoView(
                    photo = photoForAcception.value!!,
                    onAcceptPhoto = {
                        image.value = it
                        onImageChanged(image.value)
                        needToAcceptOrDeclinePhoto.value = false
                        onCloseButtonClicked()
                    },
                    onDeclinePhoto = {
                        needToAcceptOrDeclinePhoto.value = false
                    })
            }
        }

    }
}

private fun takePhoto(
    controller: LifecycleCameraController,
    onPhotoTaken: (Bitmap) -> Unit,
    context: Context
) {

    controller.takePicture(
        ContextCompat.getMainExecutor(context),
        object : OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
                val matrix = Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                }
                val rotatedBitmap = Bitmap.createBitmap(
                    image.toBitmap(),
                    0,
                    0,
                    image.width,
                    image.height,
                    matrix,
                    true
                )
                onPhotoTaken(rotatedBitmap)
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("Camera", "Could not take photo", exception)
            }
        }
    )
}