package com.crystal2033.qrextractor.scanner_feature.presentation.state

import com.crystal2033.qrextractor.scanner_feature.domain.model.Person

data class PersonState(
    val scannedDataInfo: Person? = null,
    val isLoading: Boolean = false
) : ScannedDataState()
