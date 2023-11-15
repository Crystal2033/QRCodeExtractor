package com.crystal2033.qrextractor.scanner_feature.list_of_groups.vm_view_communication

sealed class UIScannedGroupsListEvent {
    data class ShowSnackBar(val message: String) : UIScannedGroupsListEvent()
    data class Navigate(val route: String): UIScannedGroupsListEvent()
}