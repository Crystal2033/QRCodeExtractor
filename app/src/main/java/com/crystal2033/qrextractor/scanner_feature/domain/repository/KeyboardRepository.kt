package com.crystal2033.qrextractor.scanner_feature.domain.repository

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.domain.model.Keyboard
import kotlinx.coroutines.flow.Flow

interface KeyboardRepository {
    fun getKeyboard(id: Int): Flow<Resource<Keyboard>>
}