package com.crystal2033.qrextractor.scanner_feature.scanner.presentation.uiItems.preview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel

@Composable
fun ScannedObjectPreview(
    device: InventarizedAndQRScannableModel,
    cabinetName: String,
    organizationName: String,
    buildingAddress: String,
    branchName: String
) {
    Surface(
        color = Color(0xff1c1b1f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = device.getDatabaseTableName().name.uppercase(),
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column {
                    device.image?.let { bitmap ->
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ShowId(
                        device.id ?: 0,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    FieldNameAndValue(
                        "Name",
                        device.name,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FieldNameAndValue(
                        "Inventory number",
                        device.inventoryNumber
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        FieldNameAndValue(
                            "Organization",
                            organizationName
                        )
                        FieldNameAndValue(
                            "Branch",
                            branchName
                        )
                        FieldNameAndValue(
                            "Building",
                            buildingAddress
                        )
                        FieldNameAndValue(
                            "Cabinet",
                            cabinetName
                        )
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.Center
//                        ) {
//
//                        }
                    }


                }

            }
        }


    }
}