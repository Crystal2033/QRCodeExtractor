package com.crystal2033.qrextractor.scanner_feature.modify_concrete_object.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.chair.AddChairView
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.desk.AddDeskView
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.keyboard.AddKeyboardView
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.monitor.AddMonitorView
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.projector.AddProjectorView
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.system_unit.AddSystemUnitView
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.AddDataViewModels
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.chair.AddChairViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.desk.AddDeskViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.keyboard.AddKeyboardViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.monitor.AddMonitorViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.projector.AddProjectorViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.system_unit.AddSystemUnitViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication.AddNewObjectEvent
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.viewmodel.ScannedObjectsListViewModel
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.vm_view_communication.ScannedObjectsListEvent


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateViewByModifyType(
    scannedObjectsListViewModel: ScannedObjectsListViewModel,
    navBackStackEntry: NavBackStackEntry,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    onChangePlaceClicked: () -> Unit
) {
    val context = LocalContext.current
    val isAddButtonEnabled = remember {
        mutableStateOf(false)
    }

    val typeOfView by remember {
        scannedObjectsListViewModel.chosenObjectClassState
    }

    val deviceForUpdate by remember {
        scannedObjectsListViewModel.chosenDeviceState
    }

    val userAndPlaceBundle by remember {
        scannedObjectsListViewModel.userAndPlaceBundleState
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
                        viewModel = AddDataViewModels.addChairViewModel<AddChairViewModel>(
                            userAndPlaceBundle = userAndPlaceBundle,
                            chairForUpdate = deviceForUpdate
                        )
                        AddChairView(
                            viewModel = viewModel as AddChairViewModel,
                            userAndPlaceBundle = userAndPlaceBundle,
                            isAllFieldsInsertedState = isAddButtonEnabled,
                            onNavigate = { navEvent ->
                                navController.navigate(navEvent.route)
                            },
                            snackbarHostState = snackbarHostState,
                            onChangePlaceClicked = onChangePlaceClicked,
                            isForUpdate = true
                        )

                    }

                    DatabaseObjectTypes.PROJECTOR -> {
                        viewModel =
                            AddDataViewModels.addProjectorViewModel<AddProjectorViewModel>(
                                userAndPlaceBundle = userAndPlaceBundle,
                                projectorForUpdate = deviceForUpdate
                            )
                        AddProjectorView(
                            viewModel = viewModel as AddProjectorViewModel,
                            userAndPlaceBundle = userAndPlaceBundle,
                            isAllFieldsInsertedState = isAddButtonEnabled,
                            onNavigate = { navEvent ->
                                navController.navigate(navEvent.route)
                            },
                            snackbarHostState = snackbarHostState,
                            onChangePlaceClicked = {
                                onChangePlaceClicked()
                            },
                            isForUpdate = true
                        )
                    }

                    DatabaseObjectTypes.DESK -> {
                        viewModel =
                            AddDataViewModels.addDeskViewModel(
                                userAndPlaceBundle = userAndPlaceBundle,
                                deskForUpdate = deviceForUpdate
                            )
                        AddDeskView(
                            viewModel = viewModel as AddDeskViewModel,
                            userAndPlaceBundle = userAndPlaceBundle,
                            isAllFieldsInsertedState = isAddButtonEnabled,
                            onNavigate = { navEvent ->
                                navController.navigate(navEvent.route)
                            },
                            snackbarHostState = snackbarHostState,
                            onChangePlaceClicked = {
                                onChangePlaceClicked()
                            },
                            isForUpdate = true
                        )
                    }


                    DatabaseObjectTypes.SYSTEM_UNIT -> {
                        viewModel =
                            AddDataViewModels.addSystemUnitViewModel(
                                userAndPlaceBundle = userAndPlaceBundle,
                                systemUnitForUpdate = deviceForUpdate
                            )
                        AddSystemUnitView(
                            viewModel = viewModel as AddSystemUnitViewModel,
                            userAndPlaceBundle = userAndPlaceBundle,
                            isAllFieldsInsertedState = isAddButtonEnabled,
                            onNavigate = { navEvent ->
                                navController.navigate(navEvent.route)
                            },
                            snackbarHostState = snackbarHostState,
                            onChangePlaceClicked = {
                                onChangePlaceClicked()
                            },
                            isForUpdate = true
                        )
                    }

                    DatabaseObjectTypes.MONITOR -> {
                        viewModel =
                            AddDataViewModels.addMonitorViewModel(
                                userAndPlaceBundle = userAndPlaceBundle,
                                monitorForUpdate = deviceForUpdate
                            )
                        AddMonitorView(
                            viewModel = viewModel as AddMonitorViewModel,
                            userAndPlaceBundle = userAndPlaceBundle,
                            isAllFieldsInsertedState = isAddButtonEnabled,
                            onNavigate = { navEvent ->
                                navController.navigate(navEvent.route)
                            },
                            snackbarHostState = snackbarHostState,
                            onChangePlaceClicked = {
                                onChangePlaceClicked()
                            },
                            isForUpdate = true
                        )
                    }

                    DatabaseObjectTypes.KEYBOARD -> {
                        viewModel =
                            AddDataViewModels.addKeyboardViewModel(
                                userAndPlaceBundle = userAndPlaceBundle,
                                keyboardForUpdate = deviceForUpdate
                            )
                        AddKeyboardView(
                            viewModel = viewModel as AddKeyboardViewModel,
                            userAndPlaceBundle = userAndPlaceBundle,
                            isAllFieldsInsertedState = isAddButtonEnabled,
                            onNavigate = { navEvent ->
                                navController.navigate(navEvent.route)
                            },
                            snackbarHostState = snackbarHostState,
                            onChangePlaceClicked = {
                                onChangePlaceClicked()
                            },
                            isForUpdate = true
                        )
                    }

                    DatabaseObjectTypes.UNKNOWN -> {
                        null
                    }


                }
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ) {
                    Button(enabled = isAddButtonEnabled.value,
                        onClick = {
                            viewModel.onEvent(AddNewObjectEvent.OnCabinetChanged(userAndPlaceBundle.cabinet.id))
                            viewModel.addObjectInDatabaseClicked(
                                afterUpdateAction = {
                                    scannedObjectsListViewModel.onEvent(ScannedObjectsListEvent.Refresh)
                                }
                            )
                        }) {
                        Text(text = "Accept")
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    Button(onClick = {
                        navController.navigate(context.getString(R.string.list_of_scanned_objects))
                    }) {
                        Text(text = "Cancel")
                    }
                }
            }


        }
    }

}

