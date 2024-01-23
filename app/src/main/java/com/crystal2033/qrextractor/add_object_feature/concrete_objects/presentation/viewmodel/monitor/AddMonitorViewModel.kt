package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.monitor

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state.BaseDeviceState
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state.MonitorUIState
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.core.remote_server.data.model.Monitor
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.monitor.AddMonitorUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.data.Converters
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AddMonitorViewModel @AssistedInject constructor(
    @Assisted private val userAndPlaceBundle: UserAndPlaceBundle,
    @ApplicationContext private val context: Context,
    converters: Converters,
    private val addMonitorUseCase: AddMonitorUseCase
) : BaseAddObjectViewModel(context, converters, userAndPlaceBundle) {


    private val _monitorState = mutableStateOf(
        Monitor(
            cabinetId = userAndPlaceBundle.cabinet.id
        )
    ) //work with this here is more convenient

    private val _monitorStateWithLoadingStatus = mutableStateOf<BaseDeviceState<Monitor>>(
        MonitorUIState(
            _monitorState, false
        )
    )
    val monitorStateWithLoadingStatus: State<BaseDeviceState<Monitor>> =
        _monitorStateWithLoadingStatus


    @AssistedFactory
    interface Factory {
        fun create(userAndPlaceBundle: UserAndPlaceBundle): AddMonitorViewModel
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
        val monitorDTO = _monitorState.value.toDTO()

        viewModelScope.launch {
            addMonitorUseCase(monitorDTO).onEach { statusWithState ->
                makeActionWithResourceResult(
                    statusWithState = statusWithState,
                    deviceState = _monitorStateWithLoadingStatus,
                    onAddObjectClicked,
                    QRCodeStickerInfo()
                )
            }.launchIn(this)
        }
    }

    override fun isAllNeededFieldsInsertedCorrectly(): Boolean {
        return isAllNeededFieldsInsertedCorrectly(_monitorState.value)
    }

    override fun setNewImage(image: Bitmap?) {
        _monitorState.value = Monitor(
            id = _monitorState.value.id,
            image = image,
            inventoryNumber = _monitorState.value.inventoryNumber,
            name = _monitorState.value.name,
            cabinetId = _monitorState.value.cabinetId
        )
    }

    override fun setNewName(name: String) {
        _monitorState.value = Monitor(
            id = _monitorState.value.id,
            image = _monitorState.value.image,
            inventoryNumber = _monitorState.value.inventoryNumber,
            name = name,
            cabinetId = _monitorState.value.cabinetId
        )
    }

    override fun setNewInventoryNumber(invNumber: String) {
        _monitorState.value = Monitor(
            id = _monitorState.value.id,
            image = _monitorState.value.image,
            inventoryNumber = invNumber,
            name = _monitorState.value.name,
            cabinetId = _monitorState.value.cabinetId
        )
    }

    override fun getEssentialNameForQRCodeSticker(): String {
        return _monitorState.value.name
    }

}