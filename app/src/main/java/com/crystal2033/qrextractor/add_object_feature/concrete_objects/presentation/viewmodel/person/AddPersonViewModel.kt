package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.person

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person.PersonGetterUseCases
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.model.Department
import com.crystal2033.qrextractor.core.model.Title
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.model.WorkSpace
import com.crystal2033.qrextractor.core.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class AddPersonViewModel @AssistedInject constructor(
    @Assisted private val user: User,
    @ApplicationContext private val context: Context,
    private val personGetterUseCases: PersonGetterUseCases

) : BaseAddObjectViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(user: User?): AddPersonViewModel
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

    init {
        loadInfoFromRemoteServer()
    }

    private fun loadInfoFromRemoteServer(): Job {
        return viewModelScope.launch {
            personGetterUseCases.getDepartmentsUseCase().onEach { statusWithState ->
                insertPossibleObjectsInListIfSuccess(statusWithState, _listOfDepartments)
            }.launchIn(this)

            personGetterUseCases.getTitlesUseCase().onEach { statusWithState ->
                insertPossibleObjectsInListIfSuccess(statusWithState, _listOfTitles)
            }.launchIn(this)

            personGetterUseCases.getWorkspacesUseCase().onEach { statusWithState ->
                insertPossibleObjectsInListIfSuccess(statusWithState, _listOfWorkSpaces)
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
                listOfPossibleObjects.addAll(statusWithState.data ?: emptyList())
                for (currentObj in listOfPossibleObjects) {
                    Log.i(LOG_TAG_NAMES.INFO_TAG, "Possible object: ${currentObj.toString()}")
                }
            }
        }
    }

    //states
    private val _listOfDepartments = mutableStateListOf<Department>()
    val listOfDepartments: SnapshotStateList<Department> = _listOfDepartments

    private val _listOfTitles = mutableStateListOf<Title>()
    val listOfTitles: SnapshotStateList<Title> = _listOfTitles

    private val _listOfWorkSpaces = mutableStateListOf<WorkSpace>()
    val listOfWorkSpaces: SnapshotStateList<WorkSpace> = _listOfWorkSpaces

    //states
    fun test() {
        //createQRCode()
    }


}