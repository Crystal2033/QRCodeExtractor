package com.crystal2033.qrextractor.core.remote_server.domain.repository.impl

import android.content.Context
import com.crystal2033.qrextractor.core.remote_server.api.BranchAPI
import com.crystal2033.qrextractor.core.remote_server.data.model.Branch
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.BranchRepository
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BranchRepositoryImpl(
    private val branchAPI: BranchAPI,
    private val context: Context
) : BranchRepository {
    override fun getBranchesByOrg(orgId: Long): Flow<Resource<List<Branch>>> = flow {

    }

}