package com.crystal2033.qrextractor.scanner_feature.scanner.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.scanner_feature.scanner.StaticConverters
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.dialog_window.DialogInsertName
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.dialog_window.DialogMessage
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.state.DialogWindowInfoState
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.uiItems.preview.ShowDataItemByType
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.util.QRCodeAnalyzer
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.viewmodel.QRCodeScannerViewModel
import com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication.QRScannerEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication.UIScannerEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QRCodeView(
    viewModel: QRCodeScannerViewModel,
    onNavigate: (UIScannerEvent.Navigate) -> Unit,
    snackbarHostState: SnackbarHostState
) {

    val scannedObject = remember {
        viewModel.previewDataFromQRState
    } // or just  val scannedObject = viewModel.previewDataFromQRState.value??

    val chosenListOfScannedObjects = remember {
        viewModel.listOfAddedScannables
    }

    val isNeedToShowMessageDialog = remember {
        mutableStateOf(false)
    }
    val dialogWindowInfo by remember { mutableStateOf(DialogWindowInfoState()) }


    val isNeedToShowGroupNameInsertDialog = remember {
        mutableStateOf(false)
    }

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

    ObserveAsEvents(flow = viewModel.eventFlow, launcher) { event ->
        when (event) {
            is UIScannerEvent.ShowSnackBar -> {
                snackbarHostState.showSnackbar(
                    message = event.message,
                    actionLabel = "Okay",
                    duration = SnackbarDuration.Long
                )
            }

            is UIScannerEvent.ShowMessagedDialogWindow -> {
                StaticConverters.fromEventDialogWindowIntoDialogInfoState(
                    event,
                    dialogWindowInfo
                )
                isNeedToShowMessageDialog.value = true
            }

            is UIScannerEvent.Navigate -> {
                onNavigate(event)
            }

            is UIScannerEvent.ShowScannedGroupNameDialogWindow -> {
                isNeedToShowGroupNameInsertDialog.value = true
            }

            else -> Unit
        }
    }


    Scaffold {
        Box {
            if (isNeedToShowMessageDialog.value) {
                DialogMessage(
                    dialogWindowInfoState = dialogWindowInfo,
                    isNeedToShowDialog = isNeedToShowMessageDialog
                )
            }

            if (isNeedToShowGroupNameInsertDialog.value) {
                DialogInsertName(
                    isNeedToShowDialog = isNeedToShowGroupNameInsertDialog,
                    onAcceptButtonClicked = { groupName ->
                        viewModel.onEvent(QRScannerEvent.OnAddNameForScannedGroup(groupName))
                    },
                    title = "Scanned objects group name",
                    helpMessage = "Please set scanned group name for added objects. ",
                    placeholderInTextField = "Group name",
                )
            }


            IconButton(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(0.dp, 5.dp),
                enabled = chosenListOfScannedObjects.isNotEmpty(),
                onClick = {
                    viewModel.onEvent(QRScannerEvent.OnAddScannedGroup)
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

            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset((-25).dp, 18.dp)
                    .size(20.dp),
                enabled = chosenListOfScannedObjects.isNotEmpty(),
                onClick = {
                    viewModel.onEvent(QRScannerEvent.ClearListOfScannedObjects)
                }
            ) {
                Icon(
                    ImageVector.vectorResource(R.drawable.broom_35),
                    contentDescription = "Broom"
                )
            }

            IconButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(25.dp, 18.dp)
                    .size(20.dp),
                onClick = {
                    viewModel.onEvent(QRScannerEvent.OnGoToScannedGroupsWindow)
                    cameraProviderFuture.get().unbindAll()
                }
            ) {
                Icon(
                    ImageVector.vectorResource(R.drawable.folders_100),
                    contentDescription = "Folder",
                )
            }


            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .offset(0.dp, 55.dp)
                    .fillMaxSize()
            ) {
                Spacer(Modifier.size(30.dp))
                if (hasCameraPermission) {
                    AndroidView(
                        modifier = Modifier
                            .scale(0.8f)
                            .weight(1f)
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
                    scannedObject.value.scannedDataInfo?.let { scannedData ->
                        ShowDataItemByType(
                            qrScannable = scannedData,
                            modifier = Modifier.weight(0.8f),
                            userAndPlaceBundle = viewModel.userAndPlaceBundle.value,
                            onAddObjectIntoListClicked = {
                                viewModel.onEvent(
                                    QRScannerEvent.OnAddObjectInList(
                                        scannedData,
                                        false
                                    )
                                )
                            },
                            onDeleteDevice = { deviceForRemoving ->
                                viewModel.onEvent(
                                    QRScannerEvent.OnDeleteDeviceFromServerClicked(
                                        deviceForRemoving
                                    )
                                )
                            }
                        )
                    }
                }
            }

            if (scannedObject.value.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

        }
    }


}

@Composable
private fun <T> ObserveAsEvents(
    flow: Flow<T>,
    cameraLauncher: ManagedActivityResultLauncher<String, Boolean>,
    onEvent: suspend (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(flow, lifecycleOwner.lifecycle) {
        cameraLauncher.launch(Manifest.permission.CAMERA)
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collectLatest(onEvent)
            }
        }
    }
}