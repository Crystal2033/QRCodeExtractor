package com.crystal2033.qrextractor.core.remote_server.domain.repository.impl

import android.content.Context
import android.util.Log
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.remote_server.api.BranchAPI
import com.crystal2033.qrextractor.core.remote_server.data.APIArgumentsFillers
import com.crystal2033.qrextractor.core.remote_server.data.dto.BranchDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.Branch
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.BranchRepository
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.ExceptionAndErrorParsers
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.RemoteServerRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.stream.Collectors.toList

class BranchRepositoryImpl(
    private val branchAPI: BranchAPI,
    private val context: Context
) : BranchRepository {
    override fun getBranchesByOrg(orgId: Long): Flow<Resource<List<Branch>>> = flow {
        emit(Resource.Loading())
        try {
            val branches = tryToGetBranches(orgId)
            emit(Resource.Success(branches))
        } catch (e: RemoteServerRequestException) {
            Log.e("ERROR", e.message ?: "Unknown error")
            emit(Resource.Error(message = e.message ?: "Unknown error"))
        }
    }

    override fun getBranchById(branchId: Long): Flow<Resource<Branch>> = flow {
        emit(Resource.Loading())
        try {
            val branch = tryToGetBranch(branchId)
            emit(Resource.Success(branch))
        } catch (e: RemoteServerRequestException) {
            Log.e("ERROR", e.message ?: "Unknown error")
            emit(Resource.Error(message = e.message ?: "Unknown error"))
        }
    }

    private suspend fun tryToGetBranches(orgId: Long): List<Branch> {
        val message: String
        try {
            val response = branchAPI.getAllBranchesByOrg(orgId)
            val branches =
                response.body()?.stream()?.map(BranchDTO::toBranch)?.collect(toList())
            branches?.let {
                return it
            } ?: throw RemoteServerRequestException(
                ExceptionAndErrorParsers.getErrorMessageFromResponse(response)
            )
        } catch (e: HttpException) {
            message = ExceptionAndErrorParsers.getErrorMessageFromException(e)
            throw RemoteServerRequestException(message)
        } catch (e: IOException) {
            message = context.getString(R.string.server_connection_error)
            throw RemoteServerRequestException(message)
        }
    }

    private suspend fun tryToGetBranch(branchId: Long): Branch {
        val message: String
        try {
            val response = branchAPI.getBranchById(
                APIArgumentsFillers.NOT_NEEDED.value, branchId
            )
            val branch =
                response.body()?.toBranch()
            branch?.let {
                return it
            } ?: throw RemoteServerRequestException(
                ExceptionAndErrorParsers.getErrorMessageFromResponse(response)
            )
        } catch (e: HttpException) {
            message = ExceptionAndErrorParsers.getErrorMessageFromException(e)
            throw RemoteServerRequestException(message)
        } catch (e: IOException) {
            message = context.getString(R.string.server_connection_error)
            throw RemoteServerRequestException(message)
        }
    }

}