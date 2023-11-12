package com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.model.ScannedGroup
import com.crystal2033.qrextractor.scanner_feature.scanner.data.util.ScannedTableNameAndId

@Composable
fun ShowGroupIcon(
    scannedGroup: ScannedGroup,
    onScannedGroupClicked: () -> Unit
) {

    Column() {
        IconButton(
            onClick = onScannedGroupClicked,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(
//                modifier = Modifier
//                    .size(100.dp),
                imageVector = ImageVector.vectorResource(R.drawable.folder_100),
                contentDescription = "File with groups",
                tint = Color.White
            )

        }
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = scannedGroup.groupName ?: "Unknown name",
            textAlign = TextAlign.Center,
            color = Color.LightGray
        )

    }


}

@Composable
@Preview
fun PreviewCard() {
    val scannedTableNameAndId: List<ScannedTableNameAndId> =
        listOf(ScannedTableNameAndId("person", 1))
    val scannedGroup = ScannedGroup(1, "S", scannedTableNameAndId)
    ShowGroupIcon(scannedGroup) {
        Log.i(LOG_TAG_NAMES.INFO_TAG, "Group id: ${scannedGroup.id}")
    }

}