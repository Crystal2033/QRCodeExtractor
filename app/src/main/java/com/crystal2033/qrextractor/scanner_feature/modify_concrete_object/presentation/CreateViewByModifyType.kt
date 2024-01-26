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
import androidx.compose.runtime.State
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
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.AddDataViewModels
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.chair.AddChairViewModel
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.viewmodel.ScannedObjectsListViewModel
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.vm_view_communication.ScannedObjectsListEvent


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateViewByModifyType(
    scannedObjectsListViewModel: ScannedObjectsListViewModel,
    typeOfView: DatabaseObjectTypes,
    navBackStackEntry: NavBackStackEntry,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    userWithPlaceBundle: State<UserAndPlaceBundle>,
    deviceForUpdate: InventarizedAndQRScannableModel,
    onAddObjectClicked: (QRCodeStickerInfo) -> Unit = {}
) {
    val context = LocalContext.current
    val isAddButtonEnabled = remember {
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

                        viewModel = AddDataViewModels.addChairViewModel<AddChairViewModel>(
                            userAndPlaceBundle = userWithPlaceBundle.value,
                            chairForUpdate = deviceForUpdate
                        )
                        AddChairView(
                            viewModel = viewModel as AddChairViewModel,
                            isAllFieldsInsertedState = isAddButtonEnabled,
                            onNavigate = { navEvent ->
                                navController.navigate(navEvent.route)
                            },
                            snackbarHostState = snackbarHostState,
                            isForUpdate = true
                        )

                    }

                    DatabaseObjectTypes.PROJECTOR -> {
//                        viewModel =
//                            AddDataViewModels.addProjectorViewModel<AddProjectorViewModel>(
//                                userAndPlaceBundle = userWithPlaceBundle
//                            )
//                        AddProjectorView(
//                            viewModel = viewModel as AddProjectorViewModel,
//                            isAllFieldsInsertedState = isAddButtonEnabled,
//                            onNavigate = { navEvent ->
//                                navController.navigate(navEvent.route)
//                            },
//                            snackbarHostState = snackbarHostState
//                        )
                    }

                    DatabaseObjectTypes.DESK -> {
//                        viewModel =
//                            AddDataViewModels.addDeskViewModel(
//                                userAndPlaceBundle = userWithPlaceBundle
//                            )
//                        AddDeskView(
//                            viewModel = viewModel as AddDeskViewModel,
//                            isAllFieldsInsertedState = isAddButtonEnabled,
//                            onNavigate = { navEvent ->
//                                navController.navigate(navEvent.route)
//                            },
//                            snackbarHostState = snackbarHostState
//                        )
                    }


                    DatabaseObjectTypes.SYSTEM_UNIT -> {
//                        viewModel =
//                            AddDataViewModels.addSystemUnitViewModel(
//                                userAndPlaceBundle = userWithPlaceBundle
//                            )
//                        AddSystemUnitView(
//                            viewModel = viewModel as AddSystemUnitViewModel,
//                            isAllFieldsInsertedState = isAddButtonEnabled,
//                            onNavigate = { navEvent ->
//                                navController.navigate(navEvent.route)
//                            },
//                            snackbarHostState = snackbarHostState
//                        )
                    }

                    DatabaseObjectTypes.MONITOR -> {
//                        viewModel =
//                            AddDataViewModels.addMonitorViewModel(
//                                userAndPlaceBundle = userWithPlaceBundle
//                            )
//                        AddMonitorView(
//                            viewModel = viewModel as AddMonitorViewModel,
//                            isAllFieldsInsertedState = isAddButtonEnabled,
//                            onNavigate = { navEvent ->
//                                navController.navigate(navEvent.route)
//                            },
//                            snackbarHostState = snackbarHostState
//                        )
                    }

                    DatabaseObjectTypes.KEYBOARD -> {
//                        viewModel =
//                            AddDataViewModels.addKeyboardViewModel(
//                                userAndPlaceBundle = userWithPlaceBundle
//                            )
//                        AddKeyboardView(
//                            viewModel = viewModel as AddKeyboardViewModel,
//                            isAllFieldsInsertedState = isAddButtonEnabled,
//                            onNavigate = { navEvent ->
//                                navController.navigate(navEvent.route)
//                            },
//                            snackbarHostState = snackbarHostState
//                        )
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

