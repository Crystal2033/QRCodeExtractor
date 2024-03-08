package com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.list_items_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.state.InventarizedObjectInfoAndIDInLocalDB

@Composable
fun StartObjectInfo(
    scannedObjectWithCabinetName: InventarizedObjectInfoAndIDInLocalDB,
    modifier: Modifier = Modifier,
    onObjectClicked: () -> Unit,
    onDeleteScannedObjectClicked: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onObjectClicked()
            }
            .border(1.dp, Color.DarkGray),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        scannedObjectWithCabinetName.objectInfo.image?.let { image ->
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = "Image of object",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(85.dp)
                    .clip(RectangleShape)
                    .align(Alignment.CenterVertically)
                    .padding(10.dp),
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.name_translate) + ": ${scannedObjectWithCabinetName.objectInfo.name}",
                color = Color.LightGray,
                fontSize = 15.sp
            )
            Text(
                text = stringResource(id = R.string.cabinet_translate) + ": ${scannedObjectWithCabinetName.cabinetName}",
                color = Color.LightGray,
                fontSize = 15.sp
            )
        }

        IconButton(
            onClick = {
                onDeleteScannedObjectClicked(scannedObjectWithCabinetName.objectIdInLocalDB)
            },
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp)
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete scanned object",
                tint = Color.Red
            )
        }
    }
}