package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.projector

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state.BaseDeviceState
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state.ProjectorUIState
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.core.remote_server.data.model.Projector
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.projector.AddProjectorUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.data.Converters
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AddProjectorViewModel @AssistedInject constructor(
    @Assisted private val userAndPlaceBundle: UserAndPlaceBundle,
    @ApplicationContext private val context: Context,
    converters: Converters,
    private val addProjectorUseCase: AddProjectorUseCase
) : BaseAddObjectViewModel(context, converters, userAndPlaceBundle) {


    private val _projectorState = mutableStateOf(
        Projector(
            cabinetId = userAndPlaceBundle.cabinet.id
        )
    ) //work with this here is more convenient

    private val _projectorStateWithLoadingStatus = mutableStateOf<BaseDeviceState<Projector>>(
        ProjectorUIState(
            _projectorState, false
        )
    )
    val projectorStateWithLoadingStatus: State<BaseDeviceState<Projector>> =
        _projectorStateWithLoadingStatus


    @AssistedFactory
    interface Factory {
        fun create(userAndPlaceBundle: UserAndPlaceBundle): AddProjectorViewModel
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

    override fun addObjectInDatabaseClicked(onAddObjectClicked: (QRCodeStickerInfo) -> Unit) {
        val qrCodeStickerInfo = QRCodeStickerInfo()
        val chair = _projectorState.value.toDTO()

        viewModelScope.launch {
            addProjectorUseCase(chair).onEach { statusWithState ->
                makeActionWithResourceResult(
                    statusWithState = statusWithState,
                    deviceState = _projectorStateWithLoadingStatus,
                    onAddObjectClicked,
                    qrCodeStickerInfo
                )
            }.launchIn(this)
        }
    }

    override fun isAllNeededFieldsInsertedCorrectly(): Boolean {
        return isAllNeededFieldsInsertedCorrectly(_projectorState.value)
    }

    override fun setNewImage(image: Bitmap?) {
        _projectorState.value = Projector(
            id = _projectorState.value.id,
            image = image,
            inventoryNumber = _projectorState.value.inventoryNumber,
            name = _projectorState.value.name,
            cabinetId = _projectorState.value.cabinetId
        )
    }

    override fun setNewName(name: String) {
        _projectorState.value = Projector(
            id = _projectorState.value.id,
            image = _projectorState.value.image,
            inventoryNumber = _projectorState.value.inventoryNumber,
            name = name,
            cabinetId = _projectorState.value.cabinetId
        )
    }

    override fun setNewInventoryNumber(invNumber: String) {
        _projectorState.value = Projector(
            id = _projectorState.value.id,
            image = _projectorState.value.image,
            inventoryNumber = invNumber,
            name = _projectorState.value.name,
            cabinetId = _projectorState.value.cabinetId
        )
    }

    override fun getEssentialNameForQRCodeSticker(): String {
        return _projectorState.value.name
    }

}