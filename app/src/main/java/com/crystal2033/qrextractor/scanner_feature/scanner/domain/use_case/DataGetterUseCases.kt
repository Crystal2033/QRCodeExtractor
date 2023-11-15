package com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case

import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.concrete_use_case.GetKeyboardUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.concrete_use_case.GetPersonUseCase

data class DataGetterUseCases(
    val getPersonUseCase: GetPersonUseCase,
    val getKeyboardUseCase: GetKeyboardUseCase
)