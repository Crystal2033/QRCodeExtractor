package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.person


import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import com.crystal2033.qrextractor.core.model.Department
import com.crystal2033.qrextractor.core.model.Title
import com.crystal2033.qrextractor.core.model.WorkSpace

//val id: MutableState<Long> = mutableLongStateOf(0L),
//val firstNameState: MutableState<String> = mutableStateOf(""),
//val secondNameState: MutableState<String> = mutableStateOf(""),
//val inventoryNumberState: MutableState<String> = mutableStateOf(""),
//val imageState: MutableState<Bitmap?> = mutableStateOf(null),
//val titleState: MutableState<TitleState> = mutableStateOf(TitleState()),
//val departmentState: MutableState<DepartmentState> = mutableStateOf(DepartmentState()),
//val workspaceState: MutableState<WorkspaceState> = mutableStateOf(WorkspaceState())

//val id: Long = 0,
//val firstName: String = "",
//val secondName: String = "",
//val image: Bitmap? = null,
//val title: Title? = null,
//val department: Department? = null,
//val workSpace: WorkSpace? = null

data class PersonState(
    var id: Long = 0,
    var firstName: String = "",
    var secondName: String = "",
    var inventoryNumber: String = "",
    var image: Bitmap? = null,
    var title: Title? = null,
    var department: Department? = null,
    var workSpace: WorkSpace? = null
)
