package com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.util.QRCodeGenerator
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.vm_view_communication.CreateQRCodeEvent
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.vm_view_communication.UICreateQRCodeEvent
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
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
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.person_qr_code)


        _listOfAddedQRCodes.addAll(
            listOf(
                QRCodeStickerInfo(
                    qrCode = QRCodeGenerator.encodeAsBitmap("{\"id\":0,\"tableName\":\"SomeTable\"}", 250, 250).asImageBitmap(),
                    essentialName = "Essential name for object 1HELLOWORLDpavelkilkov",
                    inventoryNumber = "000000227",
                    databaseObjectTypes = DatabaseObjectTypes.PERSON
                ),
                QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 2",
                    inventoryNumber = "000000222",
                    databaseObjectTypes = DatabaseObjectTypes.PERSON
                ),
                QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 3",
                    inventoryNumber = "000000223",
                    databaseObjectTypes = DatabaseObjectTypes.KEYBOARD
                ), QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 4",
                    inventoryNumber = "000000224",
                    databaseObjectTypes = DatabaseObjectTypes.MONITOR
                ),
                QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 5",
                    inventoryNumber = "000000225",
                    databaseObjectTypes = DatabaseObjectTypes.PERSON
                ), QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 6",
                    inventoryNumber = "000000226",
                    databaseObjectTypes = DatabaseObjectTypes.MONITOR
                ), QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 7",
                    inventoryNumber = "000000228",
                    databaseObjectTypes = DatabaseObjectTypes.PERSON
                ),
                QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 5",
                    inventoryNumber = "000000225",
                    databaseObjectTypes = DatabaseObjectTypes.KEYBOARD
                ), QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 6",
                    inventoryNumber = "000000226",
                    databaseObjectTypes = DatabaseObjectTypes.KEYBOARD
                ), QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 7",
                    inventoryNumber = "000000228",
                    databaseObjectTypes = DatabaseObjectTypes.MONITOR
                ),
                QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 5",
                    inventoryNumber = "000000225",
                    databaseObjectTypes = DatabaseObjectTypes.PERSON
                ), QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 6",
                    inventoryNumber = "000000226",
                    databaseObjectTypes = DatabaseObjectTypes.PERSON
                ), QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 7",
                    inventoryNumber = "000000228",
                    databaseObjectTypes = DatabaseObjectTypes.KEYBOARD
                ),
                QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 5",
                    inventoryNumber = "000000225",
                    databaseObjectTypes = DatabaseObjectTypes.KEYBOARD
                ), QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 6",
                    inventoryNumber = "000000226",
                    databaseObjectTypes = DatabaseObjectTypes.KEYBOARD
                ), QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 7",
                    inventoryNumber = "000000228",
                    databaseObjectTypes = DatabaseObjectTypes.KEYBOARD
                ),
                QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 5",
                    inventoryNumber = "000000225",
                    databaseObjectTypes = DatabaseObjectTypes.KEYBOARD
                ), QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 6",
                    inventoryNumber = "000000226",
                    databaseObjectTypes = DatabaseObjectTypes.KEYBOARD
                ), QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 7",
                    inventoryNumber = "000000228",
                    databaseObjectTypes = DatabaseObjectTypes.KEYBOARD
                ),
                QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 5",
                    inventoryNumber = "000000225",
                    databaseObjectTypes = DatabaseObjectTypes.PERSON
                ), QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 6",
                    inventoryNumber = "000000226",
                    databaseObjectTypes = DatabaseObjectTypes.PERSON
                ), QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 7",
                    inventoryNumber = "000000228",
                    databaseObjectTypes = DatabaseObjectTypes.PERSON
                ),
                QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 5",
                    inventoryNumber = "000000225",
                    databaseObjectTypes = DatabaseObjectTypes.PERSON
                ), QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 6",
                    inventoryNumber = "000000226",
                    databaseObjectTypes = DatabaseObjectTypes.PERSON
                ), QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 7",
                    inventoryNumber = "000000228",
                    databaseObjectTypes = DatabaseObjectTypes.PERSON
                ),
                QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 5",
                    inventoryNumber = "000000225",
                    databaseObjectTypes = DatabaseObjectTypes.KEYBOARD
                ), QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 6",
                    inventoryNumber = "000000226",
                    databaseObjectTypes = DatabaseObjectTypes.MONITOR
                ), QRCodeStickerInfo(
                    qrCode = bitmap.asImageBitmap(),
                    essentialName = "Essential name for object 7",
                    inventoryNumber = "000000228",
                    databaseObjectTypes = DatabaseObjectTypes.PERSON
                )
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
                Log.i(
                    LOG_TAG_NAMES.INFO_TAG, "Added new QRCode in list:" +
                            " ${event.qrCodeStickerInfo.essentialName}"
                )
                _listOfAddedQRCodes.add(event.qrCodeStickerInfo)
            }

            CreateQRCodeEvent.OnQRCodesListClicked -> {
                sendUiEvent(UICreateQRCodeEvent.Navigate(context.resources.getString(R.string.qr_codes_list)))
            }
        }
    }


    private fun sendUiEvent(event: UICreateQRCodeEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }


}