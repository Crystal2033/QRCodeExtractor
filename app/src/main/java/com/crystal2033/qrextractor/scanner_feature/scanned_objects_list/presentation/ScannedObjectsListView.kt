package com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.state.InventarizedObjectInfoAndIDInLocalDB
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.state.ObjectsListState
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.viewmodel.ScannedObjectsListViewModel
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.vm_view_communication.ScannedObjectsListEvent
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.vm_view_communication.UIScannedObjectsListEvent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScannedObjectsListView(
    viewModel: ScannedObjectsListViewModel,
    onNavigate: (UIScannedObjectsListEvent.Navigate) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val listOfObjectsState = remember {
        viewModel.objectsListState
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIScannedObjectsListEvent.Navigate -> {
                    onNavigate(event)
                }

                is UIScannedObjectsListEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = "Okay",
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

    Scaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Text(
                    text = viewModel.getChosenGroupName(),
                    fontSize = 30.sp,
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Divider(color = Color.White, thickness = 2.dp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = listOfObjectsState.value.listOfObjectsWithCabinetName.size.toString())
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    listOfObjectsState.value.listOfObjectsWithCabinetName.groupBy { it.objectInfo.javaClass.kotlin }
                        .forEach { (objectClass, correspondingObjects) ->
                            stickyHeader {
                                ShowHeaderClassName(
                                    className = objectClass.simpleName ?: "Unknown class"
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                            items(correspondingObjects) { scannableObjectWithItsCabinetName ->
                                ShowListItemByType(
                                    objectInfoWithLocalDB = scannableObjectWithItsCabinetName,
                                    onObjectClicked = {
                                        viewModel.onEvent(
                                            ScannedObjectsListEvent.OnScannedObjectClicked(
                                                scannedObject = scannableObjectWithItsCabinetName.objectInfo
                                            )
                                        )
                                    },
                                    onDeleteScannedObjectClicked = { deletingObjectId ->
                                        viewModel.onEvent(
                                            ScannedObjectsListEvent.DeleteObjectFromScannedGroup(
                                                deletingObjectId
                                            )
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                }
            }

            if (listOfObjectsState.value.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}

@Composable
fun ShowHeaderClassName(
    className: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = className,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
@Preview
fun ShowHeader() {
    val className = "keyboard"
    ShowHeaderClassName(className)
}