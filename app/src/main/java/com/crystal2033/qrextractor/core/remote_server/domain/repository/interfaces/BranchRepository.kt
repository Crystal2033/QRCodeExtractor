package com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces

import com.crystal2033.qrextractor.core.remote_server.data.model.Branch
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface BranchRepository {
    fun getBranchesByOrg(orgId: Long): Flow<Resource<List<Branch>>>

    fun getBranchById(branchId: Long): Flow<Resource<Branch>>
}