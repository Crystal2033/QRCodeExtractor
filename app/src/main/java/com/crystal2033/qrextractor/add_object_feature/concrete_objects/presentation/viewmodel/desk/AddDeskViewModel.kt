package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.desk

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state.BaseDeviceState
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state.DeskUIState
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.core.remote_server.data.model.Desk
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.desk.AddDeskUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.data.Converters
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AddDeskViewModel @AssistedInject constructor(
    @Assisted private val userAndPlaceBundle: UserAndPlaceBundle,
    @ApplicationContext private val context: Context,
    converters: Converters,
    private val addDeskUseCase: AddDeskUseCase
) : BaseAddObjectViewModel(context, converters, userAndPlaceBundle) {


    private val _deskState = mutableStateOf(
        Desk(
            cabinetId = userAndPlaceBundle.cabinet.id
        )
    ) //work with this here is more convenient

    private val _deskStateWithLoadingStatus = mutableStateOf<BaseDeviceState>(
        DeskUIState(
            _deskState, false
        )
    )
    val deskStateWithLoadingStatus: State<BaseDeviceState> = _deskStateWithLoadingStatus


    @AssistedFactory
    interface Factory {
        fun create(userAndPlaceBundle: UserAndPlaceBundle): AddDeskViewModel
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
        val deskDTO = _deskState.value.toDTO()

        viewModelScope.launch {
            addDeskUseCase(deskDTO).onEach { statusWithState ->
                makeActionWithResourceResult(
                    statusWithState = statusWithState,
                    deviceState = _deskStateWithLoadingStatus,
                    onAddObjectClicked,
                    QRCodeStickerInfo()
                )
            }.launchIn(this)
        }
    }

    override fun isAllNeededFieldsInsertedCorrectly(): Boolean {
        return isAllNeededFieldsInsertedCorrectly(_deskState.value)
    }

    override fun setNewImage(image: Bitmap?) {
        _deskState.value = Desk(
            id = _deskState.value.id,
            image = image,
            inventoryNumber = _deskState.value.inventoryNumber,
            name = _deskState.value.name,
            cabinetId = _deskState.value.cabinetId
        )
    }

    override fun setNewName(name: String) {
        _deskState.value = Desk(
            id = _deskState.value.id,
            image = _deskState.value.image,
            inventoryNumber = _deskState.value.inventoryNumber,
            name = name,
            cabinetId = _deskState.value.cabinetId
        )
    }

    override fun setNewInventoryNumber(invNumber: String) {
        _deskState.value = Desk(
            id = _deskState.value.id,
            image = _deskState.value.image,
            inventoryNumber = invNumber,
            name = _deskState.value.name,
            cabinetId = _deskState.value.cabinetId
        )
    }

    override fun getEssentialNameForQRCodeSticker(): String {
        return _deskState.value.name
    }

}