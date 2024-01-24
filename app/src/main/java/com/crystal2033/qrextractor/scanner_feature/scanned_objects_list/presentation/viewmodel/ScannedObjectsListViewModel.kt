package com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.viewmodel

import android.content.Context
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
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.ScannedGroup
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.state.ObjectsListState
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.vm_view_communication.ScannedObjectsListEvent
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.vm_view_communication.UIScannedObjectsListEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.Unknown
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory.GetScannableDataFromServerUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory.UseCaseGetObjectFromServerFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ScannedObjectsListViewModel @AssistedInject constructor(
    @Assisted private val scannedGroup: ScannedGroup,
    private val useCaseGetObjectFactory: UseCaseGetObjectFromServerFactory,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _eventFlow = Channel<UIScannedObjectsListEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()


    //states
    private val _objectsListState = mutableStateOf(ObjectsListState())
    val objectsListState: State<ObjectsListState> = _objectsListState
    //states

    init {
        Log.i(LOG_TAG_NAMES.INFO_TAG, "load data from the server")
        _objectsListState.value.listOfObjects?.clear()
        viewModelScope.launch {
            setObjectsListInState(Resource.Loading(arrayListOf()))
            loadDataFromRemoteServer().join()
            setObjectsListInState(Resource.Success(_objectsListState.value.listOfObjects))
        }

    }

    override fun onCleared() {
        super.onCleared()
        Log.i(LOG_TAG_NAMES.INFO_TAG, "CLEARED")
    }

    private lateinit var getObjectInfoUseCase: GetDeviceUseCaseInvoker

    @AssistedFactory
    interface Factory {
        fun create(scannedGroup: ScannedGroup?): ScannedObjectsListViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            scannedGroup: ScannedGroup?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(scannedGroup) as T
            }
        }
    }


    fun onEvent(event: ScannedObjectsListEvent) {
        when (event) {
            is ScannedObjectsListEvent.OnScannedObjectClicked -> {
                Log.i(
                    LOG_TAG_NAMES.INFO_TAG,
                    "Clicked on ${event.scannedObject.javaClass.simpleName} with id " +
                            "${event.scannedObject.getDatabaseID()}"
                )
            }
        }

    }


    private fun loadDataFromRemoteServer(): Job {
        return viewModelScope.launch {
            for (scannedObject in scannedGroup.listOfScannedObjects) {
                Log.i(LOG_TAG_NAMES.INFO_TAG, "ID=${scannedObject.id}")
                getObjectInfoUseCase =
                    useCaseGetObjectFactory.createUseCase(scannedObject.tableName)

                getObjectInfoUseCase(scannedObject.id).onEach { resultData ->
                    addResultInList(resultData, scannedObject.id)
                }.launchIn(this)
                //TODO: add catching errors
            }
        }


    }

    private fun addResultInList(objectGetResult: Resource<InventarizedAndQRScannableModel>, id: Long) {
        when (objectGetResult) {
            is Resource.Error -> {
                Log.i(LOG_TAG_NAMES.INFO_TAG, "ERROR WITH ID: $id")
            }

            is Resource.Loading -> {
                Log.i(LOG_TAG_NAMES.INFO_TAG, "LOADING WITH ID: $id")
            }

            is Resource.Success -> {
                Log.i(LOG_TAG_NAMES.INFO_TAG, "SUCCESS ID: $id")
                _objectsListState.value.listOfObjects?.add(
                    objectGetResult.data ?: Unknown("Not found")
                )
            }
        }
    }

    private fun setObjectsListInState(data: Resource<MutableList<QRScannableData>>) {
        when (data) {
            is Resource.Loading -> {
                setDataWithStatus(data, true)
            }

            is Resource.Error -> {
                setErrorStatusAndSendSnackbarEvent(data.message)
            }

            is Resource.Success -> {
                Log.i(LOG_TAG_NAMES.INFO_TAG, "Got values: ${data.data?.size}")
                setDataWithStatus(data, false)
            }
        }
    }

    private fun setDataWithStatus(
        result: Resource<MutableList<QRScannableData>>,
        isLoading: Boolean
    ) {
        _objectsListState.value = objectsListState.value.copy(
            listOfObjects = result.data,
            isLoading = isLoading
        )
    }

    private fun setErrorStatusAndSendSnackbarEvent(errorMessage: String?) {
        _objectsListState.value = objectsListState.value.copy(
            listOfObjects = null,
            isLoading = false
        )
        sendUiEvent(
            UIScannedObjectsListEvent.ShowSnackBar(
                message = errorMessage ?: "Unknown error"
            )
        )
    }


    private fun sendUiEvent(event: UIScannedObjectsListEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }
}