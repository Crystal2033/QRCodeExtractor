package com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case

import com.crystal2033.qrextractor.core.remote_server.domain.use_case.chair.GetChairUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.desk.GetDeskUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.keyboard.GetKeyboardUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.monitor.GetMonitorUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.projector.GetProjectorUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.system_unit.GetSystemUnitUseCase

data class DataGetterUseCases(
    val getChairUseCase: GetChairUseCase,
    val getDeskUseCase: GetDeskUseCase,
    val getProjectorUseCase: GetProjectorUseCase,
    val getMonitorUseCase: GetMonitorUseCase,
    val getSystemUnitUseCase: GetSystemUnitUseCase,
    val getKeyboardUseCase: GetKeyboardUseCase
)