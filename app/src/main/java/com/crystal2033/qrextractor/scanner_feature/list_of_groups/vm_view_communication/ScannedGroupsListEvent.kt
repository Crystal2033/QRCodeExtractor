package com.crystal2033.qrextractor.scanner_feature.list_of_groups.vm_view_communication

sealed class ScannedGroupsListEvent {
    data class OnGroupClickedEvent(val groupId: Long) : ScannedGroupsListEvent()
}