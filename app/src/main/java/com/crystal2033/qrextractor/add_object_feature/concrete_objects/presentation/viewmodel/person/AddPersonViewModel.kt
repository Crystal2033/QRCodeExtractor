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
                when (statusWithState) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        Log.i(LOG_TAG_NAMES.INFO_TAG, "Added list of departments")
                        _listOfDepartments.addAll(statusWithState.data ?: emptyList())
                        for (department in listOfDepartments) {
                            Log.i(LOG_TAG_NAMES.INFO_TAG, "Workspace: ${department.name}")
                        }
                    }
                }
            }.launchIn(this)

            personGetterUseCases.getTitlesUseCase().onEach { statusWithState ->
                when (statusWithState) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        Log.i(LOG_TAG_NAMES.INFO_TAG, "Added list of titles")
                        _listOfTitles.addAll(statusWithState.data ?: emptyList())
                        for (title in listOfTitles) {
                            Log.i(LOG_TAG_NAMES.INFO_TAG, "Workspace: ${title.name}")
                        }
                    }
                }
            }.launchIn(this)

            personGetterUseCases.getWorkspacesUseCase().onEach { statusWithState ->
                when (statusWithState) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        Log.i(LOG_TAG_NAMES.INFO_TAG, "Added list of workspaces")
                        _listOfWorkSpaces.addAll(statusWithState.data ?: emptyList())
                        for (workspace in listOfWorkSpaces) {
                            Log.i(LOG_TAG_NAMES.INFO_TAG, "Workspace: ${workspace.id}")
                        }
                    }
                }
            }.launchIn(this)
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