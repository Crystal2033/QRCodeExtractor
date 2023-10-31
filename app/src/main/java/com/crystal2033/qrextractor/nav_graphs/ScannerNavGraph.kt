package com.crystal2033.qrextractor.nav_graphs

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.scanner_feature.presentation.QRCodeView
import com.crystal2033.qrextractor.scanner_feature.presentation.scanned_list.ScannedListView
import com.crystal2033.qrextractor.scanner_feature.presentation.viewmodel.QRCodeScannerViewModel
import com.crystal2033.qrextractor.sharedViewModel

fun NavGraphBuilder.scannerGraph(navController: NavController,
                                 context : Context,
                                 snackbarHostState: SnackbarHostState){
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

        composable(context.resources.getString(R.string.list_of_scanned_objects_route)) {
            val viewModel = it.sharedViewModel<QRCodeScannerViewModel>(navController)
            //TODO: probably here we need new viewmodel for list work with 
            ScannedListView(
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