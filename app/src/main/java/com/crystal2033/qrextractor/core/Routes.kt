package com.crystal2033.qrextractor.core

import android.content.Context
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.scanner_feature.domain.repository.KeyboardRepository
import com.crystal2033.qrextractor.scanner_feature.domain.use_case.concrete_use_case.GetKeyboardFromQRCodeUseCase
import dagger.Provides
import javax.inject.Singleton

//data class Routes(val context: Context) {
//    val LIST_SCANNED_OBJECTS_ROUTE = context.resources.getString(R.string.list_of_scanned_objects_route)
//}