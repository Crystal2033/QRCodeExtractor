package com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.viewmodel.ScannedObjectsListViewModel
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.vm_view_communication.UIScannedObjectsListEvent
import kotlinx.coroutines.flow.collectLatest

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

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
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
        Box {
            LazyColumn(){
                listOfObjectsState.value.listOfObjects?.let { listOfObjects ->
                    items(listOfObjects.toList()){ scannableObject ->
                        ShowListItemByType(listItem = scannableObject)
                    }

                }


            }
        }
    }

}