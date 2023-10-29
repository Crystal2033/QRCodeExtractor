package com.crystal2033.qrextractor.scanner_feature.presentation.scanned_list

import androidx.compose.runtime.Composable
import com.crystal2033.qrextractor.scanner_feature.domain.model.Keyboard
import com.crystal2033.qrextractor.scanner_feature.domain.model.Person
import com.crystal2033.qrextractor.scanner_feature.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.presentation.scanned_list.list_items_view.KeyboardListItem
import com.crystal2033.qrextractor.scanner_feature.presentation.scanned_list.list_items_view.PersonListItem

@Composable
fun ShowListItemByType(listItem: QRScannableData) {
    when (listItem){
        is Keyboard -> {
            KeyboardListItem(keyboard = listItem)
        }
        is Person -> {
            PersonListItem(person = listItem)
        }
    }
}