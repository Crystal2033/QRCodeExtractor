package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.chair

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.BaseViewForInventarizedDevice
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.chair.AddChairViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication.UIAddNewObjectEvent
import com.crystal2033.qrextractor.ui.text_elements.TextFieldView

@Composable
fun AddChairView(
    viewModel: AddChairViewModel,
    isAllFieldsInsertedState: MutableState<Boolean>,
    onNavigate: (UIAddNewObjectEvent.Navigate) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {

    val chairState by remember {
        viewModel.chairStateWithLoadingStatus
    }

    val isNeedToShowCamera = remember {
        mutableStateOf(false)
    }

    isAllFieldsInsertedState.value = viewModel.isAllFieldInsertedCorrectly()


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
                deviceState = chairState,
                isNeedToShowCamera = isNeedToShowCamera,
                onNavigate = onNavigate,
                snackbarHostState = snackbarHostState
            )
            if (!isNeedToShowCamera.value) {
                TextFieldView(
                    fieldHint = "Inventory number",
                    currentText = "lol",
                    onValueChanged = { },
                    horizontalArrangement = Arrangement.Center
                )
                Spacer(modifier = Modifier.height(15.dp))
                TextFieldView(
                    fieldHint = "Inventory number",
                    currentText = "lol",
                    onValueChanged = {},
                    horizontalArrangement = Arrangement.Center
                )
                Spacer(modifier = Modifier.height(15.dp))
                TextFieldView(
                    fieldHint = "Inventory number",
                    currentText = "lol",
                    onValueChanged = {},
                    horizontalArrangement = Arrangement.Center
                )
            }

        }

        if (chairState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }
}

