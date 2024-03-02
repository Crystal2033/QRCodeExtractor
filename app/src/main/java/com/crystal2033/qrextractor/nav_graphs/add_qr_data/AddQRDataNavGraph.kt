package com.crystal2033.qrextractor.nav_graphs.add_qr_data

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.chair.AddChairView
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.desk.AddDeskView
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.keyboard.AddKeyboardView
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.monitor.AddMonitorView
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.projector.AddProjectorView
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.system_unit.AddSystemUnitView
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.AddDataViewModels.Companion.addChairViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.AddDataViewModels.Companion.addDeskViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.AddDataViewModels.Companion.addKeyboardViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.AddDataViewModels.Companion.addMonitorViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.AddDataViewModels.Companion.addProjectorViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.AddDataViewModels.Companion.addSystemUnitViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.AddDataViewModels.Companion.qrCodeDocumentViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.AddDataViewModels.Companion.sharedAddDataMenuViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.chair.AddChairViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.desk.AddDeskViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.keyboard.AddKeyboardViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.monitor.AddMonitorViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.projector.AddProjectorViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.system_unit.AddSystemUnitViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication.AddNewObjectEvent
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.MenuView
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel.CreateQRCodesViewModel
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.vm_view_communication.CreateQRCodeEvent
import com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.PlaceChoiceView
import com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.viewmodel.PlaceChoiceViewModel
import com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.viewmodel.PlaceViewModelCreator.Companion.sharedPlaceChoiceViewModel
import com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.vm_view_communication.PlaceChoiceEvent
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.QRCodeStickersView
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.viewmodel.DocumentWithQRCodesViewModel
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.presentation.NotLoginLinkView
import com.crystal2033.qrextractor.core.remote_server.data.model.Branch
import com.crystal2033.qrextractor.core.remote_server.data.model.Building
import com.crystal2033.qrextractor.core.remote_server.data.model.Cabinet
import com.crystal2033.qrextractor.core.remote_server.data.model.Organization
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.ui.NavBottomBarConstants

