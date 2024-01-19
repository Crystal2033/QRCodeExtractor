package com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.vm_view_communication

sealed class PlaceChoiceEvent {
    data class OnBranchChanged(val chosenId: Long) : PlaceChoiceEvent()
    data class OnBuildingChanged(val chosenId: Long) : PlaceChoiceEvent()

    data object OnContinueClicked : PlaceChoiceEvent()
}