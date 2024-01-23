package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.chair

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state.BaseDeviceState
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state.ChairUIState
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.core.remote_server.data.model.Chair
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
) : BaseAddObjectViewModel(context, converters, userAndPlaceBundle) {


    private val _chairState = mutableStateOf(
        Chair(
            cabinetId = userAndPlaceBundle.cabinet.id
        )
    ) //work with this here is more convenient

    private val _chairStateWithLoadingStatus = mutableStateOf<BaseDeviceState<Chair>>(
        ChairUIState(
            _chairState, false
        )
    )
    val chairStateWithLoadingStatus: State<BaseDeviceState<Chair>> = _chairStateWithLoadingStatus


    @AssistedFactory
    interface Factory {
        fun create(userAndPlaceBundle: UserAndPlaceBundle): AddChairViewModel
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
        val chair = _chairState.value.toDTO()

        viewModelScope.launch {
            addChairUseCase(chair).onEach { statusWithState ->
                makeActionWithResourceResult(
                    statusWithState = statusWithState,
                    deviceState = _chairStateWithLoadingStatus,
                    onAddObjectClicked,
                    QRCodeStickerInfo()
                )
            }.launchIn(this)
        }
    }

    override fun isAllNeededFieldsInsertedCorrectly(): Boolean {
        return isAllNeededFieldsInsertedCorrectly(_chairState.value)
    }

    override fun setNewImage(image: Bitmap?) {
        _chairState.value = Chair(
            id = _chairState.value.id,
            image = image,
            inventoryNumber = _chairState.value.inventoryNumber,
            name = _chairState.value.name,
            cabinetId = _chairState.value.cabinetId
        )
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