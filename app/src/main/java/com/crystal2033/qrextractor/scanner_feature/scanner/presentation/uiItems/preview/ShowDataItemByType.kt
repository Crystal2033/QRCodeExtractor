package com.crystal2033.qrextractor.scanner_feature.scanner.presentation.uiItems.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.Unknown

@Composable
fun ShowDataItemByType(
    qrScannable: InventarizedAndQRScannableModel?,
    modifier: Modifier = Modifier,
    onAddObjectIntoListClicked: () -> Unit,
    userAndPlaceBundle: UserAndPlaceBundle,
    onDeleteDevice: (InventarizedAndQRScannableModel) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        qrScannable?.let { existingScannable ->
            when (existingScannable) {
                is Unknown -> {
                    UnknownItem(unknownDataInfo = existingScannable)
                }

                else -> {
                    ScannedObjectPreview(
                        device = existingScannable,
                        organizationName = userAndPlaceBundle.organization.name,
                        branchName = userAndPlaceBundle.branch.name,
                        buildingAddress = userAndPlaceBundle.building.address,
                        cabinetName = userAndPlaceBundle.cabinet.name,
                        onDeleteDevice = onDeleteDevice,
                        //modifier = modifier
                    )

                    Button(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                        ,
                        onClick = onAddObjectIntoListClicked,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(106, 158, 129))
                    ) {
                        Text(
                            text = stringResource(id = R.string.add_in_list_translate),
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
//Кресло Aurora
//ABCD1234

//BENQ GW2765
//QWER8942
                    IconButton(
                        onClick = {
                            onDeleteDevice(existingScannable)
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.delete_forever_35),
                            contentDescription = "Delete",
                            tint = Color.White,
                            modifier = Modifier
                                .background(Color.Red)
                        )
                    }
                }
            }


        }

    }

}