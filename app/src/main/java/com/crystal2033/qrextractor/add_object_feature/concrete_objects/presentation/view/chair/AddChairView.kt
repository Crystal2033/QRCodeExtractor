package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.chair

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.chair.AddChairViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication.UIAddNewObjectEvent
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication.chair.AddChairEvent
import com.crystal2033.qrextractor.core.camera_for_photos.CameraXView
import com.crystal2033.qrextractor.ui.text_elements.TextFieldView
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddChairView(
    viewModel: AddChairViewModel,
    isAllFieldsInsertedState: MutableState<Boolean>,
    onNavigate: (UIAddNewObjectEvent.Navigate) -> Unit,
    snackbarHostState: SnackbarHostState
) {

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIAddNewObjectEvent.Navigate -> {
                    onNavigate(event)
                }

                is UIAddNewObjectEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = "Okay",
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

    val spaceBetween = 15.dp

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
        if (!isNeedToShowCamera.value) {
            Row {
                Column {
                    chairState.deviceState.value.image?.let { bitmap ->
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Picture",
                            modifier = Modifier
                                .size(200.dp)
                                .padding(20.dp)
                        )
                    }
                    Button(onClick = {
                        isNeedToShowCamera.value = true
                    }) {
                        Text(text = "Add picture")
                    }
                }


                Column {
                    TextFieldView(
                        fieldHint = "Name",
                        currentText = chairState.deviceState.value.name,
                        onValueChanged = {
                            viewModel.onEvent(AddChairEvent.OnNameChanged(it))
                        },
                    )
                    Spacer(modifier = Modifier.height(spaceBetween))
                    TextFieldView(
                        fieldHint = "Inventory number",
                        currentText = chairState.deviceState.value.inventoryNumber,
                        onValueChanged = {
                            viewModel.onEvent(AddChairEvent.OnInventoryNumberChanged(it))
                            //TODO: CHECK UNIQUE
                        }
                    )
                    Spacer(modifier = Modifier.height(spaceBetween))
                    TextFieldView(
                        fieldHint = "Branch",
                        currentText = viewModel.userAndPlaceBundleState.value.branch.name,
                        isEnabled = false
                    )
                    Spacer(modifier = Modifier.height(spaceBetween))
                    TextFieldView(
                        fieldHint = "Building address",
                        currentText = viewModel.userAndPlaceBundleState.value.building.address,
                        isEnabled = false
                    )
                    Spacer(modifier = Modifier.height(spaceBetween))
                    TextFieldView(
                        fieldHint = "Cabinet",
                        currentText = viewModel.userAndPlaceBundleState.value.cabinet.name,
                        isEnabled = false
                    )
                }
            }
        } else {
            CameraXView(
                darkTheme = true,
                onImageChanged = {
                    viewModel.onEvent(AddChairEvent.OnImageChanged(it))
                },
                onCloseButtonClicked = {
                    isNeedToShowCamera.value = false
                })
        }

        if (chairState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }
}