package com.crystal2033.qrextractor.nav_graphs.add_qr_data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.person.AddPersonView
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.person.AddPersonViewModel
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.MenuView
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel.CreateQRCodesViewModel
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.vm_view_communication.CreateQRCodeEvent
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.QRCodeStickersView
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.viewmodel.DocumentWithQRCodesViewModel
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.nav_graphs.add_qr_data.AddDataViewModels.Companion.addPersonViewModel
import com.crystal2033.qrextractor.nav_graphs.add_qr_data.AddDataViewModels.Companion.qrCodeDocumentViewModel
import com.crystal2033.qrextractor.nav_graphs.add_qr_data.AddDataViewModels.Companion.sharedAddDataMenuViewModel
import com.crystal2033.qrextractor.ui.NavBottomBarConstants

fun NavGraphBuilder.addQRCodeGraph(
    navController: NavController,
    context: Context,
    snackbarHostState: SnackbarHostState,
    user: User?
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
            Column(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, NavBottomBarConstants.HEIGHT_BOTTOM_BAR)) {
                MenuView(
                    viewModel = menuViewModel,
                    onNavigate = { navigateEvent ->
                        navController.navigate(navigateEvent.route)
                    }
                )
            }


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
            Column(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, NavBottomBarConstants.HEIGHT_BOTTOM_BAR)) {
                createViewByAddType(
                    typeOfView = menuViewModel.chosenObjectClassState.value,
                    navBackStackEntry = it,
                    navController = navController,
                    user = user
                ) { qrCodeStickerInfo ->
                    menuViewModel.onEvent(CreateQRCodeEvent.OnAddNewObjectInList(qrCodeStickerInfo))
                }

            }

        }

        composable(context.resources.getString(R.string.qr_codes_list)){
            val menuViewModel = it.sharedAddDataMenuViewModel<CreateQRCodesViewModel>(
                navController = navController,
                user = user
            )
            val documentQRCodesViewModel = qrCodeDocumentViewModel<DocumentWithQRCodesViewModel>(listOfQRCodes = menuViewModel.listOfAddedQRCodes)
            Column(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, NavBottomBarConstants.HEIGHT_BOTTOM_BAR)) {
                QRCodeStickersView(viewModel = documentQRCodesViewModel)
            }


        }
    }


}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun createViewByAddType(
    typeOfView: DatabaseObjectTypes,
    navBackStackEntry: NavBackStackEntry,
    navController: NavController,
    user: User?,
    onAddObjectClicked: (QRCodeStickerInfo) -> Unit
) {
    val context = LocalContext.current
    val isAddButtonEnabled = remember {
        mutableStateOf(false)
    }

    lateinit var viewModel: BaseAddObjectViewModel

    Scaffold {
        Box() {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())

            ) {
                when (typeOfView) {
                    DatabaseObjectTypes.PERSON -> {
                        viewModel = addPersonViewModel<AddPersonViewModel>(
                            user = user
                        )
                        AddPersonView(
                            viewModel = viewModel as AddPersonViewModel,
                            isAllFieldsInsertedState = isAddButtonEnabled,
                            onNavigate = { navEvent ->
                                navController.navigate(navEvent.route)
                            }
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

                    DatabaseObjectTypes.DESK -> TODO()
                    DatabaseObjectTypes.CHAIR -> TODO()
                    DatabaseObjectTypes.SYSTEM_UNIT -> TODO()
                    DatabaseObjectTypes.PROJECTOR -> TODO()
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    Button(enabled = isAddButtonEnabled.value,
                        onClick = {
                            viewModel.addObjectInDatabaseClicked(onAddObjectClicked)
                        }) {
                        Text(text = "Add")
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    Button(onClick = {
                        navController.navigate(context.getString(R.string.menu_add_route))
                    }) {
                        Text(text = "Cancel")
                    }
                }
            }


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