package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.person

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.core.model.Department
import com.crystal2033.qrextractor.core.model.Title
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.model.WorkSpace
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext


class AddPersonViewModel @AssistedInject constructor(
    @Assisted private val user: User,
    @ApplicationContext private val context: Context,
    //TODO: USE_CASES

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
        //TODO: GET ALL NEEDED LIST FROM API
    }

    //states
    private val _listOfDepartments = mutableStateListOf<Department>()
    val listOfDepartments: SnapshotStateList<Department> = _listOfDepartments

    private val _listOfTitles = mutableStateListOf<Title>()
    val listOfTitles: SnapshotStateList<Title> = _listOfTitles

    private val _listOfWorkSpace = mutableStateListOf<WorkSpace>()
    val listOfWorkSpace: SnapshotStateList<WorkSpace> = _listOfWorkSpace
    //states
    fun test() {
        //createQRCode()
    }


}