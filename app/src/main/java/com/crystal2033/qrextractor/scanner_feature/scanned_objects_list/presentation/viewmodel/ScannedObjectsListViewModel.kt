package com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.ScannedGroup
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.state.ObjectsListState
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.vm_view_communication.UIScannedObjectsListEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory.GetDataFromServerUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory.UseCaseGetObjectFromServerFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ScannedObjectsListViewModel @AssistedInject constructor(
    @Assisted private val scannedGroup: ScannedGroup,
    private val useCaseGetObjectFactory: UseCaseGetObjectFromServerFactory,
    @ApplicationContext private val context: Context
) : ViewModel() {

    init {
        loadDataFromRemoteServer()
    }

    private lateinit var getObjectInfoUseCase: GetDataFromServerUseCase

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

    private val _eventFlow = Channel<UIScannedObjectsListEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()


    //states
    private val _objectsListState = mutableStateOf(ObjectsListState())
    val objectsListState: State<ObjectsListState> = _objectsListState

    //states
    fun onEvent() {

    }


    private fun loadDataFromRemoteServer() {
        for (scannedObject in scannedGroup.listOfScannedObjects) {
            getObjectInfoUseCase = useCaseGetObjectFactory.createUseCase(scannedObject.tableName)

        }
    }

    private fun setObjectsListInState(data: Resource<List<QRScannableData>>) {
        when (data) {
            is Resource.Loading -> {
                setDataWithStatus(data, true)
            }

            is Resource.Error -> {
                setErrorStatusAndSendSnackbarEvent(data.message)
            }

            is Resource.Success -> {
                setDataWithStatus(data, false)
            }
        }
    }

    private fun setDataWithStatus(result: Resource<List<QRScannableData>>, isLoading: Boolean) {
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