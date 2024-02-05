package com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation.vm_view_communication

sealed class InventoryCheckEvent {
    data class OnScanQRCode(val scannedString : String) : InventoryCheckEvent()
}