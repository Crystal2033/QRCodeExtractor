package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication.chair

import android.graphics.Bitmap

sealed class AddChairEvent {

    data class OnNameChanged(val name: String) : AddChairEvent()
    data class OnInventoryNumberChanged(val inventoryNumber: String) : AddChairEvent()
    data class OnImageChanged(val image: Bitmap?) : AddChairEvent()
}