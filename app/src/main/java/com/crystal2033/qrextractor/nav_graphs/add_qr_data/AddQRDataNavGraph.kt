package com.crystal2033.qrextractor.nav_graphs.add_qr_data

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.TextWindow
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.person.AddPersonView
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.person.AddPersonViewModel
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.MenuView
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel.CreateQRCodesViewModel
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.vm_view_communication.CreateQRCodeEvent
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.nav_graphs.add_qr_data.AddDataViewModels.Companion.addPersonViewModel
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
//            val baseAddViewModelFactory: BaseAddViewModelFactory =
//                createConcreteAddViewModelFactory(menuViewModel.chosenObjectClassState.value)
//
//            val addViewModel1 = baseAddViewModelFactory.createAddObjectViewModel(
//                user = user,
//                navBackStackEntry = it,
//                navController = navController
//            )

            val addViewModel = createViewByAddType(
                typeOfViewModel = menuViewModel.chosenObjectClassState.value,
                navBackStackEntry = it,
                navController = navController,
                user = user
            ) { qrCodeStickerInfo ->
                menuViewModel.onEvent(CreateQRCodeEvent.OnAddNewObjectInList(qrCodeStickerInfo))
            }


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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun createViewByAddType(
    typeOfViewModel: DatabaseObjectTypes,
    navBackStackEntry: NavBackStackEntry,
    navController: NavController,
    user: User,
    onAddObjectClicked: (QRCodeStickerInfo) -> Unit
) {
    val context = LocalContext.current
    val isAddButtonEnabled = remember {
        mutableStateOf(false)
    }

    val qrCodeStickerInfoState by remember {
        mutableStateOf(QRCodeStickerInfo())
    }

    Scaffold {
        Box() {
            when (typeOfViewModel) {
                DatabaseObjectTypes.PERSON -> {
                    val viewModel = addPersonViewModel<AddPersonViewModel>(
                        user = user
                    )
                    AddPersonView(
                        viewModel = viewModel,
                        qrCodeStickerInfo = qrCodeStickerInfoState
                    )
                }

                DatabaseObjectTypes.KEYBOARD -> {
                    null
                }

                DatabaseObjectTypes.MONITOR -> {
                    null
                }

                DatabaseObjectTypes.UNKNOWN -> {
                    null
                }
            }

//            Row(modifier = Modifier.fillMaxWidth()) {
//                Button(enabled = isAddButtonEnabled.value,
//                    onClick = {
//                        onAddObjectClicked(qrCodeStickerInfoState)
//                    }) {
//                    Text(text = "Add")
//                }
//                Button(onClick = {
//                    navController.navigate(context.getString(R.string.menu_add_route))
//                }) {
//                    Text(text = "Cancel")
//                }
//            }
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