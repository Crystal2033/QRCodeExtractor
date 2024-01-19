package com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.vm_view_communication

sealed class UIPlaceChoiceEvent {
    data class Navigate(val route: String) : UIPlaceChoiceEvent()
}