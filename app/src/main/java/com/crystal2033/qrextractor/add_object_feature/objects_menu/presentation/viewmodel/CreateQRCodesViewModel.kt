package com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.util.QRCodeGenerator
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.vm_view_communication.CreateQRCodeEvent
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.vm_view_communication.UICreateQRCodeEvent
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CreateQRCodesViewModel @AssistedInject constructor(
    @Assisted private val userWithPlaceBundle: UserAndPlaceBundle,
    @ApplicationContext private val context: Context

) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(userWithPlaceBundle: UserAndPlaceBundle): CreateQRCodesViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            userWithPlaceBundle: UserAndPlaceBundle
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(userWithPlaceBundle) as T
            }
        }
    }

    //states
    private val _menuListState = mutableStateListOf<DatabaseObjectTypes>()
    val menuListState: SnapshotStateList<DatabaseObjectTypes> = _menuListState

    private val _listOfAddedQRCodes = mutableStateListOf<QRCodeStickerInfo>()
    val listOfAddedQRCodes: SnapshotStateList<QRCodeStickerInfo> = _listOfAddedQRCodes

    private val _chosenObjectClassState = mutableStateOf(DatabaseObjectTypes.UNKNOWN)
    val chosenObjectClassState: State<DatabaseObjectTypes> = _chosenObjectClassState

    private val _branchName = mutableStateOf(userWithPlaceBundle.branch.name)
    val branchName: State<String> = _branchName

    private val _buildingAddress = mutableStateOf(userWithPlaceBundle.building.address)
    val buildingAddress: State<String> = _buildingAddress

    private val _cabinetName = mutableStateOf(userWithPlaceBundle.cabinet.name)
    val cabinetName: State<String> = _cabinetName
    //states


    private val _eventFlow = Channel<UICreateQRCodeEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private val listOfObjectNames = listOf<String>(
        "Монитор",
        "Д-САН",
        "Подставка телефона",
        "Деревянная подставка для палок",
        "Настольная лампа",
        "мышь",
        "динамик1",
        "динамик2",
        "стол",
        "системник",
        "книга Кинга1",
        "книга Кинга2",
        "книга Кинга3",
        "книга Кинга4",
        "книга Кинга5",
        "книга Кинга6",
        "книга Кинга7",
        "пенал",
        "стакан из мака",
        "рефлектор",
        "бардовая коробка",
        "фигурка гендальфа",
        "книга Лавкрафта",
        "Шишкин лес",
        "Доска для мела",
        "Свеча исильдур",
        "свеча йенифер",
        "свеча гендальф",
        "свеча арвен",
        "свеча Дамблдор",
        "свеча шир",
        "свеча леголас",
        "свеча тонкабим",
        "будильник цифровой",
        "айфон 7",
        "наушники JBL",
        "пробуждение левиафана книга",
        "книга Агата Кристи",
        "мягкая лягушка",
        "кровать",
        "гантеля 1",
        "гантеля 2",
        "совершенный код",
        "чистый код",
        "олиферы",
        "построение и анализ",
        "покрывало кровати",
        "кровать",
        "подушка для кровати",
        "сумка для ноута",
        "ноутбук манжаро",
        "коврик для мыши",
        "точилка для карандашей",
        "сундук с игрушками",
        "зеленые духи",
        "фиолетовые духи",
        "духи Паша",
        "духи синие",
        "духи гангстер",
        "духи черные",
        "коробка из-под фиолетовых духов",
        "архив1",
        "архив2",
        "архив3",
        "архив4",
        "архив5",
        "архив6",
        "гипсовые руки",
        "розетка",
        "коробка для ноута",
        "расческа",
        "шкаф",
        "тапок 1",
        "тапок 2",
        "стул аврора",
        "коробка для наушников",
        "вторая мышь",
        "зарядный блок",
        "бутылка 1",
        "бутылка 2",
        "бутылка 3",
        "капли назальные",
        "мышь в бк",
        "стул в бк",
        "моник в бк",
        "черный стол рядом с кроватью в бк",
        "компьюетный стол в бк",
        "телевизор в бк",
        "радио в бк",
        "зеркало в бк",
        "цифровые часы в бк",
        "зарядка беспроводная",
        "пульт1",
        "пульт2",
        "пульт3",
        "пульт4",
        "айфон 5",
        "папина сумка",
        "диван",
        "роутер бочонок",
        "диван в бк"
    )

    private val randomStrHashSet = hashSetOf<String>()

    init {
        _menuListState.addAll(
            listOf(
                DatabaseObjectTypes.KEYBOARD,
                DatabaseObjectTypes.DESK,
                DatabaseObjectTypes.SYSTEM_UNIT,
                DatabaseObjectTypes.MONITOR,
                DatabaseObjectTypes.CHAIR,
                DatabaseObjectTypes.PROJECTOR
            )
        )

        _listOfAddedQRCodes.addAll(
                generateQRCodes(100)
            )
    }

    private fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        var randomStr = ""
        do {
            randomStr = (1..length)
                .map { allowedChars.random() }
                .joinToString("")
                .uppercase()
        }while (randomStrHashSet.contains(randomStr))
        randomStrHashSet.add(randomStr)
        return randomStr
    }
    private fun generateQRCodes(capacity : Int) : MutableList<QRCodeStickerInfo>{
        val listOfCodes = mutableListOf<QRCodeStickerInfo>()
        for(i in 0..<capacity){
            val invNumber = getRandomString(8)
            val qrCode = QRCodeStickerInfo(
                qrCode = QRCodeGenerator.encodeAsBitmap("{\"id\":${i},\"tableName\":\"monitor\",\"invNumber\":\"${invNumber}\",\"orgId\":\"1\"}", 250, 250).asImageBitmap(),
                essentialName = listOfObjectNames[i],
                inventoryNumber = invNumber,
                databaseObjectTypes = DatabaseObjectTypes.MONITOR
            )
            //Log.i(LOG_TAG_NAMES.DEBUG_TAG, "${i+1}. ${listOfObjectNames[i]} : $invNumber")
            listOfCodes.add(qrCode)
        }

        listOfCodes.shuffle()
        for(i in 0..<listOfCodes.size){
            Log.i(LOG_TAG_NAMES.DEBUG_TAG, "${i+1}. ${listOfCodes[i].essentialName} : ${listOfCodes[i].inventoryNumber}")
        }
        return listOfCodes
    }

    fun onEvent(event: CreateQRCodeEvent) {
        when (event) {
            is CreateQRCodeEvent.SetChosenObjectClass -> {
                _chosenObjectClassState.value = event.objectType
                sendUiEvent(UICreateQRCodeEvent.Navigate(context.getString(R.string.add_concrete_class)))
            }

            is CreateQRCodeEvent.OnAddNewObjectInList -> {
                Log.i(
                    LOG_TAG_NAMES.INFO_TAG, "Added new QRCode in list:" +
                            " ${event.qrCodeStickerInfo.essentialName}"
                )
                _listOfAddedQRCodes.add(event.qrCodeStickerInfo)
            }

            is CreateQRCodeEvent.OnQRCodesListClicked -> {
                sendUiEvent(UICreateQRCodeEvent.Navigate(context.resources.getString(R.string.qr_codes_list)))
            }

            is CreateQRCodeEvent.OnChangePlaceClicked -> {
                sendUiEvent(
                    UICreateQRCodeEvent.Navigate(
                        context.resources.getString(R.string.place_choice_add)
                    )
                )
            }

            is CreateQRCodeEvent.ChangePlaceField -> {
                _branchName.value = event.userAndPlaceBundle.branch.name
                _buildingAddress.value = event.userAndPlaceBundle.building.address
                _cabinetName.value = event.userAndPlaceBundle.cabinet.name
            }
        }
    }

//    fun getBranchName(): String {
//        return userWithPlaceBundle.branch.name
//    }
//
//    fun getBuildingAddress(): String {
//        return userWithPlaceBundle.building.address
//    }
//
//    fun getCabinetName(): String {
//        return userWithPlaceBundle.cabinet.name
//    }

    private fun sendUiEvent(event: UICreateQRCodeEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }
}