package com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication

import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData

sealed class QRScannerEvent {
    data class OnAddObjectInList(
        val scannableObject: InventarizedAndQRScannableModel,
        val addEvenIfDuplicate: Boolean
    ) : QRScannerEvent()

    data class OnScanQRCode(val scannedData: String) : QRScannerEvent()
    data object OnAddScannedGroup : QRScannerEvent()
    data class OnAddNameForScannedGroup(val groupName: String) : QRScannerEvent()

    data object ClearListOfScannedObjects : QRScannerEvent()

    data object OnGoToScannedGroupsWindow : QRScannerEvent()

    data class OnDeleteDeviceFromServerClicked(val qrScannableData: QRScannableData) :
        QRScannerEvent()
}
