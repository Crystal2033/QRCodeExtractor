package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.person

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf

data class TitleState(
    val name: MutableState<String> = mutableStateOf(""),
    //val id: MutableState<Long> = mutableLongStateOf(0L)
)