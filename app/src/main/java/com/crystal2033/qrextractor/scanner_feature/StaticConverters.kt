package com.crystal2033.qrextractor.scanner_feature

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap


object StaticConverters {
    fun fromBytesIntoImageBitmap(stringBytes: String): ImageBitmap {
        val imageBytes = Base64.decode(stringBytes, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size).asImageBitmap()
    }
}