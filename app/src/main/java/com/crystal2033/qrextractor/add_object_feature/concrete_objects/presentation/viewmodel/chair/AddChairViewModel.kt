package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.chair

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state.BaseDeviceState
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state.ChairUIState
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.camera_for_photos.ImageConstants
import com.crystal2033.qrextractor.core.remote_server.data.model.Chair
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.chair.AddChairUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.chair.UpdateChairUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.data.Converters
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


class AddChairViewModel @AssistedInject constructor(
    @Assisted private val userAndPlaceBundle: UserAndPlaceBundle,
    @ApplicationContext private val context: Context,
    converters: Converters,
    private val addChairUseCase: AddChairUseCase,
    @Assisted private val chairForUpdate: InventarizedAndQRScannableModel?,
    private val updateChairUseCase: UpdateChairUseCase,
) : BaseAddObjectViewModel(context, converters, userAndPlaceBundle) {


    private val _chairState = mutableStateOf(
        chairForUpdate ?: Chair(
            cabinetId = userAndPlaceBundle.cabinet.id
        )
    ) //work with this here is more convenient

    private val _chairStateWithLoadingStatus = mutableStateOf<BaseDeviceState>(
        ChairUIState(
            _chairState, false
        )
    )
    val chairStateWithLoadingStatus: State<BaseDeviceState> = _chairStateWithLoadingStatus


    @AssistedFactory
    interface Factory {
        fun create(
            userAndPlaceBundle: UserAndPlaceBundle,
            deviceForUpdate: InventarizedAndQRScannableModel?
        ): AddChairViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            userAndPlaceBundle: UserAndPlaceBundle,
            chairForUpdate: InventarizedAndQRScannableModel?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(userAndPlaceBundle, chairForUpdate) as T
            }
        }
    }


    override fun addObjectInDatabaseClicked(
        onAddObjectClicked: (QRCodeStickerInfo) -> Unit,
        afterUpdateAction: () -> Unit
    ) {
        val chairDTO = (_chairState.value as Chair).toDTO()

        viewModelScope.launch {
            chairForUpdate?.let {
                updateChairUseCase(chairDTO).onEach { statusWithState ->
                    makeActionWithResourceResult(
                        statusWithState = statusWithState,
                        deviceState = _chairStateWithLoadingStatus,
                        afterUpdateAction = afterUpdateAction,
                    )
                }.launchIn(this)
            } ?: addChairUseCase(chairDTO).onEach { statusWithState ->
                makeActionWithResourceResult(
                    statusWithState = statusWithState,
                    deviceState = _chairStateWithLoadingStatus,
                    onAddObjectClicked = onAddObjectClicked,
                    qrCodeStickerInfo = QRCodeStickerInfo()
                )
            }.launchIn(this)
        }
    }

    override fun isAllNeededFieldsInsertedCorrectly(): Boolean {
        return isAllNeededFieldsInsertedCorrectly(_chairState.value)
    }

    override fun setNewImage(image: Bitmap?) {
        image?.let {
            val rescaledImage = scaleImage(it)
            _chairState.value = Chair(
                id = _chairState.value.id,
                image = rescaledImage,
                inventoryNumber = _chairState.value.inventoryNumber,
                name = _chairState.value.name,
                cabinetId = _chairState.value.cabinetId
            )
        }
    }

    override fun setNewName(name: String) {
        _chairState.value = Chair(
            id = _chairState.value.id,
            image = _chairState.value.image,
            inventoryNumber = _chairState.value.inventoryNumber,
            name = name,
            cabinetId = _chairState.value.cabinetId
        )
    }

    override fun setNewCabinetId(cabinetId: Long) {
        _chairState.value = Chair(
            id = _chairState.value.id,
            image = _chairState.value.image,
            inventoryNumber = _chairState.value.inventoryNumber,
            name = _chairState.value.name,
            cabinetId = cabinetId
        )
    }

    override fun setNewInventoryNumber(invNumber: String) {
        _chairState.value = Chair(
            id = _chairState.value.id,
            image = _chairState.value.image,
            inventoryNumber = invNumber,
            name = _chairState.value.name,
            cabinetId = _chairState.value.cabinetId
        )
    }

    override fun getEssentialNameForQRCodeSticker(): String {
        return _chairState.value.name
    }

}