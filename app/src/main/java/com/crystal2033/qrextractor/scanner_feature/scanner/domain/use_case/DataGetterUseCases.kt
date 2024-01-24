package com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case

import com.crystal2033.qrextractor.core.remote_server.domain.use_case.chair.GetChairUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.concrete_use_case.GetPersonUseCase

data class DataGetterUseCases(
    val getPersonUseCase: GetPersonUseCase,
    val getChairUseCase: GetChairUseCase
    //val getKeyboardUseCase: GetKeyboardUseCase
)