package com.crystal2033.qrextractor.core.remote_server.domain.repository.impl

import android.content.Context
import com.crystal2033.qrextractor.core.remote_server.api.BuildingAPI
import com.crystal2033.qrextractor.core.remote_server.data.model.Building
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.BuildingRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BuildingRepositoryImpl(
    private val buildingAPI: BuildingAPI,
    private val context: Context
) : BuildingRepository {
    override fun getBuildingsByOrgAndBranch(
        orgId: Long,
        branchId: Long
    ): Flow<Resource<List<Building>>> = flow {

    }
}