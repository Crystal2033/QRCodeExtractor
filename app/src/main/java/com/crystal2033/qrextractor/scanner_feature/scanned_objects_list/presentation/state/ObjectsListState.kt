package com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.state

import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel

data class ObjectsListState(
    val listOfObjectsWithCabinetName: MutableList<InventarizedObjectInfoAndIDInLocalDB> = arrayListOf(),
    val isLoading: Boolean = false
)
