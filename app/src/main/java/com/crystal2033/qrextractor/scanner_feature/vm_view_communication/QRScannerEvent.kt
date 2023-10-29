package com.crystal2033.qrextractor.scanner_feature.vm_view_communication

import com.crystal2033.qrextractor.scanner_feature.domain.model.QRScannableData

sealed class QRScannerEvent {
    data class OnAddObjectInList(
        val scannableObject: QRScannableData,
        val addEvenIfDuplicate: Boolean
    ) : QRScannerEvent()

    data class OnScanQRCode(val scannedData: String) : QRScannerEvent()
    object OnGoToScannedList : QRScannerEvent()
}
