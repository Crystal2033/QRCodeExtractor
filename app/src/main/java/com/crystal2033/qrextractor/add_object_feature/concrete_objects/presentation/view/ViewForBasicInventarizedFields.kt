package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state.BaseDeviceState
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication.AddNewObjectEvent
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication.UIAddNewObjectEvent
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.getGoodNameByDatabaseObjectType
import com.crystal2033.qrextractor.core.camera_for_photos.CameraXView
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.ui.text_elements.TextFieldView
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BaseViewForInventarizedDevice(
    viewModel: BaseAddObjectViewModel,
    userAndPlaceBundle: UserAndPlaceBundle? = null,
    deviceState: BaseDeviceState,
    isNeedToShowCamera: MutableState<Boolean>,
    onNavigate: (UIAddNewObjectEvent.Navigate) -> Unit,
    snackbarHostState: SnackbarHostState,
    isForUpdate: Boolean = false,
    onChangePlaceClicked: () -> Unit = {}
) {
    val spaceBetween = 15.dp
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


    if (!isNeedToShowCamera.value) {
        deviceState.deviceState.value.image?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Picture",
                modifier = Modifier
                    .size(300.dp)
                    .padding(20.dp)
            )
        }
        Button(onClick = {
            isNeedToShowCamera.value = true
        }) {
            Text(text = "Add picture")
        }
        Spacer(modifier = Modifier.height(3 * spaceBetween))
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = getGoodNameByDatabaseObjectType(deviceState.deviceState.value.getDatabaseTableName()),
                color = Color.LightGray,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(spaceBetween))
        TextFieldView(
            fieldHint = "Name",
            currentText = deviceState.deviceState.value.name,
            onValueChanged = {
                viewModel.onEvent(AddNewObjectEvent.OnNameChanged(it))
            },
            horizontalArrangement = Arrangement.Center
        )
        Spacer(modifier = Modifier.height(spaceBetween))
        TextFieldView(
            fieldHint = "Inventory number",
            currentText = deviceState.deviceState.value.inventoryNumber,
            onValueChanged = {
                viewModel.onEvent(AddNewObjectEvent.OnInventoryNumberChanged(it))
                //TODO: CHECK UNIQUE. Can make this call inside BaseAddObjectViewModel
            },
            isEnabled = !isForUpdate,
            horizontalArrangement = Arrangement.Center
        )
        Spacer(modifier = Modifier.height(spaceBetween))

        Text(text = "Change place",
            fontSize = 20.sp,
            color = Color.Cyan,
            modifier = Modifier.clickable {
                onChangePlaceClicked()
            })
        Spacer(modifier = Modifier.height(spaceBetween))

        TextFieldView(
            fieldHint = "Branch",
            currentText = userAndPlaceBundle?.branch?.name ?: "",
            isEnabled = false,
            horizontalArrangement = Arrangement.Center
        )
        Spacer(modifier = Modifier.height(spaceBetween))
        TextFieldView(
            fieldHint = "Building address",
            currentText = userAndPlaceBundle?.building?.address ?: "",
            isEnabled = false,
            horizontalArrangement = Arrangement.Center
        )
        Spacer(modifier = Modifier.height(spaceBetween))
        TextFieldView(
            fieldHint = "Cabinet",
            currentText = userAndPlaceBundle?.cabinet?.name ?: "",
            isEnabled = false,
            horizontalArrangement = Arrangement.Center
        )
    } else {
        CameraXView(
            darkTheme = true,
            onImageChanged = {
                viewModel.onEvent(AddNewObjectEvent.OnImageChanged(it))
            },
            onCloseButtonClicked = {
                isNeedToShowCamera.value = false
            })
    }


}
