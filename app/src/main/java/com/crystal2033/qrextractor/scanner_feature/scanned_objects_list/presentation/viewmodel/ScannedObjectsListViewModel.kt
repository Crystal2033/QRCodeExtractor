package com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.remote_server.data.model.Building
import com.crystal2033.qrextractor.core.remote_server.data.model.Cabinet
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.data.model.Organization
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetDeviceUseCaseInvoker
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetPlaceUseCases
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.ScannedGroup
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.state.ObjectsListState
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.vm_view_communication.ScannedObjectsListEvent
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.vm_view_communication.UIScannedObjectsListEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.Unknown
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory.UseCaseGetObjectFromServerFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ScannedObjectsListViewModel @AssistedInject constructor(
    @Assisted private val scannedGroup: ScannedGroup,
    @Assisted private val userAndPlaceBundle: UserAndPlaceBundle,
    private val useCaseGetObjectFactory: UseCaseGetObjectFromServerFactory,
    @ApplicationContext private val context: Context,
    private val getPlaceUseCases: GetPlaceUseCases
    //TODO: Add delete use case?
) : ViewModel() {

    private val _eventFlow = Channel<UIScannedObjectsListEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    //states
    private val _objectsListState = mutableStateOf(ObjectsListState())
    val objectsListState: State<ObjectsListState> = _objectsListState

    private val _chosenObjectClassState = mutableStateOf(DatabaseObjectTypes.UNKNOWN)
    val chosenObjectClassState: State<DatabaseObjectTypes> = _chosenObjectClassState

    private val _chosenDeviceState = mutableStateOf<InventarizedAndQRScannableModel?>(null)
    val chosenDeviceState: State<InventarizedAndQRScannableModel?> = _chosenDeviceState

    private val _userAndPlaceBundle = mutableStateOf(userAndPlaceBundle)
    val userAndPlaceBundleState: State<UserAndPlaceBundle> = _userAndPlaceBundle


    //states

    init {
        Log.i(LOG_TAG_NAMES.INFO_TAG, "load data from the server")
        refresh()
    }

    private fun refresh() {
        _objectsListState.value = objectsListState.value.copy(
            listOfObjects = arrayListOf(),
            isLoading = objectsListState.value.isLoading
        )
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
        fun create(
            scannedGroup: ScannedGroup?,
            userAndPlaceBundle: UserAndPlaceBundle
        ): ScannedObjectsListViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            scannedGroup: ScannedGroup?,
            userAndPlaceBundle: UserAndPlaceBundle
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(scannedGroup, userAndPlaceBundle) as T
            }
        }
    }


    fun onEvent(event: ScannedObjectsListEvent) {
        when (event) {
            is ScannedObjectsListEvent.OnScannedObjectClicked -> {
                _chosenDeviceState.value = event.scannedObject
                _chosenObjectClassState.value = event.scannedObject.getDatabaseTableName()
                Log.i(
                    LOG_TAG_NAMES.INFO_TAG,
                    "Clicked on ${event.scannedObject.javaClass.simpleName} with id " +
                            "${event.scannedObject.getDatabaseID()}"
                )
                CoroutineScope(Dispatchers.Default).launch {
                    Log.i(LOG_TAG_NAMES.INFO_TAG, "Before set")
                    setPlaceByDeviceAndUser(_chosenDeviceState.value, this)
                }

            }

            is ScannedObjectsListEvent.Refresh -> {
                refresh()
            }

            is ScannedObjectsListEvent.OnPlaceUpdate -> {
                _userAndPlaceBundle.value = event.userAndPlaceBundle
            }
        }

    }


    private fun loadDataFromRemoteServer(): Job {
        //_objectsListState.value.listOfObjects.clear()
        return viewModelScope.launch {
            for (scannedObject in scannedGroup.listOfScannedObjects) {
                Log.i(LOG_TAG_NAMES.INFO_TAG, "ID=${scannedObject.id}")
                getObjectInfoUseCase =
                    useCaseGetObjectFactory.createUseCase(scannedObject.tableName)

                getObjectInfoUseCase(scannedObject.id).onEach { resultData ->
                    addResultInList(resultData, scannedObject.id)
                    //TODO: add catching errors
                }.launchIn(this)

            }
        }
    }

    private fun addResultInList(
        objectGetResult: Resource<InventarizedAndQRScannableModel>,
        id: Long
    ) {
        when (objectGetResult) {
            is Resource.Error -> {
                Log.i(LOG_TAG_NAMES.INFO_TAG, "ERROR WITH ID: $id")
            }

            is Resource.Loading -> {
                //Log.i(LOG_TAG_NAMES.INFO_TAG, "LOADING WITH ID: $id")
            }

            is Resource.Success -> {
                Log.i(LOG_TAG_NAMES.INFO_TAG, "SUCCESS ID: $id with name: ${objectGetResult.data?.name}")
                _objectsListState.value.listOfObjects.add(
                    objectGetResult.data ?: Unknown("Not found")
                )
            }
        }
    }

    private fun setObjectsListInState(data: Resource<MutableList<InventarizedAndQRScannableModel>>) {
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

    private fun setBuildingByCabinet(
        resourceCabinet: Resource<Cabinet>,
        coroutineScope: CoroutineScope
    ) {
        when (resourceCabinet) {
            is Resource.Error -> {}
            is Resource.Loading -> {}
            is Resource.Success -> {
                getPlaceUseCases.getBuildingUseCase(resourceCabinet.data!!.buildingId).onEach {
                    when (it) {
                        is Resource.Error -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            _userAndPlaceBundle.value = userAndPlaceBundleState.value.copy(
                                user = userAndPlaceBundleState.value.user,
                                branch = userAndPlaceBundleState.value.branch,
                                building = it.data!!,
                                cabinet = userAndPlaceBundleState.value.cabinet,
                                organization = userAndPlaceBundleState.value.organization,
                            )
                            Log.i(LOG_TAG_NAMES.INFO_TAG, "Building set")
                            setBranchByBuilding(it, coroutineScope)
                        }
                    }
                }.launchIn(coroutineScope)
            }
        }
    }

    private fun setBranchByBuilding(
        resourceBuilding: Resource<Building>,
        coroutineScope: CoroutineScope
    ) {
        when (resourceBuilding) {
            is Resource.Error -> {}
            is Resource.Loading -> {}
            is Resource.Success -> {
                getPlaceUseCases.getBranchUseCase(resourceBuilding.data!!.branchId).onEach {
                    when (it) {
                        is Resource.Error -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            _userAndPlaceBundle.value = userAndPlaceBundleState.value.copy(
                                user = userAndPlaceBundleState.value.user,
                                branch = it.data!!,
                                building = userAndPlaceBundleState.value.building,
                                cabinet = userAndPlaceBundleState.value.cabinet,
                                organization = userAndPlaceBundleState.value.organization,
                            )
                            Log.i(LOG_TAG_NAMES.INFO_TAG, "Branch set")
                            sendUiEvent(
                                UIScannedObjectsListEvent.Navigate(
                                    context.resources.getString(
                                        R.string.modify_concrete_object
                                    )
                                )
                            )

                        }
                    }
                }.launchIn(coroutineScope)
            }
        }
    }

    private fun setOrganization(resourceOrganization: Resource<Organization>) {
        when (resourceOrganization) {
            is Resource.Error -> {}
            is Resource.Loading -> {}
            is Resource.Success -> {
                _userAndPlaceBundle.value = userAndPlaceBundleState.value.copy(
                    user = userAndPlaceBundleState.value.user,
                    branch = userAndPlaceBundleState.value.branch,
                    building = userAndPlaceBundleState.value.building,
                    cabinet = userAndPlaceBundleState.value.cabinet,
                    organization = resourceOrganization.data!!
                )
                Log.i(LOG_TAG_NAMES.INFO_TAG, "Organization set")
            }
        }
    }

    private fun setCabinet(resourceCabinet: Resource<Cabinet>) {
        when (resourceCabinet) {
            is Resource.Error -> {}
            is Resource.Loading -> {}
            is Resource.Success -> {
                _userAndPlaceBundle.value = userAndPlaceBundleState.value.copy(
                    user = userAndPlaceBundleState.value.user,
                    branch = userAndPlaceBundleState.value.branch,
                    building = userAndPlaceBundleState.value.building,
                    cabinet = resourceCabinet.data!!,
                    organization = userAndPlaceBundleState.value.organization
                )
                Log.i(LOG_TAG_NAMES.INFO_TAG, "Cabinet set")
            }
        }
    }

    private fun setPlaceByDeviceAndUser(
        data: InventarizedAndQRScannableModel?,
        coroutineScope: CoroutineScope
    ) {
        data?.let {
            getPlaceUseCases.getOrganizationUseCase(_userAndPlaceBundle.value.user.organizationId)
                .onEach {
                    setOrganization(it)
                }.launchIn(coroutineScope)

            getPlaceUseCases.getCabinetUseCase(data.cabinetId).onEach {
                setCabinet(it)
                setBuildingByCabinet(it, coroutineScope)
            }.launchIn(coroutineScope)
        }
    }


    private fun setDataWithStatus(
        result: Resource<MutableList<InventarizedAndQRScannableModel>>,
        isLoading: Boolean
    ) {
        _objectsListState.value = objectsListState.value.copy(
            listOfObjects = result.data ?: arrayListOf(),
            isLoading = isLoading
        )
    }

    private fun setErrorStatusAndSendSnackbarEvent(errorMessage: String?) {
        _objectsListState.value = objectsListState.value.copy(
            listOfObjects = arrayListOf(),
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