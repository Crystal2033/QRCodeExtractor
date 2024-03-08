package com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel.CreateQRCodesViewModel
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.vm_view_communication.CreateQRCodeEvent
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.vm_view_communication.UICreateQRCodeEvent
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MenuView(
    userAndPlaceBundle: UserAndPlaceBundle,
    viewModel: CreateQRCodesViewModel,
    onChangePlaceClicked: () -> Unit,
    onNavigate: (UICreateQRCodeEvent.Navigate) -> Unit
) {

    val context = LocalContext.current

    val listOfAddedQRStickers = remember {
        viewModel.listOfAddedQRCodes
    }

    val menuListWithAvailableTypes = remember {
        viewModel.menuListState
    }

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UICreateQRCodeEvent.Navigate -> {
                    onNavigate(event)
                }
            }
        }
    }

    Scaffold {
        Box(modifier = Modifier.fillMaxSize()) {

            Column {
                Column {
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = stringResource(id = R.string.change_place_translate),
                        color = Color.Cyan,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .clickable {
                                onChangePlaceClicked()
                            }
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = stringResource(id = R.string.branch_translate), fontSize = 13.sp, color = Color.LightGray)
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = viewModel.branchName.value, fontSize = 15.sp)
                        }
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = stringResource(id = R.string.building_translate), fontSize = 13.sp, color = Color.LightGray)
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = viewModel.buildingAddress.value, fontSize = 15.sp)
                        }
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = stringResource(id = R.string.cabinet_translate), fontSize = 13.sp, color = Color.LightGray)
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = viewModel.cabinetName.value, fontSize = 15.sp)
                        }


                    }
                }

                IconButton(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(0.dp, 5.dp),
                    enabled = listOfAddedQRStickers.isNotEmpty(),
                    onClick = {
                        viewModel.onEvent(CreateQRCodeEvent.OnQRCodesListClicked)
                    }
                ) {
                    BadgedBox(
                        badge = {
                            if (listOfAddedQRStickers.isNotEmpty()) {
                                Badge {
                                    Text(
                                        text = listOfAddedQRStickers.size.toString()
                                    )
                                }
                            }
                        })
                    {
                        Icon(
                            ImageVector.vectorResource(R.drawable.qr_code_sticker),
                            contentDescription = "List"
                        )
                    }

                }


                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    menuListWithAvailableTypes.groupBy { type ->
                        type.getTranslatedLabel(context).uppercase()[0]
                    }
                        .forEach { (firstLetter, objectType) ->
                            stickyHeader {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.DarkGray),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = firstLetter.toString(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 30.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(15.dp))
                            }


                            items(objectType) { typeName ->
                                //Spacer(modifier = Modifier.height(10.dp))
                                Divider(color = Color.DarkGray, thickness = 1.dp)
                                MenuItem(item = typeName) {
                                    viewModel.onEvent(
                                        CreateQRCodeEvent.SetChosenObjectClass(
                                            typeName
                                        )
                                    )
                                }
                                Divider(color = Color.DarkGray, thickness = 1.dp)
                                Spacer(modifier = Modifier.height(10.dp))

                            }
                        }
                }
            }


        }
    }
}