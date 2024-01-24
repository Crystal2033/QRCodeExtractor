package com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces

import android.content.Context
import android.util.Log
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.remote_server.data.dto.InventarizedDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedModel
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.BundleID
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.ExceptionAndErrorParsers
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.RemoteServerRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

//[M]odel, [D]TO, [A]PI, re[T]urning result, [AT] -- is returning type from api function
interface CRUDDeviceOperationsRepository<D : InventarizedDTO, A> {
    fun getAllDevicesInCabinet(cabinetId: Long): Flow<Resource<List<InventarizedAndQRScannableModel>>>

    fun getDeviceById(deviceId: Long): Flow<Resource<InventarizedAndQRScannableModel>>

    fun addDevice(deviceDTO: D?): Flow<Resource<InventarizedAndQRScannableModel>>

    fun updateDevice(deviceDTO: D): Flow<Resource<InventarizedAndQRScannableModel>>

    fun deleteDevice(deviceId: Long): Flow<Resource<Unit>>

    fun <T, AT> apiCall(
        api: A,
        context: Context,
        bundleID: BundleID,
        deviceDTO: D?,
        apiCallFunction: suspend(
            bundle: BundleID,
            deviceDTO: D?
        ) -> Response<AT>,
        getRequestBodyAndConvertInModel: (Response<AT>) -> T
    ): Flow<Resource<T>> = flow {
        emit(Resource.Loading())
        try {
            val result = tryToMakeAPICall(
                api,
                context,
                bundleID,
                deviceDTO,
                apiCallFunction,
                getRequestBodyAndConvertInModel
            )
            emit(Resource.Success(result))
        } catch (e: RemoteServerRequestException) {
            Log.e("ERROR", e.message ?: "Unknown error")
            emit(Resource.Error(message = e.message ?: "Unknown error"))
        }
    }


    //not needed parameters could be omitted
    suspend fun <T, AT> tryToMakeAPICall(
        api: A,
        context: Context,
        bundleID: BundleID,
        deviceDTO: D?,
        apiCallFunction: suspend (
            bundleID: BundleID,
            deviceDTO: D?
        ) -> Response<AT>,
        getRequestBodyAndConvertInModel: (Response<AT>) -> T
    ): T {
        val message: String
        try {
            val response = apiCallFunction(bundleID, deviceDTO)
            return getRequestBodyAndConvertInModel(response)
        } catch (e: HttpException) {
            message = ExceptionAndErrorParsers.getErrorMessageFromException(e)
            throw RemoteServerRequestException(message)
        } catch (e: IOException) {
            message = context.getString(R.string.server_connection_error)
            throw RemoteServerRequestException(message)
        }
    }

}