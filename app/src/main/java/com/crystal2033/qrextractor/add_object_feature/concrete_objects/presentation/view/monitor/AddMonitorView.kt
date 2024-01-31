package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.monitor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.BaseViewForInventarizedDevice
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.monitor.AddMonitorViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication.UIAddNewObjectEvent
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle

@Composable
fun AddMonitorView(
    viewModel: AddMonitorViewModel,
    isCameraOn: MutableState<Boolean>,
    userAndPlaceBundle: UserAndPlaceBundle,
    isAllFieldsInsertedState: MutableState<Boolean>,
    onNavigate: (UIAddNewObjectEvent.Navigate) -> Unit,
    snackbarHostState: SnackbarHostState,
    onChangePlaceClicked: () -> Unit,
    isForUpdate: Boolean = false
) {

    val monitorState by remember {
        viewModel.monitorStateWithLoadingStatus
    }

    val isNeedToShowCamera = remember {
        isCameraOn
    }

    isAllFieldsInsertedState.value = viewModel.isAllNeededFieldsInsertedCorrectly()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BaseViewForInventarizedDevice(
                viewModel = viewModel,
                userAndPlaceBundle = userAndPlaceBundle,
                deviceState = monitorState,
                isNeedToShowCamera = isNeedToShowCamera,
                onNavigate = onNavigate,
                snackbarHostState = snackbarHostState,
                isForUpdate = isForUpdate,
                onChangePlaceClicked = onChangePlaceClicked
            )
//            if (!isNeedToShowCamera.value) {
//                //Here is specific for device fields
//            }

        }

        if (monitorState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }
}

