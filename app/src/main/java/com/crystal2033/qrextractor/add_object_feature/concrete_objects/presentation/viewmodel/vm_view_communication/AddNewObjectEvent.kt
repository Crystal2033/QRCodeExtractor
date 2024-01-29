package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication

import android.graphics.Bitmap

sealed class AddNewObjectEvent {

    data class OnNameChanged(val name: String) : AddNewObjectEvent()
    data class OnInventoryNumberChanged(val inventoryNumber: String) : AddNewObjectEvent()
    data class OnImageChanged(val image: Bitmap?) : AddNewObjectEvent()

    data class OnCabinetChanged(val cabinetId: Long) : AddNewObjectEvent()
}
