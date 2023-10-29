package com.crystal2033.qrextractor.scanner_feature.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.crystal2033.qrextractor.scanner_feature.StaticConverters
import com.crystal2033.qrextractor.scanner_feature.presentation.floating_views.DialogQuestion
import com.crystal2033.qrextractor.scanner_feature.presentation.state.DialogWindowInfoState
import com.crystal2033.qrextractor.scanner_feature.presentation.uiItems.preview.ShowDataItemByType
import com.crystal2033.qrextractor.scanner_feature.presentation.util.QRCodeAnalyzer
import com.crystal2033.qrextractor.scanner_feature.presentation.viewmodel.QRCodeScannerViewModel
import com.crystal2033.qrextractor.scanner_feature.vm_view_communication.QRScannerEvent
import com.crystal2033.qrextractor.scanner_feature.vm_view_communication.UIEvent
import kotlinx.coroutines.flow.collectLatest


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QRCodeView(
    viewModel: QRCodeScannerViewModel,
    onNavigate: (UIEvent.Navigate) -> Unit,
    snackbarHostState: SnackbarHostState
) {

    val dataState = viewModel.previewDataFromQRState.value
    val chosenListOfScannedObjects = viewModel.listOfAddedScannables

    val isNeedToShowDialog = remember {
        mutableStateOf(false)
    }
    val dialogWindowInfoState by remember { mutableStateOf(DialogWindowInfoState()) }


    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }


    var hasCameraPermission by rememberSaveable {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        })

    //val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA) //TODO: make RequestPermission
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = "Okay",
                        duration = SnackbarDuration.Long
                    )
                }

                is UIEvent.ShowDialogWindow -> {
                    StaticConverters.fromEventDialogWindowIntoDialogInfoState(
                        event,
                        dialogWindowInfoState
                    )
                    isNeedToShowDialog.value = true
                }

                is UIEvent.Navigate -> {
                    onNavigate(event)
                }

                else -> Unit
            }
        }

    }

    Scaffold {
        Box {
            if (isNeedToShowDialog.value) {
                DialogQuestion(
                    dialogWindowInfoState = dialogWindowInfoState,
                    isNeedToShowDialog = isNeedToShowDialog
                )
            }


            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .offset(0.dp, 55.dp)
                    .fillMaxSize()
            )

            {
                Spacer(Modifier.size(30.dp))
                if (hasCameraPermission) {
                    AndroidView(
                        modifier = Modifier
                            .size(400.dp)
                            .align(Alignment.CenterHorizontally),
                        factory = { context ->
                            val previewView = PreviewView(context)
                            val preview = Preview.Builder().build()
                            val selector = CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                .build()
                            preview.setSurfaceProvider(previewView.surfaceProvider)

                            val resolutionSelector = ResolutionSelector.Builder()
                                .setResolutionStrategy(ResolutionStrategy.HIGHEST_AVAILABLE_STRATEGY)
                                .build()

                            val imageAnalysis = ImageAnalysis.Builder()
                                .setResolutionSelector(resolutionSelector)
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build()

                            imageAnalysis.setAnalyzer(
                                ContextCompat.getMainExecutor(context),
                                QRCodeAnalyzer { scannedString ->
                                    viewModel.onEvent(QRScannerEvent.OnScanQRCode(scannedString))
                                }

                            )
                            try {
                                cameraProviderFuture.get().bindToLifecycle(
                                    lifecycleOwner,
                                    selector,
                                    preview,
                                    imageAnalysis
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                            previewView
                        },
                    )
                    Spacer(modifier = Modifier.size(60.dp))

                    dataState.scannedDataInfo?.let { scannedData ->
                        ShowDataItemByType(
                            qrScannable = scannedData,
                            onAddObjectIntoListClicked = {
                                viewModel.onEvent(
                                    QRScannerEvent.OnAddObjectInList(
                                        scannedData,
                                        false
                                    )
                                )
                            }
                        )
                    }
                }
            }

            if (dataState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }


            IconButton(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(0.dp, 5.dp),
                onClick = {
                    viewModel.onEvent(QRScannerEvent.OnGoToScannedList)
                    cameraProviderFuture.get().unbindAll()
                }
            ) {
                BadgedBox(
                    badge = {
                        if (chosenListOfScannedObjects.isNotEmpty()) {
                            Badge {
                                Text(
                                    text = chosenListOfScannedObjects.size.toString()
                                )
                            }
                        }
                    })
                {
                    Icon(
                        Icons.Filled.List,
                        contentDescription = "List"
                    )
                }

            }


        }
    }


}

