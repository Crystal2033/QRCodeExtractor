package com.crystal2033.qrextractor.scanner_feature.presentation.viewmodel

import com.crystal2033.qrextractor.scanner_feature.domain.model.QRScannableData

sealed class QRScannerEvent {
    data class OnAddObjectInList(
        val scannableObject: QRScannableData,
        val addEvenIfDuplicate: Boolean
    ) : QRScannerEvent()

    object OnGoToScannedList : QRScannerEvent()
}
