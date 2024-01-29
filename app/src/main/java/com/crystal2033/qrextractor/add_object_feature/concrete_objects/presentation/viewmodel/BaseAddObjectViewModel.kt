package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state.BaseDeviceState
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication.AddNewObjectEvent
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication.UIAddNewObjectEvent
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.util.QRCodeGenerator
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedModel
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.data.Converters
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseAddObjectViewModel(
    private val context: Context,
    private val converter: Converters,
    private val userAndPlaceBundle: UserAndPlaceBundle

) : ViewModel() {
    private val _eventFlow = Channel<UIAddNewObjectEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()
    private fun createQRCode(qrScannableData: QRScannableData): ImageBitmap {
        val convertedJsonFromString = converter.toJsonFromQRScannableData(qrScannableData)
        val bitmap = QRCodeGenerator.encodeAsBitmap(convertedJsonFromString, 250, 250)
        return bitmap.asImageBitmap()
    }
//
//    private val _userAndPlaceBundle = mutableStateOf(userAndPlaceBundle)
//    val userAndPlaceBundleState: State<UserAndPlaceBundle> = _userAndPlaceBundle

    abstract fun addObjectInDatabaseClicked(
        onAddObjectClicked: (QRCodeStickerInfo) -> Unit = {},
        afterUpdateAction: () -> Unit = {})


    protected fun sendUiEvent(event: UIAddNewObjectEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }

    private fun isBasicFieldsInsertedCorrectly(device: InventarizedModel): Boolean {
        return device.image != null &&
                device.name.isNotBlank() &&
                device.cabinetId != 0L &&
                device.inventoryNumber.isNotBlank()
    }

    protected fun isSpecificFieldInsertedCorrectly(): Boolean {
        return true
    }

    protected fun isAllNeededFieldsInsertedCorrectly(device: InventarizedModel): Boolean {
        return isSpecificFieldInsertedCorrectly() && isBasicFieldsInsertedCorrectly(device)
    }

    abstract fun isAllNeededFieldsInsertedCorrectly(): Boolean

    fun onEvent(event: AddNewObjectEvent) {
        when (event) {
            is AddNewObjectEvent.OnImageChanged -> {
                setNewImage(event.image)
            }

            is AddNewObjectEvent.OnInventoryNumberChanged -> {
                setNewInventoryNumber(event.inventoryNumber)
            }

            is AddNewObjectEvent.OnNameChanged -> {
                setNewName(event.name)
            }

            is AddNewObjectEvent.OnCabinetChanged -> {
                setNewCabinetId(event.cabinetId)
            }
        }
    }

    protected abstract fun setNewImage(image: Bitmap?)
    protected abstract fun setNewName(name: String)

    protected abstract fun setNewCabinetId(cabinetId: Long)
    protected abstract fun setNewInventoryNumber(invNumber: String)

    protected fun <M : InventarizedAndQRScannableModel> makeActionWithResourceResult(
        statusWithState: Resource<M>,
        deviceState: MutableState<BaseDeviceState>,
        onAddObjectClicked: (QRCodeStickerInfo) -> Unit = {},
        afterUpdateAction: () -> Unit = {},
        qrCodeStickerInfo: QRCodeStickerInfo? = null
    ) {
        when (statusWithState) {
            is Resource.Error -> {
                deviceState.value = deviceState.value.stateCopy(
                    deviceState.value.deviceState,
                    false
                )
                sendUiEvent(
                    UIAddNewObjectEvent.ShowSnackBar(
                        statusWithState.message ?: "Unknown error"
                    )
                )
            }

            is Resource.Loading -> {
                deviceState.value = deviceState.value.stateCopy(
                    deviceState.value.deviceState,
                    true
                )
            }

            is Resource.Success -> {
                deviceState.value.deviceState.value.id = statusWithState.data?.id ?: 0

                // Not need for update
                qrCodeStickerInfo?.let { existingQRCodeSticker ->
                    setQRStickerInfo(
                        statusWithState.data as InventarizedAndQRScannableModel,
                        existingQRCodeSticker
                    )
                    onAddObjectClicked(existingQRCodeSticker)
                } ?: afterUpdateAction()

                // Not need for update

                deviceState.value = deviceState.value.stateCopy(
                    deviceState.value.deviceState,
                    false
                )
                Log.i(LOG_TAG_NAMES.INFO_TAG, "UPDATE")

                qrCodeStickerInfo?.let {
                    sendUiEvent(UIAddNewObjectEvent.Navigate(context.resources.getString(R.string.menu_add_route)))
                }
                    ?: sendUiEvent(UIAddNewObjectEvent.Navigate(context.resources.getString(R.string.list_of_scanned_objects)))


            }
        }
    }

    protected abstract fun getEssentialNameForQRCodeSticker(): String

    private fun setQRStickerInfo(
        device: InventarizedAndQRScannableModel,
        qrCodeStickerInfo: QRCodeStickerInfo
    ) {
        device.let {
            qrCodeStickerInfo.qrCode = createQRCode(device)
            qrCodeStickerInfo.essentialName = getEssentialNameForQRCodeSticker()
            qrCodeStickerInfo.inventoryNumber = device.inventoryNumber
            qrCodeStickerInfo.databaseObjectTypes = device.getDatabaseTableName()
        }
    }

    protected fun <T> insertPossibleObjectsInListIfSuccess(
        statusWithState: Resource<List<T>>,
        listOfPossibleObjects: MutableList<T>
    ) {
        when (statusWithState) {
            is Resource.Error -> {
                sendUiEvent(
                    UIAddNewObjectEvent.ShowSnackBar(
                        statusWithState.message ?: "Unknown error"
                    )
                )
            }

            is Resource.Loading -> {}

            is Resource.Success -> {
                Log.i(LOG_TAG_NAMES.INFO_TAG, "Added list of possible objects")
                listOfPossibleObjects.clear()
                listOfPossibleObjects.addAll(statusWithState.data ?: emptyList())
                for (currentObj in listOfPossibleObjects) {
                    Log.i(LOG_TAG_NAMES.INFO_TAG, "Possible object: ${currentObj.toString()}")
                }
            }
        }
    }
}