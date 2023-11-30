package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.person

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person.PersonGetterUseCases
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.person.PersonState
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication.AddNewObjectEvent
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication.UIAddNewObjectEvent
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.core.model.Department
import com.crystal2033.qrextractor.core.model.Person
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

    //states
    private val _listOfDepartments = mutableStateListOf<Department>()
    val listOfDepartments: SnapshotStateList<Department> = _listOfDepartments

    private val _listOfTitles = mutableStateListOf<Title>()
    val listOfTitles: SnapshotStateList<Title> = _listOfTitles

    private val _listOfWorkSpaces = mutableStateListOf<WorkSpace>()
    val listOfWorkSpaces: SnapshotStateList<WorkSpace> = _listOfWorkSpaces

    private val _personState = mutableStateOf(PersonState())
    val personState: State<PersonState> = _personState

    //states

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


    fun onEvent(event: AddNewObjectEvent) {

    }


    override fun addObjectInDatabaseClicked(onAddObjectClicked: (QRCodeStickerInfo) -> Unit) {
        //send ui event to collect all inserted data from UI
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.concept)
        personState.value.imageState.value = bitmap.asImageBitmap()

        val qrCodeStickerInfo = QRCodeStickerInfo()

        val person = fromPersonStateIntoPerson()
        viewModelScope.launch {
            personGetterUseCases.addNewPersonUseCase(person).onEach { statusWithState ->
                when (statusWithState) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        personState.value.id.value = statusWithState.data?.id ?: 0
                        setQRStickerInfo(statusWithState.data, qrCodeStickerInfo)
                        onAddObjectClicked(qrCodeStickerInfo)
                        sendUiEvent(UIAddNewObjectEvent.Navigate(context.resources.getString(R.string.menu_add_route)))
                    }
                }
            }.launchIn(this)
        }
    }

    private fun setQRStickerInfo(person: Person?, qrCodeStickerInfo: QRCodeStickerInfo) {
        person?.let {
            qrCodeStickerInfo.qrCode = createQRCode(person)
            qrCodeStickerInfo.essentialName = person.firstName + person.secondName
            qrCodeStickerInfo.inventoryNumber = "ASD"
            qrCodeStickerInfo.databaseObjectTypes = person.getDatabaseTableName()
        }

    }

    private fun fromPersonStateIntoPerson(): Person {
        return Person(
            id = 0,
            department = listOfDepartments.find { it.name == personState.value.departmentState.value.name.value },
            firstName = personState.value.firstNameState.value,
            secondName = personState.value.secondNameState.value,
            image = personState.value.imageState.value,
            title = listOfTitles.find { it.name == personState.value.titleState.value.name.value },
            workSpace = listOfWorkSpaces.find { it.id.toString() == personState.value.workspaceState.value.id.value }
        )
    }


}