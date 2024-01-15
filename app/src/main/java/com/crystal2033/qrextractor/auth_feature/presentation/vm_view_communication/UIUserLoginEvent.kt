package com.crystal2033.qrextractor.auth_feature.presentation.vm_view_communication

sealed class UIUserLoginEvent {
    data class Navigate(val route: String) : UIUserLoginEvent()
}