package com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.inventarization.ObjectInInventarizedFile

@Composable
fun CheckingObjectView(checkingObject: ObjectInInventarizedFile) {
    val colorOfBackground =
        if (checkingObject.factQuantityAndPosInExcel.fieldValue == checkingObject.accountantQuantity)
            Brush.linearGradient(
                0.0f to Color(103, 197, 111),
                0.5f to Color(73, 138, 78),
                1.0f to Color(32, 87, 37)
            )
        else
            Brush.linearGradient(
                0.0f to Color(77, 129, 182),
                0.5f to Color(54, 100, 146),
                1.0f to Color(10, 71, 132)
            )



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorOfBackground),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ColumnWithFieldNameAndValue(
                stringResource(id = R.string.number_translate),
                checkingObject.ordNumber.toString()
            )
            Spacer(modifier = Modifier.height(8.dp))
            ColumnWithFieldNameAndValue(
                stringResource(id = R.string.name_translate),
                checkingObject.objectName
            )
            Spacer(modifier = Modifier.height(8.dp))
            ColumnWithFieldNameAndValue(
                stringResource(id = R.string.inventory_number_translate),
                checkingObject.invNumber
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ColumnWithFieldNameAndValue(
                stringResource(id = R.string.price_per_one_translate),
                checkingObject.pricePerOne.toString()
            )
            Spacer(modifier = Modifier.height(8.dp))
            ColumnWithFieldNameAndValue(
                stringResource(id = R.string.full_acc_price_translate),
                checkingObject.accountantPrice.toString()
            )
            Spacer(modifier = Modifier.height(8.dp))
            ColumnWithFieldNameAndValue(
                stringResource(id = R.string.current_price),
                checkingObject.factPriceAndPosInExcel.fieldValue.toString()
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.scanned_translate) + "(${checkingObject.factQuantityAndPosInExcel.fieldValue}/${checkingObject.accountantQuantity})",
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Icon(
                imageVector =
                if (checkingObject.factQuantityAndPosInExcel.fieldValue == checkingObject.accountantQuantity)
                    ImageVector.vectorResource(R.drawable.success_check_35)
                else ImageVector.vectorResource(
                    R.drawable.circle_35
                ),
                contentDescription = "Check status icon",
                tint = if (checkingObject.factQuantityAndPosInExcel.fieldValue == checkingObject.accountantQuantity)
                    Color.Green
                else
                    Color.Gray
            )
        }
    }
}