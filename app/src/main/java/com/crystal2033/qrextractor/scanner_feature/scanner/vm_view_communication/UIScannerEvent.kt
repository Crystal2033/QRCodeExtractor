package com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication

import androidx.compose.ui.graphics.vector.ImageVector

sealed class UIScannerEvent {

    data object PopBackStack: UIScannerEvent() //Navigate back to prev screen
    data class Navigate(val route: String): UIScannerEvent()
    data class ShowSnackBar(val message: String) : UIScannerEvent()
    data class ShowMessagedDialogWindow(
        val message: String,
        val onDeclineAction: () -> Unit,
        val onAcceptAction: () -> Unit,
        val dialogTitle: String,
        val icon: ImageVector
    ) : UIScannerEvent()

    data object ShowScannedGroupNameDialogWindow : UIScannerEvent()
}