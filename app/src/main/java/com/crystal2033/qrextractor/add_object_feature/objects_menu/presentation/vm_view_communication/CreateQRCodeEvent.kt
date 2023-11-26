package com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.vm_view_communication

import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes

sealed class CreateQRCodeEvent{
    data class SetChosenObjectClass(val objectType: DatabaseObjectTypes) : CreateQRCodeEvent()
    data class OnAddNewObjectInList(val qrCodeStickerInfo: QRCodeStickerInfo): CreateQRCodeEvent()
}
