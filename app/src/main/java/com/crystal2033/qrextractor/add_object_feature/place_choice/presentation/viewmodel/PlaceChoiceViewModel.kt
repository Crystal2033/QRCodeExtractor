package com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.vm_view_communication.PlaceChoiceEvent
import com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.vm_view_communication.UIPlaceChoiceEvent
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.remote_server.data.model.Branch
import com.crystal2033.qrextractor.core.remote_server.data.model.Building
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetBranchesUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetBuildingsUseCase
import com.crystal2033.qrextractor.core.util.Resource
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

class PlaceChoiceViewModel @AssistedInject constructor(
    private val getBranchesUseCase: GetBranchesUseCase,
    private val getBuildingsUseCase: GetBuildingsUseCase,
    @Assisted private val user: User?,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _eventFlow = Channel<UIPlaceChoiceEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private val _listOfBranches = mutableStateListOf<Branch>()
    val listOfBranches: SnapshotStateList<Branch> = _listOfBranches

    private val _listOfBuildings = mutableStateListOf<Building>()
    val listOfBuildings: SnapshotStateList<Building> = _listOfBuildings

    private val _selectedBranch = mutableStateOf<Branch?>(null)
    val selectedBranch: State<Branch?> = _selectedBranch

    private val _selectedBuilding = mutableStateOf<Building?>(null)
    val selectedBuilding: State<Building?> = _selectedBuilding

    init {
        loadBranchesFromServer()
    }

    fun onEvent(event: PlaceChoiceEvent) {
        when (event) {
            is PlaceChoiceEvent.OnBranchChanged -> {
                _selectedBranch.value =
                    _listOfBranches.find { branch -> branch.id == event.chosenId }
                _selectedBranch.value?.let {
                    loadBuildingByBranchFromServer(it.id)
                }
            }

            is PlaceChoiceEvent.OnBuildingChanged -> {
                _selectedBuilding.value =
                    _listOfBuildings.find { building -> building.id == event.chosenId }
            }

            is PlaceChoiceEvent.OnContinueClicked -> {
                sendUiEvent(UIPlaceChoiceEvent.Navigate(context.resources.getString(R.string.menu_add_route)))
            }
        }
    }

    fun isAbleToChooseBuilding(): Boolean {
        return _selectedBranch.value != null
    }

    private fun loadBranchesFromServer(): Job {
        return viewModelScope.launch {
            user?.let {
                getBranchesUseCase(user.organizationId).onEach { statusWithState ->
                    insertPossibleObjectsInListIfSuccess(statusWithState, _listOfBranches)
                }.launchIn(this)
            }

        }
    }

    private fun loadBuildingByBranchFromServer(branchId: Long): Job {

        return viewModelScope.launch {
            getBuildingsUseCase(branchId).onEach { statusWithState ->
                insertPossibleObjectsInListIfSuccess(statusWithState, _listOfBuildings)
            }.launchIn(this)
        }
    }


    private fun <T> insertPossibleObjectsInListIfSuccess(
        statusWithState: Resource<List<T>>,
        listOfPossibleObjects: MutableList<T>
    ) {
        when (statusWithState) {
            is Resource.Error -> {}
            is Resource.Loading -> {}
            is Resource.Success -> {
                Log.i(LOG_TAG_NAMES.INFO_TAG, "Added list of possible objects")
                listOfPossibleObjects.clear()
                listOfPossibleObjects.addAll(statusWithState.data ?: emptyList())
                for (currentObj in listOfPossibleObjects) {
                    Log.i(LOG_TAG_NAMES.INFO_TAG, "Possible object: ${currentObj.toString()}")
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(user: User?): PlaceChoiceViewModel
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

    private fun sendUiEvent(event: UIPlaceChoiceEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }
}