package com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation.viewmodel.InventoryCheckViewModel
import com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation.vm_view_communication.InventoryCheckEvent
import com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation.vm_view_communication.UIInventoryCheckEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.ScannerCameraView
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InventoryCheckView(
    viewModel: InventoryCheckViewModel, onNavigate: (UIInventoryCheckEvent.Navigate) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }


    var hasCameraPermission by rememberSaveable {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted ->
                hasCameraPermission = granted
            })

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIInventoryCheckEvent.Navigate -> {
                    onNavigate(event)
                }
            }
        }
    }

    val listOfCheckingObjects = remember {
        viewModel.listOfObjectToCheck
    }

    Scaffold {
        //Box {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                if (hasCameraPermission) {
                    ScannerCameraView(
                        modifier = Modifier
                            .scale(0.6f)
                            .align(Alignment.Top),
                        onScanAction = { scannedString ->
                            viewModel.onEvent(InventoryCheckEvent.OnScanQRCode(scannedString))
                        },
                        cameraProviderFuture = cameraProviderFuture,
                        lifecycleOwner = lifecycleOwner
                    )
                }
            }
            Spacer(modifier = Modifier.size(40.dp))
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {
                items(listOfCheckingObjects) { checkingObject ->
                    //Spacer(modifier = Modifier.height(10.dp))
                    Divider(color = Color.DarkGray, thickness = 1.dp)
                    CheckingObjectView(checkingObject)
//                                MenuItem(item = typeName) {
//                                    viewModel.onEvent(
//                                        CreateQRCodeEvent.SetChosenObjectClass(
//                                            typeName
//                                        )
//                                    )
//                                }
                    Divider(color = Color.DarkGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(10.dp))

                }
            }
        }
        //}
    }


}