package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.person

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.person.AddPersonViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication.UIAddNewObjectEvent
import com.crystal2033.qrextractor.ui.text_elements.DropListView
import com.crystal2033.qrextractor.ui.text_elements.TextFieldView
import com.crystal2033.qrextractor.core.camera_for_photos.CameraXView
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddPersonView(
    viewModel: AddPersonViewModel,
    isAllFieldsInsertedState: MutableState<Boolean>,
    onNavigate: (UIAddNewObjectEvent.Navigate) -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIAddNewObjectEvent.Navigate -> {
                    onNavigate(event)
                }
            }
        }
    }

    val spaceBetween = 15.dp

    val personState = remember {
        viewModel.personState
    }

    val isNeedToShowCamera = remember {
        mutableStateOf(false)
    }

    isAllFieldsInsertedState.value = isAllFieldInsertedCorrectly(personState.value)

    Box {
        if (!isNeedToShowCamera.value) {
            Row {
                Column {
                    personState.value.image?.let { bitmap ->
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
                        fieldHint = "First name",
                        onValueChanged = {
                            personState.value.firstName = it
                        },
                    )
                    Spacer(modifier = Modifier.height(spaceBetween))
                    TextFieldView(
                        fieldHint = "Second name",
                        onValueChanged = {
                            personState.value.secondName = it
                        }
                    )
                    Spacer(modifier = Modifier.height(spaceBetween))
                    TextFieldView(
                        fieldHint = "Inventory number",
                        onValueChanged = {
                            personState.value.inventoryNumber = it
                        }
                    )

                    Spacer(modifier = Modifier.height(spaceBetween))
                    DropListView(
                        fieldName = "Department",
                        listOfObjects = viewModel.listOfDepartments.map { it.name },
                        onValueChanged = {
                            personState.value.department?.name = it
                        },
                    )
                    Spacer(modifier = Modifier.height(spaceBetween))
                    DropListView(
                        fieldName = "Title",
                        listOfObjects = viewModel.listOfTitles.map { it.name },
                        onValueChanged = {
                            personState.value.title?.name = it
                        }
                    )
                    Spacer(modifier = Modifier.height(spaceBetween))
                    DropListView(
                        fieldName = "Workspace",
                        listOfObjects = viewModel.listOfWorkSpaces.map { it.id.toString() },
                        onValueChanged = {
                            personState.value.workSpace?.id = it.toLong()
                        }
                    )
                }
            }
        } else {
            CameraXView(
                darkTheme = true,
                onImageChanged = {
                    personState.value.image = it
                },
                onCloseButtonClicked = {
                    isNeedToShowCamera.value = false
                })
        }


    }

}

fun isAllFieldInsertedCorrectly(personState: PersonState): Boolean {
    return (personState.firstName.isNotBlank()
            &&
            personState.secondName.isNotBlank()
            &&
            personState.department?.name?.isNotBlank() ?: false
            &&
            personState.title?.name?.isNotBlank() ?: false
            &&
            personState.workSpace?.id != 0L
            )
}