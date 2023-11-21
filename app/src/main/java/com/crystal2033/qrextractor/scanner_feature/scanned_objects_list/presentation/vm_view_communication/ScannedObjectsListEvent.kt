package com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.vm_view_communication

import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData

sealed class ScannedObjectsListEvent{
    data class OnScannedObjectClicked(val scannedObject: QRScannableData) : ScannedObjectsListEvent()
}
