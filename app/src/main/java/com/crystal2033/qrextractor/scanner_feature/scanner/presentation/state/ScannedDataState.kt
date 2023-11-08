package com.crystal2033.qrextractor.scanner_feature.scanner.presentation.state

import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData

data class ScannedDataState(
    val scannedDataInfo: QRScannableData? = null,
    val isLoading: Boolean = false
)