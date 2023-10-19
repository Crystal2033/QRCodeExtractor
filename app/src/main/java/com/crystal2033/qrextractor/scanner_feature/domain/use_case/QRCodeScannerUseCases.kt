package com.crystal2033.qrextractor.scanner_feature.domain.use_case

import com.crystal2033.qrextractor.scanner_feature.domain.use_case.concrete_use_case.GetKeyboardFromQRCodeUseCase
import com.crystal2033.qrextractor.scanner_feature.domain.use_case.concrete_use_case.GetPersonFromQRCodeUseCase

data class QRCodeScannerUseCases(
    val getPersonFromQRCodeUseCase: GetPersonFromQRCodeUseCase,
    val getKeyboardFromQRCodeUseCase: GetKeyboardFromQRCodeUseCase
)