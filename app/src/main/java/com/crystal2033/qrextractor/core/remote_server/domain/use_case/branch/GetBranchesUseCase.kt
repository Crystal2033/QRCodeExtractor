package com.crystal2033.qrextractor.core.remote_server.domain.use_case.branch

import com.crystal2033.qrextractor.core.remote_server.data.model.Branch
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.BranchRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetBranchesUseCase(
    private val branchRepository: BranchRepository
) {
    operator fun invoke(orgId: Long): Flow<Resource<List<Branch>>> {
        return branchRepository.getBranchesByOrg(orgId)
    }
}