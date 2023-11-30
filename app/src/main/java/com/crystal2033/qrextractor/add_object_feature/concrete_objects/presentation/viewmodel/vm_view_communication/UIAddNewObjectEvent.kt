package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication

sealed class UIAddNewObjectEvent {
    data class Navigate(val route: String) : UIAddNewObjectEvent()
}
