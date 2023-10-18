package com.crystal2033.qrextractor.scanner_feature.domain.model

import android.widget.ImageView
import androidx.compose.ui.graphics.ImageBitmap
import com.crystal2033.qrextractor.scanner_feature.data.remote.dto.WorkSpaceDto

class Person (
    val id: Int,
    val department: Department,
    val firstName: String,
    val image: ImageBitmap,
    val secondName: String,
    val title: Title,
    val workSpace: WorkSpace
):QRScannableData{
    override fun getDatabaseTableName(): DatabaseObjectTypes {
        return DatabaseObjectTypes.PERSON
    }

    override fun getDatabaseID(): Int {
        return id;
    }

}
