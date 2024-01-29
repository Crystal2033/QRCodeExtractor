package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.system_unit

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state.BaseDeviceState
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state.SystemUnitUIState
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.data.model.SystemUnit
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.system_unit.AddSystemUnitUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.system_unit.UpdateSystemUnitUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.data.Converters
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AddSystemUnitViewModel @AssistedInject constructor(
    @Assisted private val userAndPlaceBundle: UserAndPlaceBundle,
    @ApplicationContext private val context: Context,
    converters: Converters,
    private val addSystemUnitUseCase: AddSystemUnitUseCase,
    @Assisted private val systemUnitForUpdate: InventarizedAndQRScannableModel?,
    private val updateSystemUnitUseCase: UpdateSystemUnitUseCase
) : BaseAddObjectViewModel(context, converters, userAndPlaceBundle) {


    private val _systemUnitState = mutableStateOf(
        systemUnitForUpdate ?: SystemUnit(
            cabinetId = userAndPlaceBundle.cabinet.id
        )
    ) //work with this here is more convenient

    private val _systemUnitStateWithLoadingStatus = mutableStateOf<BaseDeviceState>(
        SystemUnitUIState(
            _systemUnitState, false
        )
    )
    val systemUnitStateWithLoadingStatus: State<BaseDeviceState> =
        _systemUnitStateWithLoadingStatus


    @AssistedFactory
    interface Factory {
        fun create(
            userAndPlaceBundle: UserAndPlaceBundle,
            deviceForUpdate: InventarizedAndQRScannableModel?
        ): AddSystemUnitViewModel
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
        val systemUnitDTO = (_systemUnitState.value as SystemUnit).toDTO()

        viewModelScope.launch {
            systemUnitForUpdate?.let {
                Log.i(LOG_TAG_NAMES.INFO_TAG, "UPDATE API")
                updateSystemUnitUseCase(systemUnitDTO).onEach { statusWithState ->
                    makeActionWithResourceResult(
                        statusWithState = statusWithState,
                        deviceState = _systemUnitStateWithLoadingStatus,
                        afterUpdateAction = afterUpdateAction,
                    )
                }.launchIn(this)
            } ?: addSystemUnitUseCase(systemUnitDTO).onEach { statusWithState ->
                makeActionWithResourceResult(
                    statusWithState = statusWithState,
                    deviceState = _systemUnitStateWithLoadingStatus,
                    onAddObjectClicked = onAddObjectClicked,
                    qrCodeStickerInfo = QRCodeStickerInfo()
                )
            }.launchIn(this)
        }
    }

    override fun isAllNeededFieldsInsertedCorrectly(): Boolean {
        return isAllNeededFieldsInsertedCorrectly(_systemUnitState.value)
    }

    override fun setNewImage(image: Bitmap?) {
        _systemUnitState.value = SystemUnit(
            id = _systemUnitState.value.id,
            image = image,
            inventoryNumber = _systemUnitState.value.inventoryNumber,
            name = _systemUnitState.value.name,
            cabinetId = _systemUnitState.value.cabinetId
        )
    }

    override fun setNewName(name: String) {
        _systemUnitState.value = SystemUnit(
            id = _systemUnitState.value.id,
            image = _systemUnitState.value.image,
            inventoryNumber = _systemUnitState.value.inventoryNumber,
            name = name,
            cabinetId = _systemUnitState.value.cabinetId
        )
    }

    override fun setNewCabinetId(cabinetId: Long) {
        _systemUnitState.value = SystemUnit(
            id = _systemUnitState.value.id,
            image = _systemUnitState.value.image,
            inventoryNumber = _systemUnitState.value.inventoryNumber,
            name = _systemUnitState.value.name,
            cabinetId = cabinetId
        )
    }

    override fun setNewInventoryNumber(invNumber: String) {
        _systemUnitState.value = SystemUnit(
            id = _systemUnitState.value.id,
            image = _systemUnitState.value.image,
            inventoryNumber = invNumber,
            name = _systemUnitState.value.name,
            cabinetId = _systemUnitState.value.cabinetId
        )
    }

    override fun getEssentialNameForQRCodeSticker(): String {
        return _systemUnitState.value.name
    }

}