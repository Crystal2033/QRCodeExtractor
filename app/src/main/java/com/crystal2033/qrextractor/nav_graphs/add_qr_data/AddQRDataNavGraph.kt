package com.crystal2033.qrextractor.nav_graphs.add_qr_data

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.TextWindow
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.MenuView
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel.CreateQRCodesViewModel
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel.factory.AddPersonViewModelFactory
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel.factory.BaseAddViewModelFactory
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.nav_graphs.add_qr_data.AddDataViewModels.Companion.sharedAddDataMenuViewModel

fun NavGraphBuilder.addQRCodeGraph(
    navController: NavController,
    context: Context,
    snackbarHostState: SnackbarHostState,
    user: User
) {
    navigation(
        startDestination = context.resources.getString(R.string.menu_add_route),
        route = context.resources.getString(R.string.add_data_head_graph_route)
    ) {
        composable(context.resources.getString(R.string.menu_add_route)) {
            val menuViewModel = it.sharedAddDataMenuViewModel<CreateQRCodesViewModel>(
                navController = navController,
                user = user
            )
            MenuView(
                viewModel = menuViewModel,
                onNavigate = { navigateEvent ->
                    navController.navigate(navigateEvent.route)
                }
            )
        }
        composable(context.resources.getString(R.string.add_concrete_class)) {
            val menuViewModel = it.sharedAddDataMenuViewModel<CreateQRCodesViewModel>(
                navController = navController,
                user = user
            )
            val baseAddViewModelFactory: BaseAddViewModelFactory =
                createConcreteAddViewModelFactory(menuViewModel.chosenObjectClassState.value)

            val addViewModel = baseAddViewModelFactory.createAddObjectViewModel(
                user = user,
                navBackStackEntry = it,
                navController = navController
            )

            TextWindow(
                string = "CONCRETE OBJECT: ${
                    menuViewModel.chosenObjectClassState.value.getLabel(
                        context
                    )
                }"
            )
        }
    }


}


fun createConcreteAddViewModelFactory(typeOfViewModel: DatabaseObjectTypes): BaseAddViewModelFactory {
    return when (typeOfViewModel) {
        DatabaseObjectTypes.PERSON -> {
            AddPersonViewModelFactory()
        }

        DatabaseObjectTypes.KEYBOARD -> {
            AddPersonViewModelFactory()
        }

        DatabaseObjectTypes.MONITOR -> {
            AddPersonViewModelFactory()
        }

        DatabaseObjectTypes.UNKNOWN -> {
            AddPersonViewModelFactory()
        }
    }
}