package com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.vm_view_communication

import android.net.Uri

sealed class FileLoaderEvent {
    data class SetFilePath(val uri: Uri) : FileLoaderEvent()

    data object StartInventoryCheck : FileLoaderEvent()
}