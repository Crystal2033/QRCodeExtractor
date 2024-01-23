package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.chair

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication.chair.AddChairEvent
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.core.remote_server.data.model.Chair
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedModel
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.chair.AddChairUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.data.Converters
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AddChairViewModel @AssistedInject constructor(
    @Assisted private val userAndPlaceBundle: UserAndPlaceBundle,
    @ApplicationContext private val context: Context,
    converters: Converters,
    private val addChairUseCase: AddChairUseCase
) : BaseAddObjectViewModel(context, converters) {


    private val _chairState = mutableStateOf(Chair())
    val chairState: State<Chair> = _chairState

    private val _userAndPlaceBundleState = mutableStateOf(userAndPlaceBundle)
    val userAndPlaceBundleState: State<UserAndPlaceBundle> = _userAndPlaceBundleState


    init {
        _chairState.value.cabinetId = userAndPlaceBundle.cabinet.id
    }

    @AssistedFactory
    interface Factory {
        fun create(userAndPlaceBundle: UserAndPlaceBundle): AddChairViewModel
    }

    fun onEvent(event: AddChairEvent) {
        when (event) {
            is AddChairEvent.OnImageChanged -> {
                _chairState.value = Chair(
                    id = _chairState.value.id,
                    image = event.image,
                    inventoryNumber = _chairState.value.inventoryNumber,
                    name = _chairState.value.name,
                    cabinetId = _chairState.value.cabinetId
                )
            }

            is AddChairEvent.OnInventoryNumberChanged -> {
                _chairState.value = Chair(
                    id = _chairState.value.id,
                    image = _chairState.value.image,
                    inventoryNumber = event.inventoryNumber,
                    name = _chairState.value.name,
                    cabinetId = _chairState.value.cabinetId
                )
            }

            is AddChairEvent.OnNameChanged -> {
                _chairState.value = Chair(
                    id = _chairState.value.id,
                    image = _chairState.value.image,
                    inventoryNumber = _chairState.value.inventoryNumber,
                    name = event.name,
                    cabinetId = _chairState.value.cabinetId
                )
            }
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            userAndPlaceBundle: UserAndPlaceBundle
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(userAndPlaceBundle) as T
            }
        }
    }

    override fun setQRStickerInfo(
        device: InventarizedModel?,
        qrCodeStickerInfo: QRCodeStickerInfo
    ) {
        device as Chair
        device.let {
            qrCodeStickerInfo.qrCode = createQRCode(device)
            qrCodeStickerInfo.essentialName = device.name
            qrCodeStickerInfo.inventoryNumber = device.inventoryNumber
            qrCodeStickerInfo.databaseObjectTypes = device.getDatabaseTableName()
        }
    }

    fun isAllFieldInsertedCorrectly(): Boolean {
        return _chairState.value.image != null &&
                _chairState.value.name.isNotBlank() &&
                _chairState.value.cabinetId != 0L &&
                _chairState.value.inventoryNumber.isNotBlank()
    }

    override fun addObjectInDatabaseClicked(onAddObjectClicked: (QRCodeStickerInfo) -> Unit) {
        val qrCodeStickerInfo = QRCodeStickerInfo()
        val chair = chairState.value.toDTO()

        viewModelScope.launch {
            addChairUseCase(chair).onEach { statusWithState ->
                makeActionWithResourceResult(
                    statusWithState = statusWithState,
                    deviceState = _chairState,
                    onAddObjectClicked,
                    qrCodeStickerInfo
                )
            }.launchIn(this)
        }
    }

}