package com.crystal2033.qrextractor.core.remote_server.domain.repository.impl

import android.content.Context
import com.crystal2033.qrextractor.core.remote_server.api.CabinetAPI
import com.crystal2033.qrextractor.core.remote_server.data.model.Cabinet
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.CabinetRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CabinetRepositoryImpl(
    private val cabinetAPI: CabinetAPI,
    private val context: Context
) : CabinetRepository {
    override fun getCabinetsByOrgBranchAndBuilding(
        orgId: Long,
        branchId: Long,
        buildingId: Long
    ): Flow<Resource<List<Cabinet>>> = flow {

    }

}