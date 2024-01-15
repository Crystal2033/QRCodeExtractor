package com.crystal2033.qrextractor.auth_feature.presentation.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class UserLoginDTOState(
    val loginState: MutableState<String> = mutableStateOf(""),
    val passwordState: MutableState<String> = mutableStateOf("")
) {
}