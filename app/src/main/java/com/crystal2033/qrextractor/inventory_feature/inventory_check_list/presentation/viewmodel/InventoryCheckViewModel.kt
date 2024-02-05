package com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.inventarization.InventarizedINV_1FileParser
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.inventarization.ObjectInInventarizedFile
import com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation.vm_view_communication.InventoryCheckEvent
import com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation.vm_view_communication.UIInventoryCheckEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory.GetObjectFromServerUseCaseFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class InventoryCheckViewModel @AssistedInject constructor(
    private val useCaseGetQRCodeFactory: GetObjectFromServerUseCaseFactory,
    @Assisted private val inventoryFile: InventarizedINV_1FileParser //TODO: Add interface
) : ViewModel() {

    private val _eventFlow = Channel<UIInventoryCheckEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private val _listOfObjectToCheck = mutableStateListOf<ObjectInInventarizedFile>()

    val listOfObjectToCheck: SnapshotStateList<ObjectInInventarizedFile> = _listOfObjectToCheck

    init {
        inventoryFile.listOfObjects.forEach(_listOfObjectToCheck::add)
        Log.i("INIT", "INITTTTT")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(LOG_TAG_NAMES.INFO_TAG, "Cleared InventoryCheckViewModel")
    }

    fun onEvent(event: InventoryCheckEvent) {
        when (event) {
            is InventoryCheckEvent.OnScanQRCode -> {

            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(inventoryFile: InventarizedINV_1FileParser): InventoryCheckViewModel
    }

    companion object {
        //const val timeForDuplicateQRCodesResistInMs = 8000L

        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            inventoryFile: InventarizedINV_1FileParser
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(inventoryFile) as T
            }
        }
    }


    private fun sendUiEvent(event: UIInventoryCheckEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }
}