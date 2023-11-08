package com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation

import androidx.compose.runtime.Composable
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.Keyboard
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.Person
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation.list_items_view.KeyboardListItem
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation.list_items_view.PersonListItem

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