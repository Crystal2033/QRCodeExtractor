package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.keyboard

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state.BaseDeviceState
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state.KeyboardUIState
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.data.model.Keyboard
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.keyboard.AddKeyboardUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.keyboard.UpdateKeyboardUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.data.Converters
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AddKeyboardViewModel @AssistedInject constructor(
    @Assisted private val userAndPlaceBundle: UserAndPlaceBundle,
    @ApplicationContext private val context: Context,
    converters: Converters,
    private val addKeyboardUseCase: AddKeyboardUseCase,
    @Assisted private val keyboardForUpdate: InventarizedAndQRScannableModel?,
    private val updateKeyboardUseCase: UpdateKeyboardUseCase
) : BaseAddObjectViewModel(context, converters, userAndPlaceBundle) {


    private val _keyboardState = mutableStateOf(
        keyboardForUpdate ?: Keyboard(
            cabinetId = userAndPlaceBundle.cabinet.id
        )
    ) //work with this here is more convenient

    private val _keyboardStateWithLoadingStatus = mutableStateOf<BaseDeviceState>(
        KeyboardUIState(
            _keyboardState, false
        )
    )
    val keyboardStateWithLoadingStatus: State<BaseDeviceState> =
        _keyboardStateWithLoadingStatus


    @AssistedFactory
    interface Factory {
        fun create(
            userAndPlaceBundle: UserAndPlaceBundle,
            deviceForUpdate: InventarizedAndQRScannableModel?
        ): AddKeyboardViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            userAndPlaceBundle: UserAndPlaceBundle,
            deviceForUpdate: InventarizedAndQRScannableModel?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(userAndPlaceBundle, deviceForUpdate) as T
            }
        }
    }

    override fun addObjectInDatabaseClicked(
        onAddObjectClicked: (QRCodeStickerInfo) -> Unit,
        afterUpdateAction: () -> Unit
    ) {
        val keyboardDTO = (_keyboardState.value as Keyboard).toDTO()

        viewModelScope.launch {
            keyboardForUpdate?.let {
                Log.i(LOG_TAG_NAMES.INFO_TAG, "UPDATE API")
                updateKeyboardUseCase(keyboardDTO).onEach { statusWithState ->
                    makeActionWithResourceResult(
                        statusWithState = statusWithState,
                        deviceState = _keyboardStateWithLoadingStatus,
                        afterUpdateAction = afterUpdateAction,
                    )
                }.launchIn(this)
            } ?: addKeyboardUseCase(keyboardDTO).onEach { statusWithState ->
                makeActionWithResourceResult(
                    statusWithState = statusWithState,
                    deviceState = _keyboardStateWithLoadingStatus,
                    onAddObjectClicked = onAddObjectClicked,
                    qrCodeStickerInfo = QRCodeStickerInfo()
                )
            }.launchIn(this)
        }
    }

    override fun isAllNeededFieldsInsertedCorrectly(): Boolean {
        return isAllNeededFieldsInsertedCorrectly(_keyboardState.value)
    }

    override fun setNewImage(image: Bitmap?) {
        image?.let {
            val rescaledImage = scaleImage(it)
            _keyboardState.value = Keyboard(
                id = _keyboardState.value.id,
                image = rescaledImage,
                inventoryNumber = _keyboardState.value.inventoryNumber,
                name = _keyboardState.value.name,
                cabinetId = _keyboardState.value.cabinetId
            )
        }
    }

    override fun setNewName(name: String) {
        _keyboardState.value = Keyboard(
            id = _keyboardState.value.id,
            image = _keyboardState.value.image,
            inventoryNumber = _keyboardState.value.inventoryNumber,
            name = name,
            cabinetId = _keyboardState.value.cabinetId
        )
    }

    override fun setNewCabinetId(cabinetId: Long) {
        _keyboardState.value = Keyboard(
            id = _keyboardState.value.id,
            image = _keyboardState.value.image,
            inventoryNumber = _keyboardState.value.inventoryNumber,
            name = _keyboardState.value.name,
            cabinetId = cabinetId
        )
    }

    override fun setNewInventoryNumber(invNumber: String) {
        _keyboardState.value = Keyboard(
            id = _keyboardState.value.id,
            image = _keyboardState.value.image,
            inventoryNumber = invNumber,
            name = _keyboardState.value.name,
            cabinetId = _keyboardState.value.cabinetId
        )
    }

    override fun getEssentialNameForQRCodeSticker(): String {
        return _keyboardState.value.name
    }

}