package com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel.CreateQRCodesViewModel
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.vm_view_communication.CreateQRCodeEvent
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.vm_view_communication.UICreateQRCodeEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication.QRScannerEvent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MenuView(
    viewModel: CreateQRCodesViewModel,
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
                    menuListWithAvailableTypes.groupBy { type -> type.getLabel(context).uppercase()[0] }
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
                                MenuItem(item = typeName) {
                                    viewModel.onEvent(CreateQRCodeEvent.SetChosenObjectClass(typeName))
                                }
                                Spacer(modifier = Modifier.height(10.dp))

                            }
                        }
                }
            }



        }
    }
}