package com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.inventarization.ObjectInInventarizedFile

@Composable
fun CheckingObjectView(checkingObject: ObjectInInventarizedFile) {
    val colorOfBackground = remember {
        mutableStateOf(Color.DarkGray)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorOfBackground.value),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ColumnWithFieldNameAndValue("Number", checkingObject.ordNumber.toString())
            Spacer(modifier = Modifier.height(8.dp))
            ColumnWithFieldNameAndValue("Name", checkingObject.objectName)
            Spacer(modifier = Modifier.height(8.dp))
            ColumnWithFieldNameAndValue("Inventory number", checkingObject.invNumber)
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ColumnWithFieldNameAndValue("Price per one", checkingObject.pricePerOne.toString())
            Spacer(modifier = Modifier.height(8.dp))
            ColumnWithFieldNameAndValue("Full price", checkingObject.accountantPrice.toString())
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Scanned (${checkingObject.factQuantityAndPosInExcel.fieldValue}/${checkingObject.accountantQuantity})",
                fontSize = 12.sp)
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