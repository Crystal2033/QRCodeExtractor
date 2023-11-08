package com.crystal2033.qrextractor.scanner_feature.scanner.data.repository

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.data.remote.api.KeyboardApi
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.Keyboard
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.repository.KeyboardRepository
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.ExceptionAndErrorParsers
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.RemoteServerRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


class KeyboardRepositoryImpl(
    private val keyboardApi: KeyboardApi,
    private val context: Context
) : KeyboardRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getKeyboard(id: Long): Flow<Resource<Keyboard>> = flow {
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
        id: Long,
        context: Context
    ): Keyboard {
        val message: String
        try {
            val response = keyboardApi.getKeyboard(id)
            val keyboard = response.body()?.toKeyboard()
            keyboard?.let {
                return keyboard
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