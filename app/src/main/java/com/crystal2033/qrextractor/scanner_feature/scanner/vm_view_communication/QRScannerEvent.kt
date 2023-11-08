package com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication

import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData

sealed class QRScannerEvent {
    data class OnAddObjectInList(
        val scannableObject: QRScannableData,
        val addEvenIfDuplicate: Boolean
    ) : QRScannerEvent()

    data class OnScanQRCode(val scannedData: String) : QRScannerEvent()
    data object OnAddScannedGroup : QRScannerEvent()
    data class OnAddNameForScannedGroup(val groupName: String) : QRScannerEvent()

    data object ClearListOfScannedObjects : QRScannerEvent()
}
