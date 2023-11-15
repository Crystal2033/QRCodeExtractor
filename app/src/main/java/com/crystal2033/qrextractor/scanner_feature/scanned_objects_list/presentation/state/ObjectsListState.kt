package com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.state

import androidx.compose.animation.core.animateDpAsState
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData

data class ObjectsListState(
    val listOfObjects: MutableList<QRScannableData>? = arrayListOf(),
    val isLoading: Boolean = false
)
