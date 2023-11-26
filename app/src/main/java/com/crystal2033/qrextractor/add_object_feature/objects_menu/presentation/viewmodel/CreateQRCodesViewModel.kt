package com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.vm_view_communication.CreateQRCodeEvent
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.vm_view_communication.UICreateQRCodeEvent
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.model.User
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CreateQRCodesViewModel @AssistedInject constructor(
    @Assisted private val user: User,
    @ApplicationContext private val context: Context

) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(user: User?): CreateQRCodesViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            user: User?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(user) as T
            }
        }
    }

    //states
    private val _menuListState = mutableStateListOf<DatabaseObjectTypes>()
    val menuListState: SnapshotStateList<DatabaseObjectTypes> = _menuListState

    private val _listOfAddedQRCodes = mutableStateListOf<QRCodeStickerInfo>()
    val listOfAddedQRCodes: SnapshotStateList<QRCodeStickerInfo> = _listOfAddedQRCodes

    private val _chosenObjectClassState = mutableStateOf(DatabaseObjectTypes.PERSON)
    val chosenObjectClassState: State<DatabaseObjectTypes> = _chosenObjectClassState
    //states


    private val _eventFlow = Channel<UICreateQRCodeEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    init {
        _menuListState.addAll(
            listOf(
                DatabaseObjectTypes.PERSON,
                DatabaseObjectTypes.KEYBOARD
            )
        )
    }

    fun onEvent(event: CreateQRCodeEvent) {
        when (event) {
            is CreateQRCodeEvent.SetChosenObjectClass -> {
                _chosenObjectClassState.value = event.objectType
                sendUiEvent(UICreateQRCodeEvent.Navigate(context.getString(R.string.add_concrete_class)))
            }

            is CreateQRCodeEvent.OnAddNewObjectInList -> {
                _listOfAddedQRCodes.add(event.qrCodeStickerInfo)
            }
        }
    }


    private fun sendUiEvent(event: UICreateQRCodeEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }


}