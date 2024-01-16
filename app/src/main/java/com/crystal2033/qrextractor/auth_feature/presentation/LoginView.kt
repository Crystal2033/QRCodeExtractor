package com.crystal2033.qrextractor.auth_feature.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crystal2033.qrextractor.add_object_feature.general.view_elements.TextFieldView
import com.crystal2033.qrextractor.auth_feature.presentation.viewmodel.ProfileViewModel
import com.crystal2033.qrextractor.auth_feature.presentation.vm_view_communication.UIUserLoginEvent
import com.crystal2033.qrextractor.auth_feature.presentation.vm_view_communication.UserLoginEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginView(
    viewModel: ProfileViewModel,
    onNavigate: (UIUserLoginEvent.OnSuccessLoginNavigate) -> Unit
) {
    val userLoginDTO = remember {
        viewModel.userLoginDTO
    }

    val isAuthError = remember {
        mutableStateOf(false)
    }

    val errorMessage = remember {
        mutableStateOf("")
    }

    val spaceBetween = 15.dp

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIUserLoginEvent.OnSuccessLoginNavigate -> {
                    isAuthError.value = false
                    errorMessage.value = ""
                    onNavigate(event)
                }

                is UIUserLoginEvent.OnAuthError -> {
                    errorMessage.value = event.errorMessage
                    isAuthError.value = true
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            if (errorMessage.value.isNotBlank()) {
                Text(
                    text = errorMessage.value,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            TextFieldView(
                fieldHint = "Login",
                onValueChanged = {
                    userLoginDTO.value.login = it
                },
                horizontalArrangement = Arrangement.Center,
                focusedColor = if (isAuthError.value) Color.Red else Color.White,
                unfocusedColor = if (isAuthError.value) Color.Red else Color.Gray
            )
            Spacer(modifier = Modifier.height(spaceBetween))
            TextFieldView(
                fieldHint = "Password",
                onValueChanged = {
                    userLoginDTO.value.password = it
                },
                horizontalArrangement = Arrangement.Center,
                isPassword = true,
                focusedColor = if (isAuthError.value) Color.Red else Color.White,
                unfocusedColor = if (isAuthError.value) Color.Red else Color.Gray
            )

            Spacer(modifier = Modifier.height(spaceBetween))

            Button(
                onClick = {
                    viewModel.onEvent(UserLoginEvent.OnLoginPressed)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Login")
            }
        }
    }
}