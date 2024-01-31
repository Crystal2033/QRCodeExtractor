package com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation

import androidx.compose.runtime.Composable
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.list_items_view.StartObjectInfo
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.state.InventarizedObjectInfoAndIDInLocalDB
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.Unknown

@Composable
fun ShowListItemByType(
    objectInfoWithLocalDB: InventarizedObjectInfoAndIDInLocalDB,
    onObjectClicked: () -> Unit,
    onDeleteScannedObjectClicked: (Long) -> Unit
) {
    StartObjectInfo(
        scannedObjectWithCabinetName = objectInfoWithLocalDB,
        onObjectClicked = if (objectInfoWithLocalDB.objectInfo is Unknown) {
            {}
        } else onObjectClicked,
        onDeleteScannedObjectClicked = onDeleteScannedObjectClicked
    )
}