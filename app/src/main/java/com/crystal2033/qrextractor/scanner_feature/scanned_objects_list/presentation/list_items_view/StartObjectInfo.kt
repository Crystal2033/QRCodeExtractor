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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.state.InventarizedObjectInfoAndIDInLocalDB

@Composable
fun StartObjectInfo(
    scannedObjectWithCabinetName: InventarizedObjectInfoAndIDInLocalDB,
    modifier: Modifier = Modifier,
    onObjectClicked: () -> Unit,
    onDeleteScannedObjectClicked: (Long) -> Unit
) {
    val context = LocalContext.current

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
                    .size(80.dp)
                    .clip(RectangleShape)
                    .align(Alignment.CenterVertically)
                    .padding(10.dp),
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Name: ${scannedObjectWithCabinetName.objectInfo.name}",
                color = Color.LightGray,
                fontSize = 15.sp
            )
            Text(
                text = "Cabinet: ${scannedObjectWithCabinetName.cabinetName}",
                color = Color.LightGray,
                fontSize = 15.sp
            )
        }

        Text(
            text = "ID: ${scannedObjectWithCabinetName.objectInfo.id}  LID: ${scannedObjectWithCabinetName.objectIdInLocalDB}",
            color = Color.LightGray,
            fontSize = 17.sp
        )
        IconButton(
            onClick = {
                onDeleteScannedObjectClicked(scannedObjectWithCabinetName.objectIdInLocalDB)
            },
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete scanned object",
                tint = Color.Red
            )
        }
    }


}


//@RequiresApi(Build.VERSION_CODES.O)
//@Preview
//@Composable
//fun StartObjectInfoPreview() {
//    val bitmap = BitmapFactory.decodeFile("D:\\Картинки\\blue-space.jpg")
//    val keyboard = Keyboard(
//        id = 1,
//        image = bitmap.asImageBitmap(),
//        model = "Corsair M80-411B-20",
//        startUseDate = LocalDate.now(),
//        workSpace = WorkSpace(5)
//    )
//    StartObjectInfo(
//        image = keyboard.image,
//        text = keyboard.model,
//        onObjectClicked = {
//            Log.i(LOG_TAG_NAMES.INFO_TAG, "Clicked on scanned object ${keyboard.javaClass.simpleName} ${keyboard.id}")
//        }
//    )
//}