package com.crystal2033.qrextractor.scanner_feature.presentation.util

sealed class UIEvent {
    data class ShowSnackBar(val message: String): UIEvent()
}