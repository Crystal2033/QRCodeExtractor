package com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation.vm_view_communication

sealed class UIInventoryCheckEvent {
    data class Navigate(val route: String) : UIInventoryCheckEvent()
}