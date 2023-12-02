package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.person

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.person.AddPersonViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication.UIAddNewObjectEvent
import com.crystal2033.qrextractor.add_object_feature.general.view_elements.DropListView
import com.crystal2033.qrextractor.add_object_feature.general.view_elements.TextFieldView
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

    isAllFieldsInsertedState.value = isAllFieldInsertedCorrectly(personState.value)

    Box {
        Column() {
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