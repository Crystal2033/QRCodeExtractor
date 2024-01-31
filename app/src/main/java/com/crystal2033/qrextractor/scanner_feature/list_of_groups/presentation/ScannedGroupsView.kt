package com.crystal2033.qrextractor.scanner_feature.list_of_groups.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.presentation.viewmodel.ScannedDataGroupsViewModel
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.vm_view_communication.ScannedGroupsListEvent
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.vm_view_communication.UIScannedGroupsListEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.StaticConverters
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.dialog_window.DialogMessage
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.state.DialogWindowInfoState
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScannedGroupsView(
    viewModel: ScannedDataGroupsViewModel,
    onUpdateScannedListViewModelState: () -> Unit,
    onNavigate: (UIScannedGroupsListEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val scannedGroups by remember {
        mutableStateOf(viewModel.scannedGroupsForUser)
    }

    val isNeedToShowMessageDialog = remember {
        mutableStateOf(false)
    }
    val dialogWindowInfo by remember { mutableStateOf(DialogWindowInfoState()) }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIScannedGroupsListEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = "Okay",
                        duration = SnackbarDuration.Long
                    )
                }

                is UIScannedGroupsListEvent.Navigate -> {
                    onNavigate(event)
                }

                is UIScannedGroupsListEvent.ShowMessagedDialogWindow -> {
                    StaticConverters.fromEventDialogWindowIntoDialogInfoStateForScannedGroup(
                        event,
                        dialogWindowInfo
                    )
                    isNeedToShowMessageDialog.value = true
                }
            }
        }
    }

    Scaffold {
        Box() {
            if (isNeedToShowMessageDialog.value) {
                DialogMessage(
                    dialogWindowInfoState = dialogWindowInfo,
                    isNeedToShowDialog = isNeedToShowMessageDialog
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(128.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                scannedGroups.value.userScannedGroups?.scannedGroups?.let { listOfGroups ->
                    items(listOfGroups) { group ->
                        ShowGroup(
                            scannedGroup = group,
                            onDeleteGroupIntention = {
                                viewModel.onEvent(ScannedGroupsListEvent.OnDeleteGroupClicked(
                                    it
                                ))
                            }
                        ) {
                            viewModel.onEvent(ScannedGroupsListEvent.OnGroupClickedEvent(group.id!!))
                            onUpdateScannedListViewModelState()
                        }
                    }
                }
            }
            if (scannedGroups.value.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

        }
    }


}