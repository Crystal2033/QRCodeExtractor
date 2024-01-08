package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.person

import android.graphics.Bitmap
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
import com.crystal2033.qrextractor.add_object_feature.general.view_elements.DropListView
import com.crystal2033.qrextractor.add_object_feature.general.view_elements.TextFieldView
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

    val personPhotoState = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val isNeedToShowCamera = remember {
        mutableStateOf(false)
    }

    isAllFieldsInsertedState.value = isAllFieldInsertedCorrectly(personState.value)

    Box {
        if (!isNeedToShowCamera.value) {
            Row {
                Column {
                    personState.value.imageState.value?.let { bitmap ->
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
                        fieldName = "First name",
                        fieldValue = personState.value.firstNameState
                    )
                    Spacer(modifier = Modifier.height(spaceBetween))
                    TextFieldView(
                        fieldName = "Second name",
                        fieldValue = personState.value.secondNameState
                    )
                    Spacer(modifier = Modifier.height(spaceBetween))
                    TextFieldView(
                        fieldName = "Inventory number",
                        fieldValue = personState.value.inventoryNumberState
                    )

                    Spacer(modifier = Modifier.height(spaceBetween))
                    DropListView(
                        fieldName = "Department",
                        listOfObjects = viewModel.listOfDepartments.map { it.name },
                        chosenValue = personState.value.departmentState.value.name
                    )
                    Spacer(modifier = Modifier.height(spaceBetween))
                    DropListView(
                        fieldName = "Title",
                        listOfObjects = viewModel.listOfTitles.map { it.name },
                        chosenValue = personState.value.titleState.value.name
                    )
                    Spacer(modifier = Modifier.height(spaceBetween))
                    DropListView(
                        fieldName = "Workspace",
                        listOfObjects = viewModel.listOfWorkSpaces.map { it.id.toString() },
                        chosenValue = personState.value.workspaceState.value.id
                    )
                }
            }
        } else {
            CameraXView(
                darkTheme = true,
                personState.value.imageState,
                onCloseButtonClicked = {
                    isNeedToShowCamera.value = false
                })
        }


    }

}

fun isAllFieldInsertedCorrectly(personState: PersonState): Boolean {
    return (personState.firstNameState.value.isNotBlank()
            &&
            personState.secondNameState.value.isNotBlank()
            &&
            personState.departmentState.value.name.value.isNotBlank()
            &&
            personState.titleState.value.name.value.isNotBlank()
            &&
            personState.workspaceState.value.id.value.isNotBlank()
            )
}