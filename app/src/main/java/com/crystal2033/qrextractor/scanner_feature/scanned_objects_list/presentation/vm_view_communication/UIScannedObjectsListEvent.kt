package com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.vm_view_communication

sealed class UIScannedObjectsListEvent{
    data class ShowSnackBar(val message: String) : UIScannedObjectsListEvent()
}
