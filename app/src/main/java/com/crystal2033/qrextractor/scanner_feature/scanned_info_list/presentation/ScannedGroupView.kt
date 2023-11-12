package com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation.viewmodel.ScannedDataGroupsViewModel
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.vm_view_communication.UIScannedDataListEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication.UIScannerEvent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScannedGroupsView(
    viewModel: ScannedDataGroupsViewModel,
    onNavigate: (UIScannerEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val scannedGroups by remember {
        mutableStateOf(viewModel.scannedGroupsForUser)
    }
//    val groupedByClassesScannedObjects =
//        viewModel.listOfAddedScannables.groupBy { it.javaClass.kotlin }
    LaunchedEffect(key1 = true) {
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
            LazyVerticalGrid(
                columns = GridCells.Adaptive(128.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                scannedGroups.value.userScannedGroups?.scannedGroups?.let { listOfGroups ->
                    items(listOfGroups) { group ->
                        ShowGroupIcon(
                            scannedGroup = group
                        ) {
                            Log.i(LOG_TAG_NAMES.INFO_TAG, "Group id: ${group.id} and name: ${group.groupName}")
                        }
                    }
                }
            }
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