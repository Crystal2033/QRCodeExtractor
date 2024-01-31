package com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.vm_view_communication

import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle

sealed class ScannedObjectsListEvent {
    data class OnScannedObjectClicked(val scannedObject: InventarizedAndQRScannableModel) :
        ScannedObjectsListEvent()

    data object Refresh : ScannedObjectsListEvent()

    data class DeleteObjectFromScannedGroup(val objectId: Long) : ScannedObjectsListEvent()

    data class OnPlaceUpdate(val userAndPlaceBundle: UserAndPlaceBundle) : ScannedObjectsListEvent()
}
