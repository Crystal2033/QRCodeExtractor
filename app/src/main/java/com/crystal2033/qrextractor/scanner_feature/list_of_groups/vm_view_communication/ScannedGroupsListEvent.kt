package com.crystal2033.qrextractor.scanner_feature.list_of_groups.vm_view_communication

import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.ScannedGroup

sealed class ScannedGroupsListEvent {
    data class OnGroupClickedEvent(val groupId: Long) : ScannedGroupsListEvent()

    data class OnDeleteGroupClicked(val scannedGroup: ScannedGroup) : ScannedGroupsListEvent()
}