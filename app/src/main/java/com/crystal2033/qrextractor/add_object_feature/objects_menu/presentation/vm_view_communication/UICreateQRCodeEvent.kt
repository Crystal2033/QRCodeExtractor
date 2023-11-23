package com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.vm_view_communication

sealed class UICreateQRCodeEvent{
    data class Navigate(val route: String) : UICreateQRCodeEvent()
}
