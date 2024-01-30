package com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case

import com.crystal2033.qrextractor.core.remote_server.domain.use_case.chair.DeleteChairUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.desk.DeleteDeskUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.keyboard.DeleteKeyboardUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.monitor.DeleteMonitorUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.projector.DeleteProjectorUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.system_unit.DeleteSystemUnitUseCase

data class DeleteObjectOnServerUseCases(
    val deleteChairUseCase: DeleteChairUseCase,
    val deleteDeskUseCase: DeleteDeskUseCase,
    val deleteKeyboardUseCase: DeleteKeyboardUseCase,
    val deleteProjectorUseCase: DeleteProjectorUseCase,
    val deleteSystemUnitUseCase: DeleteSystemUnitUseCase,
    val deleteMonitorUseCase: DeleteMonitorUseCase
)
