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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.crystal2033.qrextractor.scanner_feature.presentation.uiItems.PersonInfo
import com.crystal2033.qrextractor.scanner_feature.presentation.uiItems.ShowDataItemByType
import com.crystal2033.qrextractor.scanner_feature.presentation.util.UIEvent
import com.crystal2033.qrextractor.scanner_feature.presentation.viewmodel.QRCodeScannerViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QRCodeView(viewModel: QRCodeScannerViewModel,
               modifier: Modifier = Modifier) {

    val dataState = viewModel.previewDataFromQRState.value
    val snackbarHostState = remember { SnackbarHostState() }

    var code by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    var hasCameraPermission by remember {
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

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = "Okay",
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
        launcher.launch(Manifest.permission.CAMERA)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
//        modifier = Modifier
//            .background(Color.Red)
//            .fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Show snackbar") },
                icon = { Icon(Icons.Filled.Check, contentDescription = "") },
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Snackbar")
                    }
                }
            )
        }

    ) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                if (hasCameraPermission) {
                    AndroidView(
                        //modifier = Modifier.size(200.dp, 200.dp),
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
                                QRCodeAnalyzer { result ->
                                    code = result
                                    viewModel.onScanQRCode(code)
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
                    dataState.scannedDataInfo?.let { scannedData -> ShowDataItemByType(qrScannable = scannedData) }
                }

            }
            Button(modifier = Modifier.offset(20.dp, 20.dp), onClick = {

            }) {
                Text(text = "Hello world", color = Color.White)
            }
            if (dataState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }


}