package com.crystal2033.qrextractor.nav_graphs.scanner

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.PlaceChoiceView
import com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.viewmodel.PlaceChoiceViewModel
import com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.viewmodel.PlaceViewModelCreator.Companion.sharedPlaceChoiceViewModel
import com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.vm_view_communication.PlaceChoiceEvent
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.presentation.NotLoginLinkView
import com.crystal2033.qrextractor.core.remote_server.data.model.Branch
import com.crystal2033.qrextractor.core.remote_server.data.model.Building
import com.crystal2033.qrextractor.core.remote_server.data.model.Cabinet
import com.crystal2033.qrextractor.core.remote_server.data.model.Organization
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.presentation.ScannedGroupsView
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.presentation.viewmodel.ScannedDataGroupsViewModel
import com.crystal2033.qrextractor.scanner_feature.modify_concrete_object.presentation.CreateViewByModifyType
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.ScannedObjectsListView
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.viewmodel.ScannedObjectsListViewModel
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.vm_view_communication.ScannedObjectsListEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.QRCodeView
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.viewmodel.QRCodeScannerViewModel
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.viewmodel.ScannerViewModels.Companion.qrCodeScannerViewModel
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.viewmodel.ScannerViewModels.Companion.sharedScannedDataGroupsViewModel
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.viewmodel.ScannerViewModels.Companion.sharedScannedObjectsListInGroupViewModel
import com.crystal2033.qrextractor.ui.NavBottomBarConstants


