package com.crystal2033.qrextractor.scanner_feature.scanner.data.repository

import android.content.Context
import android.util.Log
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.data.remote.api.ScanPersonApi
import com.crystal2033.qrextractor.core.model.Person
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.repository.PersonRepository
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.ExceptionAndErrorParsers
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.RemoteServerRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class PersonRepositoryImpl(
    private val personApi: ScanPersonApi,
    private val context: Context
) : PersonRepository {
    override fun getPerson(id: Long): Flow<Resource<Person>> = flow {
        emit(Resource.Loading())
        try {
            val person = tryToGetPerson(id, context)
            emit(Resource.Success(data = person))

        } catch (e: RemoteServerRequestException) {
            Log.e("ERROR", e.message ?: "Unknown error")
            emit(Resource.Error(message = e.message ?: "Unknown error"))
        }
    }

    private suspend fun tryToGetPerson(
        id: Long,
        context: Context
    ): Person {
        val message: String
        try {
            val response = personApi.getPerson(id)
            val person = response.body()?.toPerson()
            person?.let {
                return person
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