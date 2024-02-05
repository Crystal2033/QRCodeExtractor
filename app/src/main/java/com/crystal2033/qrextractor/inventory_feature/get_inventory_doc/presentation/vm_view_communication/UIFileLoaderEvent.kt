package com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.vm_view_communication

import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.LoadStatus

sealed class UIFileLoaderEvent {
    //data class SetLoadStatusInfo(val status: LoadStatus) : UIFileLoaderEvent()
    data class Navigate(val route: String) : UIFileLoaderEvent()
}