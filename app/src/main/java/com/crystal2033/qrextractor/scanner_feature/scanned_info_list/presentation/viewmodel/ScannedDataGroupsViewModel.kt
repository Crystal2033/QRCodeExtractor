package com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.User
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.model.UserWithScannedGroups
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.use_case.GetListOfUserScannedGroupsUseCase
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation.state.UserScannedGroupsState
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.vm_view_communication.ScannedDataListEvent
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.vm_view_communication.UIScannedDataListEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.viewmodel.QRCodeScannerViewModel
import com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication.UIScannerEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
class ScannedDataGroupsViewModel @AssistedInject constructor(
    private val getListOfUserScannedGroupsUseCase: GetListOfUserScannedGroupsUseCase,
    @Assisted private val user: User?
) : ViewModel() {

    //states
    private val _scannedGroupsForUser = mutableStateOf(UserScannedGroupsState())
    val scannedGroupsForUser : State<UserScannedGroupsState> = _scannedGroupsForUser
    //states

    fun printUserAddress(): Unit{
        Log.i(LOG_TAG_NAMES.INFO_TAG, "User address is: ${System.identityHashCode(user)}")
    }
    init {
        Log.i(LOG_TAG_NAMES.INFO_TAG, "RESRESH")
        refresh()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(LOG_TAG_NAMES.INFO_TAG, "CLEARED")
    }

    @AssistedFactory
    interface Factory {
        fun create(user: User?): ScannedDataGroupsViewModel
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




    private val _eventFlow = Channel<UIScannedDataListEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    fun onEvent(event: ScannedDataListEvent) {
//        when (event) {
//
//        }
    }

    private fun refresh(){
        Log.i(LOG_TAG_NAMES.INFO_TAG, "REFRESHED with user: ${user?.name} with address: ${System.identityHashCode(user)}")
        user?.let {
            existingUser ->
            viewModelScope.launch {
                getListOfUserScannedGroupsUseCase(existingUser.id).onEach { result ->
                    setListDataInStates(result)
                }.launchIn(this)
            }
        }
    }

    private fun setListDataInStates(data: Resource<UserWithScannedGroups>){
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

    private fun setDataWithStatus(result: Resource<UserWithScannedGroups>, isLoading: Boolean) {
        _scannedGroupsForUser.value = scannedGroupsForUser.value.copy(
            userScannedGroups = result.data,
            isLoading = isLoading
        )

    }

    private fun setErrorStatusAndSendSnackbarEvent(errorMessage: String?) {
        _scannedGroupsForUser.value = scannedGroupsForUser.value.copy(
            userScannedGroups = null,
            isLoading = false
        )

        sendUiEvent(
            UIScannedDataListEvent.ShowSnackBar(
                message = errorMessage ?: "Unknown error"
            )
        )
    }

    private fun sendUiEvent(event: UIScannedDataListEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }
}