fun NavGraphBuilder.addQRCodeGraph(
    navController: NavController,
    context: Context,
    snackbarHostState: SnackbarHostState,
    userState: State<User?>
) {
    navigation(
        startDestination = context.resources.getString(R.string.place_choice_add),
        route = context.resources.getString(R.string.add_data_head_graph_route)
    ) {

        composable(context.resources.getString(R.string.place_choice_add)) {

            userState.value?.let { user ->
                val placeChoiceViewModel =
                    it.sharedPlaceChoiceViewModel<PlaceChoiceViewModel>(
                        navController = navController,
                        user = user,
                        startNextRoute = context.resources.getString(R.string.menu_add_route)
                    )

                val menuViewModel = it.sharedAddDataMenuViewModel<CreateQRCodesViewModel>(
                    navController = navController,
                    userWithPlaceBundle = UserAndPlaceBundle(user = user)
                )

                Column(
                    modifier = Modifier.padding(
                        0.dp,
                        0.dp,
                        0.dp,
                        NavBottomBarConstants.HEIGHT_BOTTOM_BAR
                    )
                ) {
                    PlaceChoiceView(
                        viewModel = placeChoiceViewModel,
                        actionBeforeNavigate = {
                            menuViewModel.onEvent(
                                CreateQRCodeEvent.ChangePlaceField(
                                    UserAndPlaceBundle(
                                        user = user,
                                        branch = placeChoiceViewModel.selectedBranch.value
                                            ?: Branch(),
                                        building = placeChoiceViewModel.selectedBuilding.value
                                            ?: Building(),
                                        cabinet = placeChoiceViewModel.selectedCabinet.value
                                            ?: Cabinet(),
                                        organization = placeChoiceViewModel.currentOrganization.value
                                            ?: Organization()
                                    )
                                )
                            )
                        },
                        onNavigate = { eventRoute ->
                            navController.navigate(eventRoute.route)
                        },
                        onPopBack = {
                            navController.popBackStack()
                        })
                }

            } ?: NotLoginLinkView(navController)
        }
        composable(context.resources.getString(R.string.menu_add_route)) {
            val placeChoiceViewModel =
                it.sharedPlaceChoiceViewModel<PlaceChoiceViewModel>(
                    navController = navController,
                    user = userState.value,
                    startNextRoute = context.resources.getString(R.string.menu_add_route)
                )

            val userWithPlaceBundle = remember {
                mutableStateOf(
                    UserAndPlaceBundle(
                        user = userState.value!!,
                        branch = placeChoiceViewModel.selectedBranch.value!!,
                        building = placeChoiceViewModel.selectedBuilding.value!!,
                        cabinet = placeChoiceViewModel.selectedCabinet.value!!,
                        organization = placeChoiceViewModel.currentOrganization.value!!
                    )
                )
            }

            val menuViewModel = it.sharedAddDataMenuViewModel<CreateQRCodesViewModel>(
                navController = navController,
                userWithPlaceBundle = userWithPlaceBundle.value
            )
            Column(
                modifier = Modifier.padding(
                    0.dp,
                    0.dp,
                    0.dp,
                    NavBottomBarConstants.HEIGHT_BOTTOM_BAR
                )
            ) {
                MenuView(
                    userAndPlaceBundle = userWithPlaceBundle.value,
                    viewModel = menuViewModel,
                    onNavigate = { navigateEvent ->
                        navController.navigate(navigateEvent.route)
                    },
                    onChangePlaceClicked = {
                        placeChoiceViewModel.onEvent(
                            PlaceChoiceEvent.OnNextRouteDestinationChanged(
                                ""
                            )
                        )
                        menuViewModel.onEvent(CreateQRCodeEvent.OnChangePlaceClicked)
                    }
                )
            }


        }
        composable(context.resources.getString(R.string.add_concrete_class)) {
            val placeChoiceViewModel =
                it.sharedPlaceChoiceViewModel<PlaceChoiceViewModel>(
                    navController = navController,
                    user = userState.value
                )

            val userWithPlaceBundle = remember {
                mutableStateOf(
                    UserAndPlaceBundle(
                        user = userState.value!!,
                        branch = placeChoiceViewModel.selectedBranch.value!!,
                        building = placeChoiceViewModel.selectedBuilding.value!!,
                        cabinet = placeChoiceViewModel.selectedCabinet.value!!,
                        organization = placeChoiceViewModel.currentOrganization.value!!
                    )
                )
            }

            val menuViewModel = it.sharedAddDataMenuViewModel<CreateQRCodesViewModel>(
                navController = navController,
                userWithPlaceBundle = userWithPlaceBundle.value
            )

            Column(
                modifier = Modifier.padding(
                    0.dp,
                    0.dp,
                    0.dp,
                    NavBottomBarConstants.HEIGHT_BOTTOM_BAR
                )
            ) {
                CreateViewByAddType(
                    typeOfView = menuViewModel.chosenObjectClassState.value,
                    navBackStackEntry = it,
                    navController = navController,
                    snackbarHostState = snackbarHostState,
                    userWithPlaceBundle = userWithPlaceBundle.value,
                    onChangePlaceClicked = {
                        placeChoiceViewModel.onEvent(
                            PlaceChoiceEvent.OnNextRouteDestinationChanged(
                                ""
                            )
                        )
                        navController.navigate(context.resources.getString(R.string.place_choice_add))
                    }
                ) { qrCodeStickerInfo ->
                    menuViewModel.onEvent(CreateQRCodeEvent.OnAddNewObjectInList(qrCodeStickerInfo))
                }

            }

        }

        composable(context.resources.getString(R.string.qr_codes_list)) {
            val placeChoiceViewModel =
                it.sharedPlaceChoiceViewModel<PlaceChoiceViewModel>(
                    navController = navController,
                    user = userState.value
                )

            val userWithPlaceBundle = remember {
                mutableStateOf(
                    UserAndPlaceBundle(
                        user = userState.value!!,
                        branch = placeChoiceViewModel.selectedBranch.value!!,
                        building = placeChoiceViewModel.selectedBuilding.value!!,
                        cabinet = placeChoiceViewModel.selectedCabinet.value!!,
                        organization = placeChoiceViewModel.currentOrganization.value!!
                    )
                )
            }

            val menuViewModel = it.sharedAddDataMenuViewModel<CreateQRCodesViewModel>(
                navController = navController,
                userWithPlaceBundle = userWithPlaceBundle.value
            )

            val documentQRCodesViewModel =
                qrCodeDocumentViewModel<DocumentWithQRCodesViewModel>(listOfQRCodes = menuViewModel.listOfAddedQRCodes)
            Column(
                modifier = Modifier.padding(
                    0.dp,
                    0.dp,
                    0.dp,
                    NavBottomBarConstants.HEIGHT_BOTTOM_BAR
                )
            ) {
                QRCodeStickersView(viewModel = documentQRCodesViewModel)
            }


        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateViewByAddType(
    typeOfView: DatabaseObjectTypes,
    navBackStackEntry: NavBackStackEntry,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    userWithPlaceBundle: UserAndPlaceBundle,
    onChangePlaceClicked: () -> Unit,
    onAddObjectClicked: (QRCodeStickerInfo) -> Unit,

    ) {
    val context = LocalContext.current
    val isAddButtonEnabled = remember {
        mutableStateOf(false)
    }

    val isCameraOn = remember {
        mutableStateOf(false)
    }

    lateinit var viewModel: BaseAddObjectViewModel

    Scaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                when (typeOfView) {

                    DatabaseObjectTypes.CHAIR -> {
                        viewModel = addChairViewModel<AddChairViewModel>(
                            userAndPlaceBundle = userWithPlaceBundle,
                            chairForUpdate = null
                        )
                        AddChairView(
                            viewModel = viewModel as AddChairViewModel,
                            userAndPlaceBundle = userWithPlaceBundle,
                            isAllFieldsInsertedState = isAddButtonEnabled,
                            onNavigate = { navEvent ->
                                navController.navigate(navEvent.route)
                            },
                            snackbarHostState = snackbarHostState,
                            onChangePlaceClicked = onChangePlaceClicked,
                            isCameraOn = isCameraOn
                        )

                    }

                    DatabaseObjectTypes.PROJECTOR -> {
                        viewModel =
                            addProjectorViewModel<AddProjectorViewModel>(
                                userAndPlaceBundle = userWithPlaceBundle,
                                projectorForUpdate = null
                            )
                        AddProjectorView(
                            viewModel = viewModel as AddProjectorViewModel,
                            userAndPlaceBundle = userWithPlaceBundle,
                            isAllFieldsInsertedState = isAddButtonEnabled,
                            onNavigate = { navEvent ->
                                navController.navigate(navEvent.route)
                            },
                            snackbarHostState = snackbarHostState,
                            onChangePlaceClicked = onChangePlaceClicked,
                            isCameraOn = isCameraOn
                        )
                    }

                    DatabaseObjectTypes.DESK -> {
                        viewModel =
                            addDeskViewModel(
                                userAndPlaceBundle = userWithPlaceBundle,
                                deskForUpdate = null
                            )
                        AddDeskView(
                            viewModel = viewModel as AddDeskViewModel,
                            userAndPlaceBundle = userWithPlaceBundle,
                            isAllFieldsInsertedState = isAddButtonEnabled,
                            onNavigate = { navEvent ->
                                navController.navigate(navEvent.route)
                            },
                            snackbarHostState = snackbarHostState,
                            onChangePlaceClicked = onChangePlaceClicked,
                            isCameraOn = isCameraOn
                        )
                    }


                    DatabaseObjectTypes.SYSTEM_UNIT -> {
                        viewModel =
                            addSystemUnitViewModel(
                                userAndPlaceBundle = userWithPlaceBundle,
                                systemUnitForUpdate = null
                            )
                        AddSystemUnitView(
                            viewModel = viewModel as AddSystemUnitViewModel,
                            userAndPlaceBundle = userWithPlaceBundle,
                            isAllFieldsInsertedState = isAddButtonEnabled,
                            onNavigate = { navEvent ->
                                navController.navigate(navEvent.route)
                            },
                            snackbarHostState = snackbarHostState,
                            onChangePlaceClicked = onChangePlaceClicked,
                            isCameraOn = isCameraOn
                        )
                    }

                    DatabaseObjectTypes.MONITOR -> {
                        viewModel =
                            addMonitorViewModel(
                                userAndPlaceBundle = userWithPlaceBundle,
                                monitorForUpdate = null
                            )
                        AddMonitorView(
                            viewModel = viewModel as AddMonitorViewModel,
                            userAndPlaceBundle = userWithPlaceBundle,
                            isAllFieldsInsertedState = isAddButtonEnabled,
                            onNavigate = { navEvent ->
                                navController.navigate(navEvent.route)
                            },
                            snackbarHostState = snackbarHostState,
                            onChangePlaceClicked = onChangePlaceClicked,
                            isCameraOn = isCameraOn
                        )
                    }

                    DatabaseObjectTypes.KEYBOARD -> {
                        viewModel =
                            addKeyboardViewModel(
                                userAndPlaceBundle = userWithPlaceBundle,
                                keyboardForUpdate = null
                            )
                        AddKeyboardView(
                            viewModel = viewModel as AddKeyboardViewModel,
                            userAndPlaceBundle = userWithPlaceBundle,
                            isAllFieldsInsertedState = isAddButtonEnabled,
                            onNavigate = { navEvent ->
                                navController.navigate(navEvent.route)
                            },
                            snackbarHostState = snackbarHostState,
                            onChangePlaceClicked = onChangePlaceClicked,
                            isCameraOn = isCameraOn
                        )
                    }

                    DatabaseObjectTypes.UNKNOWN -> {
                        null
                    }


                }
                Spacer(modifier = Modifier.height(40.dp))
                if (!isCameraOn.value) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center

                    ) {
                        Button(enabled = isAddButtonEnabled.value,
                            onClick = {
                                viewModel.onEvent(
                                    AddNewObjectEvent.OnCabinetChanged(
                                        userWithPlaceBundle.cabinet.id
                                    )
                                )
                                viewModel.addObjectInDatabaseClicked(
                                    onAddObjectClicked = onAddObjectClicked
                                )
                            }) {
                            Text(text = "Add")
                        }
                        Spacer(modifier = Modifier.width(30.dp))
                        Button(onClick = {
                            navController.navigate(context.getString(R.string.menu_add_route))
                        }) {
                            Text(text = "Cancel")
                        }
                    }
                }
            }


        }
    }

}


//fun createConcreteAddViewModelFactory(typeOfViewModel: DatabaseObjectTypes): BaseAddViewModelFactory {
//    return when (typeOfViewModel) {
//        DatabaseObjectTypes.PERSON -> {
//            AddPersonViewModelFactory()
//        }
//
//        DatabaseObjectTypes.KEYBOARD -> {
//            AddPersonViewModelFactory()
//        }
//
//        DatabaseObjectTypes.MONITOR -> {
//            AddPersonViewModelFactory()
//        }
//
//        DatabaseObjectTypes.UNKNOWN -> {
//            AddPersonViewModelFactory()
//        }
//    }
//}