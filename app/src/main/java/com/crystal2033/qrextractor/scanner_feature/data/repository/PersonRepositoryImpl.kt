package com.crystal2033.qrextractor.scanner_feature.data.repository

import android.util.Log
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.data.remote.api.PersonApi
import com.crystal2033.qrextractor.scanner_feature.domain.model.Person
import com.crystal2033.qrextractor.scanner_feature.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class PersonRepositoryImpl(
    private val personApi: PersonApi
) : PersonRepository {
    override fun getPerson(id: Int): Flow<Resource<Person>> = flow {
        emit(Resource.Loading())
        try {
            val person: Person? = personApi.getPerson(id)?.toPerson()
            Log.i("Person", person.toString())
            person?.let {
                emit(Resource.Success(data = person))
            } ?: emit(Resource.Error(message = "Person with id=$id not found."))

        } catch (e: HttpException) {
            e.message?.let { Log.e("ERROR", it + "The is a problem with http request/response.") }
            emit(Resource.Error(message = "The is a problem with http request/response."))
        } catch (e: IOException) {
            e.message?.let { Log.e("ERROR", it + "The is a problem with input output streams.") }
            emit(Resource.Error(message = "The is a problem with input output streams."))
        }
        catch (e: Exception){
            e.message?.let { Log.e("unknown ERROR", it) }
        }


    }


}