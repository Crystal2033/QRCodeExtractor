package com.crystal2033.qrextractor.scanner_feature.scanned_info_list.vm_view_communication

import com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication.UIScannerEvent

sealed class UIScannedDataListEvent {
    data class ShowSnackBar(val message: String) : UIScannedDataListEvent()
}