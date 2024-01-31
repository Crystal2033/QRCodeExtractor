package com.crystal2033.qrextractor.scanner_feature.list_of_groups.vm_view_communication

import androidx.compose.ui.graphics.vector.ImageVector

sealed class UIScannedGroupsListEvent {
    data class ShowSnackBar(val message: String) : UIScannedGroupsListEvent()
    data class Navigate(val route: String) : UIScannedGroupsListEvent()

    data class ShowMessagedDialogWindow(
        val message: String,
        val onDeclineAction: () -> Unit,
        val onAcceptAction: () -> Unit,
        val dialogTitle: String,
        val icon: ImageVector
    ) : UIScannedGroupsListEvent()
}