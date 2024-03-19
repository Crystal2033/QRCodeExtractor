package com.crystal2033.qrextractor.scanner_feature.list_of_groups.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderDelete
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.util.GetStringNotInComposable
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.ScannedGroup
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.UserWithScannedGroups
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.use_case.DeleteScannedGroupUseCase
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.use_case.GetListOfUserScannedGroupsUseCase
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.presentation.state.UserScannedGroupsState
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.vm_view_communication.ScannedGroupsListEvent
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.vm_view_communication.UIScannedGroupsListEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

//@HiltViewModel

class ScannedDataGroupsViewModel @AssistedInject constructor(
    private val getListOfUserScannedGroupsUseCase: GetListOfUserScannedGroupsUseCase,
    private val deleteScannedGroupUseCase: DeleteScannedGroupUseCase,
    @Assisted private val user: User?,
    @ApplicationContext private val context: Context
) : ViewModel() {

    //states
    private val _scannedGroupsForUser = mutableStateOf(UserScannedGroupsState())
    val scannedGroupsForUser: State<UserScannedGroupsState> = _scannedGroupsForUser

    private val _chosenGroup = mutableStateOf(ScannedGroup())
    val chosenGroup: State<ScannedGroup> = _chosenGroup

    //states

    init {
        Log.i(LOG_TAG_NAMES.INFO_TAG, "REFRESH")
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

    private val _eventFlow = Channel<UIScannedGroupsListEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    fun onEvent(event: ScannedGroupsListEvent) {
        when (event) {
            is ScannedGroupsListEvent.OnGroupClickedEvent -> {
                _chosenGroup.value = findGroupById(event.groupId)!!
                sendUiEvent(UIScannedGroupsListEvent.Navigate(context.resources.getString(R.string.list_of_scanned_objects)))
            }

            is ScannedGroupsListEvent.OnDeleteGroupClicked -> {
                sendUiEvent(
                    UIScannedGroupsListEvent.ShowMessagedDialogWindow(
                        message = GetStringNotInComposable(
                            context,
                            R.string.ask_for_delete_group_translate
                        ) + " ${event.scannedGroup.groupName} ?",
                        onDeclineAction = {},
                        onAcceptAction = {
                            Log.i(LOG_TAG_NAMES.INFO_TAG, "Removing group!")
                            removeScannedGroupWithObjects(event.scannedGroup)
                        },
                        dialogTitle = GetStringNotInComposable(
                            context,
                            R.string.delete_scanned_group_translate
                        ),
                        icon = Icons.Filled.FolderDelete
                    )
                )
            }
        }
    }

    private fun findGroupById(groupId: Long): ScannedGroup? {
        return _scannedGroupsForUser.value.userScannedGroups?.getScannedGroupById(groupId)
    }

    private fun removeScannedGroupWithObjects(scannedGroupToDelete: ScannedGroup) {
        viewModelScope.launch {
            deleteScannedGroupUseCase(scannedGroupToDelete).onEach {
                when (it) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        refresh()
                    }
                }
            }.launchIn(this)
        }
    }

    private fun refresh() {
        user?.let { existingUser ->
            viewModelScope.launch {
                getListOfUserScannedGroupsUseCase(existingUser.idLocalDB).onEach { result ->
                    setGroupsListInState(result)
                }.launchIn(this)
            }
        }
    }

    private fun setGroupsListInState(data: Resource<UserWithScannedGroups>) {
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
            UIScannedGroupsListEvent.ShowSnackBar(
                message = errorMessage ?: GetStringNotInComposable(
                    context,
                    R.string.unknown_error_translate
                )
            )
        )
    }

    private fun sendUiEvent(event: UIScannedGroupsListEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }
}