package com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation

import androidx.compose.runtime.Composable
import com.crystal2033.qrextractor.core.model.Keyboard
import com.crystal2033.qrextractor.core.model.Person
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.list_items_view.StartObjectInfo
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData

@Composable
fun ShowListItemByType(listItem: QRScannableData) {
    when (listItem) {
        is Keyboard -> {
            StartObjectInfo(
                image = listItem.image,
                text = listItem.model
            )
        }

        is Person -> {
            StartObjectInfo(
                image = listItem.image,
                text = "${listItem.firstName} ${listItem.secondName}"
            )
        }
    }
}