package com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.util.GetStringNotInComposable

@Composable
fun MenuItem(
    item: DatabaseObjectTypes,
    onItemClicked: () -> Unit
) {
    val context = LocalContext.current
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
            text = getGoodNameByDatabaseObjectType(item, context),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

fun getGoodNameByDatabaseObjectType(item: DatabaseObjectTypes, context: Context): String {
    return when (item) {
        DatabaseObjectTypes.KEYBOARD -> {
            GetStringNotInComposable(context, R.string.keyboard_translate)
        }

        DatabaseObjectTypes.MONITOR -> {
            GetStringNotInComposable(context, R.string.monitor_translate)
        }

        DatabaseObjectTypes.UNKNOWN -> {
            GetStringNotInComposable(context, R.string.unknown_translate)
        }

        DatabaseObjectTypes.DESK -> {
            GetStringNotInComposable(context, R.string.desk_translate)
        }

        DatabaseObjectTypes.CHAIR -> {
            GetStringNotInComposable(context, R.string.chair_translate)
        }

        DatabaseObjectTypes.SYSTEM_UNIT -> {
            GetStringNotInComposable(context, R.string.system_unit_translate)
        }

        DatabaseObjectTypes.PROJECTOR -> {
            GetStringNotInComposable(context, R.string.projector_translate)
        }
    }
}