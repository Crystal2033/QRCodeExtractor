package com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation

import androidx.compose.runtime.Composable
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.list_items_view.StartObjectInfo

@Composable
fun ShowListItemByType(
    listItem: InventarizedAndQRScannableModel,
    onObjectClicked: () -> Unit
) {
    StartObjectInfo(
        scannedObject = listItem,
        onObjectClicked = onObjectClicked
    )

//    when (listItem) {
//        is Keyboard -> {
//            StartObjectInfo(
//                image = listItem.image,
//                text = listItem.model,
//                onObjectClicked = onObjectClicked
//            )
//        }
//    }
}