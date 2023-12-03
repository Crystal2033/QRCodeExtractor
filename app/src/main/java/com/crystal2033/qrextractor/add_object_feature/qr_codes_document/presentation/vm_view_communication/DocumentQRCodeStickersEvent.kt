package com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.vm_view_communication

import android.net.Uri
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo

sealed class DocumentQRCodeStickersEvent {
    data class OnChangeQRCodeStickerSize(
        val oldQRCodeStickerInfo: QRCodeStickerInfo,
        val newStickerSize: QRCodeStickerInfo.StickerSize
    ) : DocumentQRCodeStickersEvent()

    data class CreateDocumentByDirUriAndFileName(val dirUri: Uri, val fileName: String) : DocumentQRCodeStickersEvent()
}