package com.crystal2033.qrextractor.nav_graphs.scanner

import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.nav_graphs.scanner.ScannerViewModels.Companion.sharedScannedDataGroupsViewModel
import com.crystal2033.qrextractor.nav_graphs.ViewModelWithoutUserParameters.Companion.sharedViewModel
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.presentation.ScannedGroupsView
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.presentation.viewmodel.ScannedDataGroupsViewModel
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.QRCodeView
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.viewmodel.QRCodeScannerViewModel


fun NavGraphBuilder.scannerGraph(
    navController: NavController,
    context: Context,
    snackbarHostState: SnackbarHostState,
    user: User
) {
    navigation(
        startDestination = context.resources.getString(R.string.scanner_route),
        route = context.resources.getString(R.string.scanner_head_graph_route)
    ) {
        composable(context.resources.getString(R.string.scanner_route)) {
            val viewModel = it.sharedViewModel<QRCodeScannerViewModel>(navController)

            QRCodeView(
                viewModel = viewModel,
                onNavigate = { navigationEvent ->
                    navController.navigate(navigationEvent.route)
                },
                snackbarHostState = snackbarHostState
            )
        }

        composable(context.resources.getString(R.string.list_of_groups_route)) {
            val viewModel = it.sharedScannedDataGroupsViewModel<ScannedDataGroupsViewModel>(
                navController = navController,
                user = user
            )

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

        composable(context.resources.getString(R.string.list_of_scanned_objects)){
//            val viewModel = it.sharedScannedDataGroupsViewModel<ScannedDataGroupsViewModel>(
//                navController = navController,
//                user = user
//            )
            Log.i(LOG_TAG_NAMES.INFO_TAG, "WE ARE FROM SCANNED OBJECTS")
        }
    }
}