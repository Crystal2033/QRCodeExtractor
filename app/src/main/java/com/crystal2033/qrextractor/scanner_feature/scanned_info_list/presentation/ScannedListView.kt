package com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.crystal2033.qrextractor.core.User
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation.viewmodel.ScannedDataGroupsViewModel
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.vm_view_communication.UIScannedDataListEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.viewmodel.QRCodeScannerViewModel
import com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication.UIScannerEvent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScannedListView(
    viewModel: ScannedDataGroupsViewModel,
    onNavigate: (UIScannerEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
//    val groupedByClassesScannedObjects =
//        viewModel.listOfAddedScannables.groupBy { it.javaClass.kotlin }
    LaunchedEffect(key1 = true) {
        viewModel.refresh()
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIScannedDataListEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = "Okay",
                        duration = SnackbarDuration.Long
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold {
        Box() {
//            LazyColumn(modifier = Modifier.align(Alignment.TopCenter)) {
//                groupedByClassesScannedObjects.forEach { (initial, contactsForInitial) ->
//                    stickyHeader {
//                        Text(text = "${initial.simpleName}")
//                    }
//
//                    items(contactsForInitial) { scannedObject ->
//                        ShowListItemByType(scannedObject)
//                    }
//                }
//            }
//
//            Button( modifier = Modifier.align(Alignment.TopEnd),
//                onClick = { onPopBackStack() }) {
//                Text(text = "Go back dude")
//            }

        }
    }


}