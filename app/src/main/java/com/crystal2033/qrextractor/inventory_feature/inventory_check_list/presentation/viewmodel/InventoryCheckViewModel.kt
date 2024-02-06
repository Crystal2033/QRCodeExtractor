package com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetDeviceUseCaseInvoker
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.inventarization.InventarizedINV_1FileParser
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.inventarization.ObjectInInventarizedFile
import com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation.ObjectsToCheckState
import com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation.vm_view_communication.InventoryCheckEvent
import com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation.vm_view_communication.UIInventoryCheckEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.data.Converters
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.DeviceInfoInQRCodeRepresenter
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory.GetObjectFromServerUseCaseFactory
import com.google.gson.JsonSyntaxException
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class InventoryCheckViewModel @AssistedInject constructor(
    private val converter: Converters,
    private val useCaseGetQRCodeFactory: GetObjectFromServerUseCaseFactory,
    @Assisted private val inventoryFile: InventarizedINV_1FileParser //TODO: Add interface
) : ViewModel() {

    private val _eventFlow = Channel<UIInventoryCheckEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private val _objectsInfoState = mutableStateOf(ObjectsToCheckState())
    val objectsInfoState: State<ObjectsToCheckState> = _objectsInfoState

    //private val _listOfObjectToCheck = mutableStateListOf<ObjectInInventarizedFile>()
    //val listOfObjectToCheck: SnapshotStateList<ObjectInInventarizedFile> = _listOfObjectToCheck

    private var deleteDuplicateQRCodeStringJob: Job? = null

    private var prevScanString: String? = null
    //private var scanJob: Job? = null

    private lateinit var getDataFromQRCodeUseCase: GetDeviceUseCaseInvoker

    //private val _isLoadingData = mutableStateOf(false)
    //val isLoadingData: State<Boolean> = _isLoadingData

    init {
        inventoryFile.listOfObjects.forEach(_objectsInfoState.value.listOfObjects::add)
        Log.i("INIT", "INITTTTT")
    }

    private val alreadyUsedInvNumbers = hashSetOf<String>()

    override fun onCleared() {
        super.onCleared()
        Log.i(LOG_TAG_NAMES.INFO_TAG, "Cleared InventoryCheckViewModel")
    }

    fun onEvent(event: InventoryCheckEvent) {
        when (event) {
            is InventoryCheckEvent.OnScanQRCode -> {
                onScanQRCode(event.scannedString)
            }
        }
    }

    private fun onScanQRCode(scanResult: String) {
        if (scanResult.isBlank() || prevScanString == scanResult) {
            return
        }
        setDeduplicateStringAndDelayForClear(scanResult)
        //prevScanString = scanResult
        try {
            val scannedObject = converter.fromJsonToScannedTableNameAndId(scanResult)
            scannedObject?.let {
                if (isAlreadyCheckedInventoryNumber(it.invNumber)) {
                    sendUiEvent(UIInventoryCheckEvent.ShowSnackBar("Inventory number ${it.invNumber} already checked."))
                    return
                }
                //scanJob?.cancel()
                setCheckedInventoryIfExists(scannedObject)
            }
        } catch (e: JsonSyntaxException) {
            setDataInState(objectsInfoState.value.listOfObjects, false)
            showQRCodeFormatError(e)
        }
    }

    private fun showQRCodeFormatError(e: JsonSyntaxException) {
        Log.e(LOG_TAG_NAMES.ERROR_TAG, e.message ?: "Unknown")
        viewModelScope.launch {
            sendUiEvent(UIInventoryCheckEvent.ShowSnackBar("QR-code`s content is not compatible with this application."))
            //setPreviewObjectStateInfo(Resource.Error(message = "QR-code`s content is not compatible with this application."))
        }
    }

    private fun isAlreadyCheckedInventoryNumber(invNumber: String): Boolean {
        return alreadyUsedInvNumbers.contains(invNumber)
    }

    private fun setDeduplicateStringAndDelayForClear(scanResult: String) {
        prevScanString = scanResult
        deleteDuplicateQRCodeStringJob?.cancel()
        deleteDuplicateQRCodeStringJob = CoroutineScope(Dispatchers.Default).launch {
            delay(timeForDuplicateQRCodesResistInMs)
            prevScanString = ""
        }
    }

    private fun findObjectByPredicateInList(
        predicate: (ObjectInInventarizedFile) -> Boolean
    ): ObjectInInventarizedFile? {
        return _objectsInfoState.value.listOfObjects.find { predicate(it) }
    }

    private fun tryToFindObjectByNameFromServerAndMakeActionIfSuccess(
        scannedObject: DeviceInfoInQRCodeRepresenter,
        coroutineScope: CoroutineScope,
        actionAfterSuccessFound: (InventarizedAndQRScannableModel) -> Unit
    ) {
        try {
            getDataFromQRCodeUseCase =
                useCaseGetQRCodeFactory.createUseCase(scannedObject.tableName)
        } catch (error: ClassNotFoundException) {
            Log.e(LOG_TAG_NAMES.ERROR_TAG, error.message ?: "Unknown error")
            val errorMsg = error.message ?: "Unknown error"
            sendUiEvent(UIInventoryCheckEvent.ShowSnackBar(errorMsg))
        }

        getDataFromQRCodeUseCase(scannedObject.id).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    setDataInState(objectsInfoState.value.listOfObjects, false)
                    sendUiEvent(
                        UIInventoryCheckEvent.ShowSnackBar(
                            result.message ?: "Error from server"
                        )
                    )
                }

                is Resource.Loading -> {
                    setDataInState(objectsInfoState.value.listOfObjects, true)
                }

                is Resource.Success -> {
                    setDataInState(objectsInfoState.value.listOfObjects, false)
                    actionAfterSuccessFound(result.data!!)
                }
            }
        }.launchIn(coroutineScope)
    }

    private fun setDataInState(
        listOfObjects: MutableList<ObjectInInventarizedFile>,
        isLoading: Boolean
    ) {
        _objectsInfoState.value = objectsInfoState.value.copy(
            listOfObjects = listOfObjects,
            isLoading = isLoading
        )
    }

    private fun incrementFactQuantity(checkingObjectFromList: ObjectInInventarizedFile) {
        val savedList = _objectsInfoState.value.listOfObjects.toMutableList()
        checkingObjectFromList.incrementFactQuantity()
        setDataInState(arrayListOf(), objectsInfoState.value.isLoading)
        setDataInState(savedList, false) //not loading already
    }

    private fun tryToIncrementQuantityOrAction(
        checkingObjectFromList: ObjectInInventarizedFile?,
        actionOnNull: () -> Unit
    ) {
        if (checkingObjectFromList != null) {
            incrementFactQuantity(checkingObjectFromList)
            alreadyUsedInvNumbers.add(checkingObjectFromList.invNumber)
        } else {
            actionOnNull()
        }
    }

    private fun setCheckedInventoryIfExists(scannedObject: DeviceInfoInQRCodeRepresenter?) {

        scannedObject?.let { scannedObj ->
            setDataInState(objectsInfoState.value.listOfObjects, true)
            viewModelScope.launch {
                var checkingObjectFromList = findObjectByPredicateInList {
                    it.invNumber == scannedObj.invNumber
                }

                tryToIncrementQuantityOrAction(checkingObjectFromList) {
                    tryToFindObjectByNameFromServerAndMakeActionIfSuccess(
                        scannedObj,
                        this
                    ) { inventarizedFromServer ->
                        checkingObjectFromList =
                            findObjectByPredicateInList { inventarizedFromServer.name == it.objectName }
                        tryToIncrementQuantityOrAction(checkingObjectFromList) {
                            sendUiEvent(
                                UIInventoryCheckEvent.ShowSnackBar(
                                    "Object with inventory number: ${scannedObject.invNumber} not found."
                                )
                            )
                        }
                    }
                }
            }
        } ?: Log.e(LOG_TAG_NAMES.ERROR_TAG, "Error with scannedObject convertion. No id there")

    }

    @AssistedFactory
    interface Factory {
        fun create(inventoryFile: InventarizedINV_1FileParser): InventoryCheckViewModel
    }

    companion object {
        const val timeForDuplicateQRCodesResistInMs = 8000L

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