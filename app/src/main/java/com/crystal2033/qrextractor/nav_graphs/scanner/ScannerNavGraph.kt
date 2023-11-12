package com.crystal2033.qrextractor.nav_graphs.scanner

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.User
import com.crystal2033.qrextractor.nav_graphs.scanner.ScannerViewModels.Companion.sharedScannedDataGroupsViewModel
import com.crystal2033.qrextractor.nav_graphs.ViewModelWithoutUserParameters.Companion.sharedViewModel
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation.ScannedGroupsView
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation.viewmodel.ScannedDataGroupsViewModel
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
            val viewModel2 = it.sharedScannedDataGroupsViewModel<ScannedDataGroupsViewModel>(
                navController = navController,
                user = user
            )
//            viewModel2.printUserAddress()
//            Log.i(
//                LOG_TAG_NAMES.INFO_TAG,
//                "GOT VIEWMODEL=${System.identityHashCode(viewModel2)}"
//            )

            QRCodeView(
                viewModel = viewModel,
                onNavigate = { navigationEvent ->
                    navController.navigate(navigationEvent.route)
                },
                snackbarHostState = snackbarHostState
            )
        }

        composable(context.resources.getString(R.string.list_of_scanned_objects_route)) {
            //val viewModel = scannedDataGroupsViewModel(user = userViewModel)
            val viewModel = it.sharedScannedDataGroupsViewModel<ScannedDataGroupsViewModel>(
                navController = navController,
                user = user
            )
//            viewModel.printUserAddress()
//            Log.i(
//                LOG_TAG_NAMES.INFO_TAG,
//                "GOT VIEWMODEL=${System.identityHashCode(viewModel)}"
//            )
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
}