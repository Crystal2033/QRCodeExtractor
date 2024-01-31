package com.crystal2033.qrextractor.scanner_feature.list_of_groups.presentation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.ScannedGroup

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowGroup(
    scannedGroup: ScannedGroup,
    onDeleteGroupIntention: (ScannedGroup) -> Unit,
    onScannedGroupClicked: () -> Unit
) {

    val isShowActionsWithGroup = remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .combinedClickable(
                onClick = {
                    if (isShowActionsWithGroup.value) {
                        isShowActionsWithGroup.value = false
                    } else {
                        onScannedGroupClicked()
                    }
                },
                onLongClick = {
                    Log.i(LOG_TAG_NAMES.INFO_TAG, "LOOONG CLOCKED")
                    isShowActionsWithGroup.value = true
                }
            )
            .padding(5.dp),
    ) {
        if (isShowActionsWithGroup.value) {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(24.dp)
                    .clickable {
                        onDeleteGroupIntention(scannedGroup)
                        isShowActionsWithGroup.value = false
                    },
                imageVector = ImageVector.vectorResource(R.drawable.delete_forever_35),
                contentDescription = "Delete group",
                tint = Color.Red,
            )
        }
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                modifier = Modifier
                    .size(50.dp),
                imageVector = ImageVector.vectorResource(R.drawable.folder_100),
                contentDescription = "File with groups",
                tint = Color.White
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = scannedGroup.groupName ?: "Unknown name",
                textAlign = TextAlign.Center,
                color = Color.LightGray
            )

        }


    }


}

@Composable
@Preview
fun PreviewCard() {
//    val scannedTableNameAndId: List<ScannedTableNameAndId> =
//        listOf(ScannedTableNameAndId("person", 1))
//    val scannedGroup = ScannedGroup(1, "S", scannedTableNameAndId)
//    ShowGroup(scannedGroup) {
//        Log.i(LOG_TAG_NAMES.INFO_TAG, "Group id: ${scannedGroup.id}")
//    }

}