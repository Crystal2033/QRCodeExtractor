package com.crystal2033.qrextractor.auth_feature.presentation.vm_view_communication

sealed class UserLoginEvent {
    data class OnLoginChanged(val login: String) : UserLoginEvent()
    data class OnPasswordChanged(val password: String) : UserLoginEvent()

    data object OnLoginPressed : UserLoginEvent()
}