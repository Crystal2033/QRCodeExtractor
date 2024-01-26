package com.crystal2033.qrextractor.scanner_feature.modify_concrete_object.domain.model

import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetPlaceUseCases
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.chair.UpdateChairUseCase

data class UpdateParametersBundle(
    val chairForUpdate: InventarizedAndQRScannableModel?,
    val updateChairUseCase: UpdateChairUseCase,
    val getPlaceUseCases: GetPlaceUseCases
)
