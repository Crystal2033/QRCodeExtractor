package com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val listOfObjectsState by remember {
        mutableStateOf(viewModel.objectsListState)
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
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                listOfObjectsState.value.listOfObjects?.groupBy { it.javaClass.kotlin }
                    ?.forEach { (objectClass, correspondingObjects) ->
                        stickyHeader {
                            ShowHeaderClassName(
                                className = objectClass.simpleName ?: "Unknown class"
                            )
                        }
                        items(correspondingObjects) { scannableObject ->
                            ShowListItemByType(
                                listItem = scannableObject,
                                onObjectClicked = {
                                    viewModel.onEvent(
                                        ScannedObjectsListEvent.OnScannedObjectClicked(
                                            scannedObject = scannableObject
                                        )
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
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
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = className,
            fontSize = 27.sp
        )
    }

}

@Composable
@Preview
fun ShowHeader() {
    val className = "keyboard"
    ShowHeaderClassName(className)
}