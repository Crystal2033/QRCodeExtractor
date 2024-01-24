package com.crystal2033.qrextractor.scanner_feature.scanner.presentation.state

import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel

data class ScannedDataState(
    val scannedDataInfo: InventarizedAndQRScannableModel? = null,
    val isLoading: Boolean = false
)