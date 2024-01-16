package com.crystal2033.qrextractor.auth_feature.presentation.vm_view_communication

sealed class UIUserLoginEvent {
    data class OnSuccessLoginNavigate(val route: String) : UIUserLoginEvent()
    data class OnAuthError(val errorMessage: String) : UIUserLoginEvent()

}