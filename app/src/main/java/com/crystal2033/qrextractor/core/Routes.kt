package com.crystal2033.qrextractor.core

import android.content.Context
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.scanner_feature.domain.repository.KeyboardRepository
import com.crystal2033.qrextractor.scanner_feature.domain.use_case.concrete_use_case.GetKeyboardFromQRCodeUseCase
import dagger.Provides
import javax.inject.Singleton
sealed class LOG_TAG_NAMES{
    companion object {
        const val ERROR_TAG = "ERROR"
        const val INFO_TAG = "INFO"
        const val DEBUG_TAG = "DEBUG"
        const val WARNING_TAG = "WARNING"
    }
}