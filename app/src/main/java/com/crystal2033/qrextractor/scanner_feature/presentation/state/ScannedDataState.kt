package com.crystal2033.qrextractor.scanner_feature.presentation.state

import com.crystal2033.qrextractor.scanner_feature.domain.model.Person
import com.crystal2033.qrextractor.scanner_feature.domain.model.QRScannableData

data class ScannedDataState(
    val scannedDataInfo: QRScannableData? = null,
    val isLoading: Boolean = false
)