package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.person


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap

//val id: Long = 0,
//val department: Department? = null,
//val firstName: String = "",
//val image: ImageBitmap? = null,
//val secondName: String = "",
//val title: Title? = null,
//val workSpace: WorkSpace? = null

data class PersonState(
    val id: MutableState<Long> = mutableLongStateOf(0L),
    val firstNameState: MutableState<String> = mutableStateOf(""),
    val secondNameState: MutableState<String> = mutableStateOf(""),
    val inventoryNumberState: MutableState<String> = mutableStateOf(""),
    val imageState: MutableState<ImageBitmap> = mutableStateOf(ImageBitmap(1,1)),
    val titleState: MutableState<TitleState> = mutableStateOf(TitleState()),
    val departmentState: MutableState<DepartmentState> = mutableStateOf(DepartmentState()),
    val workspaceState: MutableState<WorkspaceState> = mutableStateOf(WorkspaceState())
) {
}
