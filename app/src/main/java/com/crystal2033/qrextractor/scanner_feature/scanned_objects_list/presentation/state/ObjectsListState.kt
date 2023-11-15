package com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.state

import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData

data class ObjectsListState(
    val listOfObjects: List<QRScannableData>? = emptyList(),
    val isLoading: Boolean = false
)
