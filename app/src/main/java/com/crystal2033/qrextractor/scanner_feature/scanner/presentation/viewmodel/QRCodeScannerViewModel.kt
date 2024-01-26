package com.crystal2033.qrextractor.scanner_feature.scanner.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.remote_server.data.model.Building
import com.crystal2033.qrextractor.core.remote_server.data.model.Cabinet
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.data.model.Organization
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetDeviceUseCaseInvoker
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetPlaceUseCases
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.data.Converters
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.ScannedTableNameAndId
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.Unknown
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.concrete_use_case.InsertScannedGroupInDBUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory.UseCaseGetObjectFromServerFactory
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.state.ScannedDataState
import com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication.QRScannerEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication.UIScannerEvent
import com.google.gson.JsonSyntaxException
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


class QRCodeScannerViewModel @AssistedInject constructor(
    private val converter: Converters,
    @Assisted private val user: User,
    private val getPlaceUseCases: GetPlaceUseCases,
    private val useCaseGetQRCodeFactory: UseCaseGetObjectFromServerFactory,
    private val insertScannedGroupInDBUseCase: InsertScannedGroupInDBUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(user: User): QRCodeScannerViewModel
    }

    companion object {
        const val timeForDuplicateQRCodesResistInMs = 12000L

        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            user: User
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(user) as T
            }
        }
    }

    private lateinit var getDataFromQRCodeUseCase: GetDeviceUseCaseInvoker

    ///States
    private val _previewDataFromQRState = mutableStateOf(ScannedDataState())
    val previewDataFromQRState: State<ScannedDataState> = _previewDataFromQRState

    private val _listOfAddedScannables = mutableStateListOf<InventarizedAndQRScannableModel>()
    val listOfAddedScannables: SnapshotStateList<InventarizedAndQRScannableModel> =
        _listOfAddedScannables

    private val _userAndPlaceBundle = mutableStateOf(
        UserAndPlaceBundle(
            user = user
        )
    )
    val userAndPlaceBundle: State<UserAndPlaceBundle> = _userAndPlaceBundle


    ///States

    private val _eventFlow = Channel<UIScannerEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private var scanJob: Job? = null

    private var deleteDuplicateQRCodeStringJob: Job? = null

    private var prevScanString: String? = null

    fun onEvent(event: QRScannerEvent) {
        when (event) {
            is QRScannerEvent.OnAddObjectInList -> {
                onAddScannableIntoListClicked(event.scannableObject, event.addEvenIfDuplicate)
            }

            is QRScannerEvent.OnAddScannedGroup -> {
                sendUiEvent(UIScannerEvent.ShowScannedGroupNameDialogWindow)
            }

            is QRScannerEvent.OnScanQRCode -> {
                onScanQRCode(event.scannedData)
            }

            is QRScannerEvent.OnAddNameForScannedGroup -> {
                onAddScannedGroupClicked(event.groupName)
            }

            is QRScannerEvent.ClearListOfScannedObjects -> {
                _listOfAddedScannables.clear()
            }

            is QRScannerEvent.OnGoToScannedGroupsWindow -> {
                sendUiEvent(UIScannerEvent.Navigate(context.resources.getString(R.string.list_of_groups_route)))
            }

        }
    }


    private fun onAddScannedGroupClicked(groupName: String) {
        Log.i(LOG_TAG_NAMES.INFO_TAG, "Group name: $groupName")

        viewModelScope.launch {
            insertScannedGroupInDBUseCase(
                qrScannableDataList = listOfAddedScannables.toList(),
                user = user,
                groupName = groupName
            ).onEach { result ->

            }.launchIn(this)
        }
    }

    private fun onScanQRCode(scanResult: String) {
        if (scanResult.isBlank() || prevScanString == scanResult) {
            return
        }
        setDeduplicateStringAndDelayForClear(scanResult)

        try {
            val scannedObject = converter.fromJsonToScannedTableNameAndId(scanResult)
            scanJob?.cancel()
            insertScannedDataInStateIfPossible(scannedObject)
        } catch (e: JsonSyntaxException) {
            showQRCodeFormatError(e)
        }
    }

    private fun onAddScannableIntoListClicked(
        scannableObject: InventarizedAndQRScannableModel,
        isAddEvenDuplicate: Boolean
    ) {
        if (isAddEvenDuplicate) {
            _listOfAddedScannables.add(scannableObject)
            return
        }

        if (_listOfAddedScannables.find { it == scannableObject } != null) {
            viewModelScope.launch {
                sendUiEvent(
                    UIScannerEvent.ShowMessagedDialogWindow(
                        message = "This object already exists in list. Do you really want to append another one?",
                        onDeclineAction = {},
                        onAcceptAction = {
                            onAddScannableIntoListClicked(scannableObject, true)
                        },
                        dialogTitle = "Duplicate object",
                        icon = Icons.Default.Warning
                    )
                )
            }
        } else {
            _listOfAddedScannables.add(scannableObject)
        }
    }

    private fun showQRCodeFormatError(e: JsonSyntaxException) {
        Log.e(LOG_TAG_NAMES.ERROR_TAG, e.message ?: "Unknown")
        scanJob = viewModelScope.launch {
            setPreviewObjectStateInfo(Resource.Error(message = "QR-code`s content is not compatible with this application."))
        }
    }

    private fun setDeduplicateStringAndDelayForClear(scanResult: String) {
        prevScanString = scanResult
        deleteDuplicateQRCodeStringJob?.cancel()
        deleteDuplicateQRCodeStringJob = CoroutineScope(Dispatchers.Default).launch {
            delay(timeForDuplicateQRCodesResistInMs)
            prevScanString = ""
        }
    }

    private fun insertScannedDataInStateIfPossible(scannedObject: ScannedTableNameAndId?) {
        scanJob = viewModelScope.launch {
            scannedObject?.let { scannedObj ->
                try {
                    getDataFromQRCodeUseCase =
                        useCaseGetQRCodeFactory.createUseCase(scannedObj.tableName)
                } catch (error: ClassNotFoundException) {
                    Log.e(LOG_TAG_NAMES.ERROR_TAG, error.message ?: "Unknown error")
                    val errorMsg = error.message ?: "Unknown error"
                    setPreviewObjectStateInfo(Resource.Error(message = errorMsg, Unknown(errorMsg)))
                    return@launch
                }

                getDataFromQRCodeUseCase(scannedObj.id).onEach { result ->
                    setPreviewObjectStateInfo(result)
                }.launchIn(this)
            } ?: Log.e(LOG_TAG_NAMES.ERROR_TAG, "Error with scannedObject convertion. No id there")
        }
    }

    private fun setPreviewObjectStateInfo(data: Resource<InventarizedAndQRScannableModel>) {
        when (data) {
            is Resource.Loading -> {
                setDataWithStatus(data, true)
            }

            is Resource.Error -> {
                setErrorStatusAndSendSnackbarEvent(data.message)
            }

            is Resource.Success -> {
                setPlaceByDeviceAndUser(data.data)
                setDataWithStatus(data, false)
            }
        }
    }


    private fun setBuildingByCabinet(resourceCabinet: Resource<Cabinet>) {
        when (resourceCabinet) {
            is Resource.Error -> {}
            is Resource.Loading -> {}
            is Resource.Success -> {
                CoroutineScope(Dispatchers.Default).launch {
                    getPlaceUseCases.getBuildingUseCase(resourceCabinet.data!!.buildingId).onEach {
                        when (it) {
                            is Resource.Error -> {}
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                _userAndPlaceBundle.value = userAndPlaceBundle.value.copy(
                                    user = userAndPlaceBundle.value.user,
                                    branch = userAndPlaceBundle.value.branch,
                                    building = it.data!!,
                                    cabinet = userAndPlaceBundle.value.cabinet,
                                    organization = userAndPlaceBundle.value.organization,
                                )
                                setBranchByBuilding(it)
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    private fun setBranchByBuilding(
        resourceBuilding: Resource<Building>
    ) {
        when (resourceBuilding) {
            is Resource.Error -> {}
            is Resource.Loading -> {}
            is Resource.Success -> {
                CoroutineScope(Dispatchers.Default).launch {
                    getPlaceUseCases.getBranchUseCase(resourceBuilding.data!!.branchId).onEach {
                        when (it) {
                            is Resource.Error -> {}
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                _userAndPlaceBundle.value = userAndPlaceBundle.value.copy(
                                    user = userAndPlaceBundle.value.user,
                                    branch = it.data!!,
                                    building = userAndPlaceBundle.value.building,
                                    cabinet = userAndPlaceBundle.value.cabinet,
                                    organization = userAndPlaceBundle.value.organization,
                                )
                            }
                        }


                    }.launchIn(this)
                }
            }
        }
    }

    private fun setOrganization(resourceOrganization: Resource<Organization>) {
        when (resourceOrganization) {
            is Resource.Error -> {}
            is Resource.Loading -> {}
            is Resource.Success -> {
                _userAndPlaceBundle.value = userAndPlaceBundle.value.copy(
                    user = userAndPlaceBundle.value.user,
                    branch = userAndPlaceBundle.value.branch,
                    building = userAndPlaceBundle.value.building,
                    cabinet = userAndPlaceBundle.value.cabinet,
                    organization = resourceOrganization.data!!
                )
            }
        }
    }

    private fun setCabinet(resourceCabinet: Resource<Cabinet>) {
        when (resourceCabinet) {
            is Resource.Error -> {}
            is Resource.Loading -> {}
            is Resource.Success -> {
                _userAndPlaceBundle.value = userAndPlaceBundle.value.copy(
                    user = userAndPlaceBundle.value.user,
                    branch = userAndPlaceBundle.value.branch,
                    building = userAndPlaceBundle.value.building,
                    cabinet = resourceCabinet.data!!,
                    organization = userAndPlaceBundle.value.organization
                )
            }
        }
    }

    private fun setPlaceByDeviceAndUser(data: InventarizedAndQRScannableModel?) {
        data?.let {
            CoroutineScope(Dispatchers.Default).launch {
                getPlaceUseCases.getOrganizationUseCase(_userAndPlaceBundle.value.user.organizationId).onEach {
                    setOrganization(it)
                }.launchIn(this)

                getPlaceUseCases.getCabinetUseCase(data.cabinetId).onEach {
                    setCabinet(it)
                    setBuildingByCabinet(it)
                }.launchIn(this)
            }

        }
    }

//    private fun setUIEventAfterAddedScannedGroup(data: Resource<Unit>) {
//        when (data) {
//            is Resource.Loading -> {
//
//            }
//
//            is Resource.Error -> {
//            }
//
//            is Resource.Success -> {
//            }
//        }
//    }

    private fun setDataWithStatus(
        result: Resource<InventarizedAndQRScannableModel>,
        isLoading: Boolean
    ) {
        _previewDataFromQRState.value = previewDataFromQRState.value.copy(
            scannedDataInfo = result.data,
            isLoading = isLoading
        )
    }

    private fun setErrorStatusAndSendSnackbarEvent(errorMessage: String?) {
        _previewDataFromQRState.value = previewDataFromQRState.value.copy(
            scannedDataInfo = null,
            isLoading = false
        )

        sendUiEvent(
            UIScannerEvent.ShowSnackBar(
                message = errorMessage ?: "Unknown error"
            )
        )
    }

    private fun sendUiEvent(event: UIScannerEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }
}