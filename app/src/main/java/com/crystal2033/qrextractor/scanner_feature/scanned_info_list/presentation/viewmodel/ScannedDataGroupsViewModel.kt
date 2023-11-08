package com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.vm_view_communication.ScannedDataListEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication.QRScannerEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication.UIScannerEvent
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ScannedDataGroupsViewModel : ViewModel() {
    fun onEvent(event: ScannedDataListEvent) {
//        when (event) {
//
//        }
    }
}