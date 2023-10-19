package com.crystal2033.qrextractor.scanner_feature.data.repository

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.Constants
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.data.remote.api.KeyboardApi
import com.crystal2033.qrextractor.scanner_feature.domain.model.Keyboard
import com.crystal2033.qrextractor.scanner_feature.domain.repository.KeyboardRepository
import com.crystal2033.qrextractor.scanner_feature.exceptions.RemoteServerRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException


class KeyboardRepositoryImpl(
    private val keyboardApi: KeyboardApi,
    private val context: Context
) : KeyboardRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getKeyboard(id: Int): Flow<Resource<Keyboard>> = flow {
        emit(Resource.Loading())
        try {
            val keyboard = tryToGetKeyboard(id, context)
            emit(Resource.Success(data = keyboard))
        } catch (e: RemoteServerRequestException) {
            Log.e("ERROR", e.message ?: "Unknown error")
            emit(Resource.Error(message = e.message ?: "Unknown error"))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun tryToGetKeyboard(
        id: Int,
        context: Context
    ): Keyboard {
        var message = "Unknown message"
        try {
            val keyboard = keyboardApi.getKeyboard(id)?.toKeyboard()
            keyboard?.let {
                return keyboard
            } ?: throw RemoteServerRequestException(message)
        } catch (e: HttpException) {
            message = getErrorMessageFromException(e)
            throw RemoteServerRequestException(message)
        } catch (e: IOException) {
            message = context.getString(R.string.server_connection_error)
            throw RemoteServerRequestException(message)
        }

    }

    private fun getErrorMessageFromException(
        e: HttpException
    ): String {
        val stringErrorFromRemoteApi = e.response()?.errorBody()?.string()
        val jObjError = stringErrorFromRemoteApi?.let { JSONObject(it) }
        return "HTTP ${jObjError?.getString(Constants.ERROR_STATUS_CODE_FIELD) ?: "Unknown code"}," +
                " ${jObjError?.getString(Constants.ERROR_MESSAGE_FIELD) ?: "Unknown error from server"}"
    }
}