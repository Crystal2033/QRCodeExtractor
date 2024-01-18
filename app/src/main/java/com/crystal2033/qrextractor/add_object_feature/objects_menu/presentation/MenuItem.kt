package com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes

@Composable
fun MenuItem(
    item: DatabaseObjectTypes,
    onItemClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                onItemClicked()
            }
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = getGoodNameByDatabaseObjectType(item),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

fun getGoodNameByDatabaseObjectType(item: DatabaseObjectTypes): String {
    return when (item) {
        DatabaseObjectTypes.PERSON -> {
            "Person"
        }

        DatabaseObjectTypes.KEYBOARD -> {
            "Keyboard"
        }

        DatabaseObjectTypes.MONITOR -> {
            "Monitor"
        }

        DatabaseObjectTypes.UNKNOWN -> {
            "Unknown"
        }

        DatabaseObjectTypes.DESK -> {
            "Desk"
        }

        DatabaseObjectTypes.CHAIR -> {
            "Chair"
        }

        DatabaseObjectTypes.SYSTEM_UNIT -> {
            "System unit"
        }

        DatabaseObjectTypes.PROJECTOR -> {
            "Projector"
        }
    }
}