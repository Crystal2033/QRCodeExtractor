package com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


class DocumentWithQRCodesViewModel @AssistedInject constructor(
    @Assisted private val listOfQRCodes: SnapshotStateList<QRCodeStickerInfo>
) : ViewModel() {


    private val _listOfQRCodesState = mutableStateListOf<QRCodeStickerInfo>()
    val listOfQRCodesState: SnapshotStateList<QRCodeStickerInfo> = _listOfQRCodesState

    @AssistedFactory
    interface Factory {
        fun create(listOfQRCodes: SnapshotStateList<QRCodeStickerInfo>): DocumentWithQRCodesViewModel
    }

    init {
        listOfQRCodes.forEach { qrCodeSticker ->
            _listOfQRCodesState.add(qrCodeSticker)
        }
    }

    fun onValueChanged(
        oldQRStickerInfo: QRCodeStickerInfo,
        newStickerSize: QRCodeStickerInfo.StickerSize
    ) { //in onEvent
        val index = _listOfQRCodesState.indexOf(oldQRStickerInfo)
        _listOfQRCodesState[index] = _listOfQRCodesState[index].copy(
            qrCode = oldQRStickerInfo.qrCode,
            essentialName = oldQRStickerInfo.essentialName,
            inventoryNumber = oldQRStickerInfo.inventoryNumber,
            databaseObjectTypes = oldQRStickerInfo.databaseObjectTypes,
            stickerSize = newStickerSize
        )
    }

    fun dropLast() {
        listOfQRCodes.removeLast()
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: DocumentWithQRCodesViewModel.Factory,
            listOfQRCodes: SnapshotStateList<QRCodeStickerInfo>
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(listOfQRCodes) as T
            }
        }
    }

    fun getSizeOfList(): Int {
        return listOfQRCodes.size
    }


}