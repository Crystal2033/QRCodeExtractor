package com.crystal2033.qrextractor.core.remote_server.data.dto

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

interface ToModelMapper<M> {
    fun toModel(): M
}