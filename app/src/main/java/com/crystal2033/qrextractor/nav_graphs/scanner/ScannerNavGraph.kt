package com.crystal2033.qrextractor.nav_graphs.scanner

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.auth_feature.presentation.viewmodel.UserHolderViewModel
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.nav_graphs.ViewModelWithoutUserParameters.Companion.sharedHiltViewModel
import com.crystal2033.qrextractor.nav_graphs.scanner.ScannerViewModels.Companion.scannedObjectsListInGroupViewModel
import com.crystal2033.qrextractor.nav_graphs.scanner.ScannerViewModels.Companion.sharedScannedDataGroupsViewModel
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.presentation.ScannedGroupsView
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.presentation.viewmodel.ScannedDataGroupsViewModel
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.ScannedObjectsListView
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.viewmodel.ScannedObjectsListViewModel
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.QRCodeView
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.viewmodel.QRCodeScannerViewModel
import com.crystal2033.qrextractor.sharedUserHolderViewModel
import com.crystal2033.qrextractor.ui.NavBottomBarConstants


fun NavGraphBuilder.scannerGraph(
    navController: NavController,
    context: Context,
    snackbarHostState: SnackbarHostState,
    stateUser: State<User?>
) {
    navigation(
        startDestination = context.resources.getString(R.string.scanner_route),
        route = context.resources.getString(R.string.scanner_head_graph_route)
    ) {
        composable(context.resources.getString(R.string.scanner_route)) {
            val user = stateUser.value
            Log.i(LOG_TAG_NAMES.INFO_TAG, "USER IS: ${user?.firstName} ${user?.secondName}")

            val viewModel = it.sharedHiltViewModel<QRCodeScannerViewModel>(navController)
            Column(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, NavBottomBarConstants.HEIGHT_BOTTOM_BAR)) {
                QRCodeView(
                    viewModel = viewModel,
                    onNavigate = { navigationEvent ->
                        navController.navigate(navigationEvent.route)
                    },
                    snackbarHostState = snackbarHostState
                )
            }
        }

        composable(context.resources.getString(R.string.list_of_groups_route)) {
            val user = stateUser.value
            val viewModel = it.sharedScannedDataGroupsViewModel<ScannedDataGroupsViewModel>(
                navController = navController,
                user = user
            )
            Column(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, NavBottomBarConstants.HEIGHT_BOTTOM_BAR)) {
                ScannedGroupsView(
                    viewModel = viewModel,
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
            val user = stateUser.value
            val viewModelFromGroups =
                it.sharedScannedDataGroupsViewModel<ScannedDataGroupsViewModel>(
                    navController = navController,
                    user = user
                )

            val viewModel = scannedObjectsListInGroupViewModel<ScannedObjectsListViewModel>(
                scannedGroup = viewModelFromGroups.chosenGroup.value
            )
            Column(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, NavBottomBarConstants.HEIGHT_BOTTOM_BAR)) {
                ScannedObjectsListView(
                    viewModel = viewModel,
                    onNavigate = { navigationEvent ->
                        navController.navigate(navigationEvent.route)

                    },
                    snackbarHostState = snackbarHostState
                )
            }

            Log.i(
                LOG_TAG_NAMES.INFO_TAG,
                "WE ARE FROM SCANNED OBJECTS size= ${viewModel.objectsListState.value.listOfObjects?.size}"
            )
        }
    }
}