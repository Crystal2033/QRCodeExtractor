package com.crystal2033.qrextractor.scanner_feature.presentation.util

import androidx.compose.ui.graphics.vector.ImageVector

sealed class UIEvent {
    data class ShowSnackBar(val message: String) : UIEvent()
    data class ShowDialogWindow(
        val message: String,
        val onDeclineAction: () -> Unit,
        val onAcceptAction: () -> Unit,
        val dialogTitle: String,
        val icon: ImageVector
    ) : UIEvent()
}