fun NavGraphBuilder.scannerGraph(
    navController: NavController,
    context: Context,
    snackbarHostState: SnackbarHostState,
    userState: State<User?>
) {
    navigation(
        startDestination = context.resources.getString(R.string.scanner_route),
        route = context.resources.getString(R.string.scanner_head_graph_route)
    ) {
        composable(context.resources.getString(R.string.scanner_route)) {
            userState.value?.let { user ->
                val viewModel =
                    it.qrCodeScannerViewModel<QRCodeScannerViewModel>(user = user)

                Column(
                    modifier = Modifier.padding(
                        0.dp,
                        0.dp,
                        0.dp,
                        NavBottomBarConstants.HEIGHT_BOTTOM_BAR
                    )
                ) {
                    QRCodeView(
                        viewModel = viewModel,
                        onNavigate = { navigationEvent ->
                            navController.navigate(navigationEvent.route)
                        },
                        snackbarHostState = snackbarHostState
                    )
                }
            } ?: NotLoginLinkView(navController)
        }

        composable(context.resources.getString(R.string.list_of_groups_route)) {
            val userAndPlaceBundle = remember {
                mutableStateOf(
                    UserAndPlaceBundle(
                        user = userState.value!!,
                    )
                )
            }

            val viewModelFromGroups =
                it.sharedScannedDataGroupsViewModel<ScannedDataGroupsViewModel>(
                    navController = navController,
                    user = userAndPlaceBundle.value.user
                )

            val viewModelScannedObjects =
                it.sharedScannedObjectsListInGroupViewModel<ScannedObjectsListViewModel>(
                    scannedGroup = viewModelFromGroups.chosenGroup,
                    navController = navController,
                    userAndPlaceBundle = userAndPlaceBundle.value
                )
            Column(
                modifier = Modifier.padding(
                    0.dp,
                    0.dp,
                    0.dp,
                    NavBottomBarConstants.HEIGHT_BOTTOM_BAR
                )
            ) {
                ScannedGroupsView(
                    viewModel = viewModelFromGroups,
                    onUpdateScannedListViewModelState = {
                        viewModelScannedObjects.onEvent(ScannedObjectsListEvent.Refresh)
                    },
                    onNavigate = { navigationEvent ->
                        navController.navigate(navigationEvent.route)
                    },
                    onPopBackStack = {
                        navController.popBackStack()
                    },
                    snackbarHostState = snackbarHostState
                )
            }
        }

        composable(context.resources.getString(R.string.list_of_scanned_objects)) {
            val userAndPlaceBundle = remember {
                mutableStateOf(
                    UserAndPlaceBundle(
                        user = userState.value!!,
                    )
                )
            }
            val viewModelFromGroups =
                it.sharedScannedDataGroupsViewModel<ScannedDataGroupsViewModel>(
                    navController = navController,
                    user = userAndPlaceBundle.value.user
                )

            val viewModel =
                it.sharedScannedObjectsListInGroupViewModel<ScannedObjectsListViewModel>(
                    scannedGroup = viewModelFromGroups.chosenGroup,
                    navController = navController,
                    userAndPlaceBundle = userAndPlaceBundle.value
                )
            Column(
                modifier = Modifier.padding(
                    0.dp,
                    0.dp,
                    0.dp,
                    NavBottomBarConstants.HEIGHT_BOTTOM_BAR
                )
            ) {
                ScannedObjectsListView(
                    viewModel = viewModel,
                    onNavigate = { navigationEvent ->
                        navController.navigate(navigationEvent.route)
                    },
                    snackbarHostState = snackbarHostState
                )
            }
        }
        composable(context.resources.getString(R.string.place_choice_update)) {
            if (userState.value == null) {
                NotLoginLinkView(navController)
            } else {
                val placeChoiceViewModel =
                    it.sharedPlaceChoiceViewModel<PlaceChoiceViewModel>(
                        navController = navController,
                        user = userState.value
                    )

                val viewModelFromGroups =
                    it.sharedScannedDataGroupsViewModel<ScannedDataGroupsViewModel>(
                        navController = navController,
                        user = userState.value
                    )

                val userAndPlaceBundle = remember {
                    mutableStateOf(
                        UserAndPlaceBundle(
                            user = userState.value!!,
                            branch = placeChoiceViewModel.selectedBranch.value ?: Branch(),
                            building = placeChoiceViewModel.selectedBuilding.value ?: Building(),
                            cabinet = placeChoiceViewModel.selectedCabinet.value ?: Cabinet(),
                            organization = placeChoiceViewModel.currentOrganization.value
                                ?: Organization()
                        )
                    )
                }

                val scannedListViewModel =
                    it.sharedScannedObjectsListInGroupViewModel<ScannedObjectsListViewModel>(
                        scannedGroup = viewModelFromGroups.chosenGroup,
                        navController = navController,
                        userAndPlaceBundle = userAndPlaceBundle.value
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
                            scannedListViewModel.onEvent(
                                ScannedObjectsListEvent.OnPlaceUpdate(
                                    UserAndPlaceBundle(
                                        user = userState.value!!,
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
            }
        }
        composable(context.resources.getString(R.string.modify_concrete_object)) {
            val viewModelFromGroups =
                it.sharedScannedDataGroupsViewModel<ScannedDataGroupsViewModel>(
                    navController = navController,
                    user = userState.value
                )

            val placeChoiceViewModel =
                it.sharedPlaceChoiceViewModel<PlaceChoiceViewModel>(
                    navController = navController,
                    user = userState.value
                )


            val userAndPlaceBundle = remember {
                mutableStateOf(
                    UserAndPlaceBundle(
                        user = userState.value!!,
                        branch = placeChoiceViewModel.selectedBranch.value ?: Branch(),
                        building = placeChoiceViewModel.selectedBuilding.value ?: Building(),
                        cabinet = placeChoiceViewModel.selectedCabinet.value ?: Cabinet(),
                        organization = placeChoiceViewModel.currentOrganization.value
                            ?: Organization()
                    )
                )
            }

            val scannedListViewModel =
                it.sharedScannedObjectsListInGroupViewModel<ScannedObjectsListViewModel>(
                    scannedGroup = viewModelFromGroups.chosenGroup,
                    navController = navController,
                    userAndPlaceBundle = userAndPlaceBundle.value
                )

            Column(
                modifier = Modifier.padding(
                    0.dp,
                    0.dp,
                    0.dp,
                    NavBottomBarConstants.HEIGHT_BOTTOM_BAR
                )
            ) {
                CreateViewByModifyType(
                    scannedObjectsListViewModel = scannedListViewModel,
                    navBackStackEntry = it,
                    snackbarHostState = snackbarHostState,
                    navController = navController,
                    onChangePlaceClicked = {
                        placeChoiceViewModel.onEvent(
                            PlaceChoiceEvent.OnLoadAllData(
                                branchId = scannedListViewModel.userAndPlaceBundleState.value.branch.id,
                                buildingId = scannedListViewModel.userAndPlaceBundleState.value.building.id,
                                cabinetId = scannedListViewModel.userAndPlaceBundleState.value.cabinet.id
                            )
                        )
                        placeChoiceViewModel.onEvent(
                            PlaceChoiceEvent.OnNextRouteDestinationChanged(
                                ""
                            )
                        )
                        navController.navigate(context.resources.getString(R.string.place_choice_update))
                    }
                )
            }
        }
    }
}
