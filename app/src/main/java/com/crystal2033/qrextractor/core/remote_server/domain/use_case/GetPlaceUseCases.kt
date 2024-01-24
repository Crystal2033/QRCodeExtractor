package com.crystal2033.qrextractor.core.remote_server.domain.use_case

import com.crystal2033.qrextractor.core.remote_server.domain.use_case.branch.GetBranchUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.building.GetBuildingUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.cabinet.GetCabinetUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.organization.GetOrganizationUseCase

data class GetPlaceUseCases(
    val getOrganizationUseCase: GetOrganizationUseCase,
    val getBranchUseCase: GetBranchUseCase,
    val getBuildingUseCase: GetBuildingUseCase,
    val getCabinetUseCase: GetCabinetUseCase
)
