package com.crystal2033.qrextractor.scanner_feature.scanner.exceptions

import com.crystal2033.qrextractor.core.ResponseErrorConstants
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

class ExceptionAndErrorParsers {
    companion object Parser{
        fun getErrorMessageFromException(
            e: HttpException
        ): String {
            val stringErrorFromRemoteApi = e.response()?.errorBody()?.string()
            val jObjError = stringErrorFromRemoteApi?.let { JSONObject(it) }
            return "HTTP ${jObjError?.getString(ResponseErrorConstants.ERROR_STATUS_CODE_FIELD) ?: "Unknown code"}," +
                    " ${jObjError?.getString(ResponseErrorConstants.ERROR_MESSAGE_FIELD) ?: "Unknown error from server"}"
        }

        fun <T> getErrorMessageFromResponse(
            response: Response<T>
        ): String {
            val stringErrorFromRemoteApi = response.errorBody()?.string()
            val jObjError = stringErrorFromRemoteApi?.let { JSONObject(it) }
            return "HTTP ${jObjError?.getString(ResponseErrorConstants.ERROR_STATUS_CODE_FIELD) ?: "Unknown code"}," +
                    " ${jObjError?.getString(ResponseErrorConstants.ERROR_MESSAGE_FIELD) ?: "Unknown error from server"}"
        }
    }
}