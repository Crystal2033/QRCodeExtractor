package com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.LoadStatus
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.viewmodel.InventoryFileLoaderViewModel
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.vm_view_communication.FileLoaderEvent
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.vm_view_communication.UIFileLoaderEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FileChoosingView(
    viewModel: InventoryFileLoaderViewModel,
    onNavigate: (UIFileLoaderEvent.Navigate) -> Unit
) {

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIFileLoaderEvent.Navigate -> {
                    onNavigate(event)
                }
            }
        }
    }

    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) { result ->
            viewModel.onEvent(FileLoaderEvent.SetFilePath(result!!))
        }

    val loadStatusInfo = remember {
        viewModel.fileLoadStatusInfo
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                launcher.launch(arrayOf("*/*"))
            }) {
                Text(
                    text = "Choose excel file.",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "File status: ", fontWeight = FontWeight.Medium)
                Text(
                    text = loadStatusInfo.value.loadStatus.name.lowercase(),
                    color = setColorByStatusType(loadStatusInfo.value.loadStatus)
                )
            }
            Text(
                text = loadStatusInfo.value.message
            )

            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                viewModel.onEvent(FileLoaderEvent.StartInventoryCheck)
            }, enabled = loadStatusInfo.value.loadStatus == LoadStatus.SUCCESS) {
                Text(text = "Start inventory")
            }
        }
    }
}

@Composable
fun setColorByStatusType(loadStatus: LoadStatus): Color {
    return when (loadStatus) {
        LoadStatus.SUCCESS -> Color.Green
        LoadStatus.ERROR_OPENING_FILE -> Color.Red
        LoadStatus.ERROR_PARSING_FILE -> Color.Red
        LoadStatus.LOADING -> Color.Yellow
        LoadStatus.NO_FILE -> Color.Blue
        LoadStatus.UNKNOWN_ERROR -> Color.Red
    }

}