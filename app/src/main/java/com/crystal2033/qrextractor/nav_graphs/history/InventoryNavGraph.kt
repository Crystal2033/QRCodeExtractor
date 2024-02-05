package com.crystal2033.qrextractor.nav_graphs.history

import android.content.Context
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
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.FileChoosingView
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.viewmodel.InventoryFileLoaderViewModel
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.viewmodel.InventoryViewModelsInitializer.Companion.sharedInventoryFileChoosingViewModel
import com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation.InventoryCheckView
import com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation.viewmodel.InventoryCheckViewModel
import com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation.viewmodel.InventoryVMExtension.Companion.inventoryViewModelWithFile
import com.crystal2033.qrextractor.ui.NavBottomBarConstants

fun NavGraphBuilder.inventoryGraph(
    navController: NavController,
    context: Context,
    snackbarHostState: SnackbarHostState,
    userState: State<User?>
) {
    navigation(
        startDestination = context.resources.getString(R.string.inventory_route),
        route = context.resources.getString(R.string.inventory_head_graph_route)
    ) {
        composable(context.resources.getString(R.string.inventory_route)) {
//            userState.value?.let { user ->
//                val viewModel =
//                    it.sharedInventoryFileChoosingViewModel<InventoryFileLoaderViewModel>(
//                        navController = navController
//                    )
//                Column(
//                    modifier = Modifier.padding(
//                        0.dp,
//                        0.dp,
//                        0.dp,
//                        NavBottomBarConstants.HEIGHT_BOTTOM_BAR
//                    )
//                ) {
//                    FileChoosingView(
//                        viewModel = viewModel,
//                        onNavigate = {
//                            navController.navigate(it.route)
//                        }
//                    )
//                }
//            } ?: NotLoginLinkView(navController)

            val viewModel =
                it.sharedInventoryFileChoosingViewModel<InventoryFileLoaderViewModel>(
                    navController = navController
                )
            Column(
                modifier = Modifier.padding(
                    0.dp,
                    0.dp,
                    0.dp,
                    NavBottomBarConstants.HEIGHT_BOTTOM_BAR
                )
            ) {
                FileChoosingView(
                    viewModel = viewModel,
                    onNavigate = { event ->
                        navController.navigate(event.route)
                    }
                )
            }

        }
        composable(context.resources.getString(R.string.inventory_list_check_route)) {
            val fileLoaderViewModel =
                it.sharedInventoryFileChoosingViewModel<InventoryFileLoaderViewModel>(
                    navController = navController
                )

            val inventoryCheckViewModel =
                it.inventoryViewModelWithFile<InventoryCheckViewModel>(
                    inventoryFile = fileLoaderViewModel.inventoryFile
                )
            Column(
                modifier = Modifier.padding(
                    0.dp,
                    0.dp,
                    0.dp,
                    NavBottomBarConstants.HEIGHT_BOTTOM_BAR
                )
            ) {
                InventoryCheckView(
                    viewModel = inventoryCheckViewModel,
                    onNavigate = { navEvent ->
                        navController.navigate(navEvent.route)
                    }
                )
            }
        }
    }
}