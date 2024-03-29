package com.crystal2033.qrextractor.scanner_feature.scanner.presentation.uiItems.preview

import FieldNameAndValue
import ShowId
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.getGoodNameByDatabaseObjectType
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel

@Composable
fun ScannedObjectPreview(
    device: InventarizedAndQRScannableModel,
    cabinetName: String,
    organizationName: String,
    buildingAddress: String,
    branchName: String,
    onDeleteDevice: (InventarizedAndQRScannableModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Surface(
        color = Color(0xff1c1b1f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Text(
                text = getGoodNameByDatabaseObjectType(device.getDatabaseTableName(), context),
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
                        stringResource(id = R.string.name_translate),
                        device.name,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FieldNameAndValue(
                        stringResource(id = R.string.inventory_number_translate),
                        device.inventoryNumber
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        FieldNameAndValue(
                            stringResource(id = R.string.organization_translate),
                            organizationName
                        )
                        FieldNameAndValue(
                            stringResource(id = R.string.branch_translate),
                            branchName
                        )
                        FieldNameAndValue(
                            stringResource(id = R.string.building_translate),
                            buildingAddress
                        )
                        FieldNameAndValue(
                            stringResource(id = R.string.cabinet_translate),
                            cabinetName
                        )
                    }

                }

            }
        }


    }
}