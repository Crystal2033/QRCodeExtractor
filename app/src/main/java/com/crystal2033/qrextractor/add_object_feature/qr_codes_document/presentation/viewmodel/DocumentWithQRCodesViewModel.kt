package com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.viewmodel

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel.CreateQRCodesViewModel
import com.crystal2033.qrextractor.core.model.User
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


class DocumentWithQRCodesViewModel @AssistedInject constructor(
    @Assisted private val listOfQRCodes: SnapshotStateList<QRCodeStickerInfo>
) : ViewModel() {

    @AssistedFactory
    interface Factory{
        fun create(listOfQRCodes: SnapshotStateList<QRCodeStickerInfo>) : DocumentWithQRCodesViewModel
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

    fun getSizeOfList() : Int{
        return listOfQRCodes.size
    }



}