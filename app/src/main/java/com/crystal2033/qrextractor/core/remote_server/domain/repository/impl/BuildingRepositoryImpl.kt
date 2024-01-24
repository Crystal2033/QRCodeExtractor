package com.crystal2033.qrextractor.core.remote_server.domain.repository.impl

import android.content.Context
import android.util.Log
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.remote_server.api.BuildingAPI
import com.crystal2033.qrextractor.core.remote_server.data.APIArgumentsFillers
import com.crystal2033.qrextractor.core.remote_server.data.dto.BuildingDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.Building
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.BuildingRepository
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.ExceptionAndErrorParsers
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.RemoteServerRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.stream.Collectors.toList

class BuildingRepositoryImpl(
    private val buildingAPI: BuildingAPI,
    private val context: Context
) : BuildingRepository {
    override fun getBuildingsByOrgAndBranch(
        branchId: Long
    ): Flow<Resource<List<Building>>> = flow {
        emit(Resource.Loading())
        try {
            val buildings = tryToGetBuildings(branchId)
            emit(Resource.Success(buildings))
        } catch (e: RemoteServerRequestException) {
            Log.e("ERROR", e.message ?: "Unknown error")
            emit(Resource.Error(message = e.message ?: "Unknown error"))
        }
    }

    override fun getBuildingById(buildingId: Long): Flow<Resource<Building>> = flow {
        emit(Resource.Loading())
        try {
            val building = tryToGetBuilding(buildingId)
            emit(Resource.Success(building))
        } catch (e: RemoteServerRequestException) {
            Log.e("ERROR", e.message ?: "Unknown error")
            emit(Resource.Error(message = e.message ?: "Unknown error"))
        }
    }

    private suspend fun tryToGetBuildings(branchId: Long): List<Building> {
        val message: String
        try {
            val response = buildingAPI.getAllBuildingsByOrgAndBranch(
                APIArgumentsFillers.NOT_NEEDED.value,
                branchId
            )
            val buildings =
                response.body()?.stream()?.map(BuildingDTO::toBuilding)?.collect(toList())
            buildings?.let {
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

    private suspend fun tryToGetBuilding(buildingId: Long): Building {
        val message: String
        try {
            val response = buildingAPI.getBuildingById(
                APIArgumentsFillers.NOT_NEEDED.value,
                APIArgumentsFillers.NOT_NEEDED.value,
                buildingId
            )
            val buildings =
                response.body()?.toBuilding()
            buildings?.let {
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