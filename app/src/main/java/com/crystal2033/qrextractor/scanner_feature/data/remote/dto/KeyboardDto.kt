package com.crystal2033.qrextractor.scanner_feature.data.remote.dto

import android.os.Build
import androidx.annotation.RequiresApi
import com.crystal2033.qrextractor.scanner_feature.StaticConverters
import com.crystal2033.qrextractor.scanner_feature.domain.model.Keyboard
import java.time.LocalDate

data class KeyboardDto(
    val id: Int,
    val image: String,
    val jsondataForQR: String,
    val model: String,
    val startUseDate: String,
    val workSpace: WorkSpaceDto
){
    @RequiresApi(Build.VERSION_CODES.O)
    fun toKeyboard(): Keyboard{
        return Keyboard(
            id = id,
            image = StaticConverters.fromBytesIntoImageBitmap(image),
            model = model,
            startUseDate = LocalDate.parse(startUseDate), //TODO: convert into date
            workSpace = workSpace.toWorkSpace()
        )
    }
}