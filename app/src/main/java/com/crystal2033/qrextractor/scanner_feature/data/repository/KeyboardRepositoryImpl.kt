package com.crystal2033.qrextractor.scanner_feature.data.repository

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.data.remote.api.KeyboardApi
import com.crystal2033.qrextractor.scanner_feature.domain.model.Keyboard
import com.crystal2033.qrextractor.scanner_feature.domain.repository.KeyboardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
            val keyboard = keyboardApi.getKeyboard(id)?.toKeyboard()
            keyboard?.let {
                emit(Resource.Success(data = it))
            } ?: emit(Resource.Error(message = "Keyboard with id=$id not found."))
        } catch (e: HttpException) {
            e.message?.let { Log.e("ERROR", it + "The is a problem with http request/response.") }
            emit(Resource.Error(message = context.getString(R.string.response_request_error)))
        } catch (e: IOException) {
            e.message?.let { Log.e("ERROR", it + "The is a problem with input output streams.") }
            emit(Resource.Error(message = context.getString(R.string.server_connection_error)))
        } catch (e: Exception) {
            e.message?.let { Log.e("unknown ERROR", it) }
            emit(Resource.Error(message = e.message ?: "Unknown error"))
        }
    }
}