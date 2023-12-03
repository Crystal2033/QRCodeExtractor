package com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.vm_view_communication.DocumentQRCodeStickersEvent
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.vm_view_communication.UIDocumentQRCodeStickersEvent
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException
import java.io.IOException
import java.io.OutputStream


class DocumentWithQRCodesViewModel @AssistedInject constructor(
    @Assisted val listOfQRCodes: SnapshotStateList<QRCodeStickerInfo>,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val fileName = mutableStateOf("")
    private val dirUri = mutableStateOf<Uri?>(null)
//    private val _listOfQRCodesState = mutableStateListOf<QRCodeStickerInfo>()
//    val listOfQRCodesState: SnapshotStateList<QRCodeStickerInfo> = _listOfQRCodesState

    private val _eventFlow = Channel<UIDocumentQRCodeStickersEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    @AssistedFactory
    interface Factory {
        fun create(listOfQRCodes: SnapshotStateList<QRCodeStickerInfo>): DocumentWithQRCodesViewModel
    }

//    init {
//        listOfQRCodes.forEach { qrCodeSticker ->
//            _listOfQRCodesState.add(qrCodeSticker)
//        }
//    }

    fun onEvent(event: DocumentQRCodeStickersEvent) {
        when (event) {
            is DocumentQRCodeStickersEvent.OnChangeQRCodeStickerSize -> {
                onValueChanged(event.oldQRCodeStickerInfo, event.newStickerSize)
            }

//            is DocumentQRCodeStickersEvent.CreatePDFFileWithQRCodes -> {
//                Log.i(LOG_TAG_NAMES.INFO_TAG, "OnEvent with pdf: ${event.pathOfFile.path}")
//                try {
//                    val contentResolver = context.contentResolver
//                    try {
//                        val pickedDir = DocumentFile.fromTreeUri(context, event.pathOfFile)
//                        val tmpFile = pickedDir?.createFile("text/plain", "somefile")
//                        val out: OutputStream? = contentResolver.openOutputStream(tmpFile!!.uri)
//                        out?.write(("\uFEFF" + "asd\r\n").toByteArray()) // adding BOM to the start for Excel to recognize utf8
//                        out?.close()
//                    } catch (e: FileNotFoundException) {
//                        e.printStackTrace()
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
//                } catch (e: Exception) {
//                    Log.e(LOG_TAG_NAMES.ERROR_TAG, e.message ?: "")
//                }
//
//            }


            is DocumentQRCodeStickersEvent.CreateDocumentByDirUriAndFileName -> {
//                dirUri.value = event.dirUri
//                fileName.value = event.fileName

                Log.i(
                    LOG_TAG_NAMES.INFO_TAG,
                    "Creating file: ${event.dirUri.path}/${event.fileName}"
                )
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        try {
                            val contentResolver = context.contentResolver
                            delay(3000L)
                            try {
                                val pickedDir = DocumentFile.fromTreeUri(context, event.dirUri)
                                val tmpFile = pickedDir?.createFile("application/pdf", event.fileName)
                                val out: OutputStream? =
                                    contentResolver.openOutputStream(tmpFile!!.uri)
                                out?.write(("Success!!!\n").toByteArray()) // adding BOM to the start for Excel to recognize utf8
                                out?.close()
                            } catch (e: FileNotFoundException) {
                                e.printStackTrace()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        } catch (e: Exception) {
                            Log.e(LOG_TAG_NAMES.ERROR_TAG, e.message ?: "")
                        }
                    }
                    sendUiEvent(UIDocumentQRCodeStickersEvent.OnFileCreatedSuccessfully(event.dirUri.path + event.fileName))
                }

            }
        }
    }

    private fun onValueChanged(
        oldQRStickerInfo: QRCodeStickerInfo,
        newStickerSize: QRCodeStickerInfo.StickerSize
    ) { //in onEvent
        val index = listOfQRCodes.indexOf(oldQRStickerInfo)
        listOfQRCodes[index] = listOfQRCodes[index].copy(
            qrCode = oldQRStickerInfo.qrCode,
            essentialName = oldQRStickerInfo.essentialName,
            inventoryNumber = oldQRStickerInfo.inventoryNumber,
            databaseObjectTypes = oldQRStickerInfo.databaseObjectTypes,
            stickerSize = newStickerSize
        )
    }

    private fun sendUiEvent(event: UIDocumentQRCodeStickersEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
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

}