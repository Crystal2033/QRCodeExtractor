package com.crystal2033.qrextractor.core.remote_server.domain.repository.impl

import android.content.Context
import android.util.Log
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.remote_server.api.CabinetAPI
import com.crystal2033.qrextractor.core.remote_server.data.APIArgumentsFillers
import com.crystal2033.qrextractor.core.remote_server.data.dto.CabinetDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.Cabinet
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.CabinetRepository
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.ExceptionAndErrorParsers
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.RemoteServerRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.stream.Collectors.toList

class CabinetRepositoryImpl(
    private val cabinetAPI: CabinetAPI,
    private val context: Context
) : CabinetRepository {
    override fun getCabinetsByOrgBranchAndBuilding(
        buildingId: Long
    ): Flow<Resource<List<Cabinet>>> = flow {
        emit(Resource.Loading())
        try {
            val cabinets = tryToGetCabinets(buildingId)
            emit(Resource.Success(cabinets))
        } catch (e: RemoteServerRequestException) {
            Log.e("ERROR", e.message ?: "Unknown error")
            emit(Resource.Error(message = e.message ?: "Unknown error"))
        }
    }

    override fun getCabinetById(cabinetId: Long): Flow<Resource<Cabinet>> = flow {
        emit(Resource.Loading())
        try {
            val cabinet = tryToGetCabinet(cabinetId)
            emit(Resource.Success(cabinet))
        } catch (e: RemoteServerRequestException) {
            Log.e("ERROR", e.message ?: "Unknown error")
            emit(Resource.Error(message = e.message ?: "Unknown error"))
        }
    }


    private suspend fun tryToGetCabinets(buildingId: Long): List<Cabinet> {
        val message: String
        try {
            val response = cabinetAPI.getAllCabinetsByOrgBranchAndBuilding(
                APIArgumentsFillers.NOT_NEEDED.value,
                APIArgumentsFillers.NOT_NEEDED.value,
                buildingId
            )
            val cabinets =
                response.body()?.stream()?.map(CabinetDTO::toCabinet)?.collect(toList())
            cabinets?.let {
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

    private suspend fun tryToGetCabinet(cabinetId: Long): Cabinet {
        val message: String
        try {
            val response = cabinetAPI.getCabinetById(
                APIArgumentsFillers.NOT_NEEDED.value,
                APIArgumentsFillers.NOT_NEEDED.value,
                APIArgumentsFillers.NOT_NEEDED.value,
                cabinetId
            )
            val cabinet =
                response.body()?.toCabinet()
            cabinet?.let {
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