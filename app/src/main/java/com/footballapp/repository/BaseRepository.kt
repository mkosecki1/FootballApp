package com.footballapp.repository

import com.footballapp.net.ResponseCall
import retrofit2.Response
import java.lang.Exception

open class BaseRepository {

    suspend fun <T: Any> call(call: suspend () -> Response<T>) : ResponseCall<T> {
        try {
            val response = call.invoke()

            //HTTP OK (200)
            return if (response.isSuccessful) {
                ResponseCall.Success(response.body() as T)

                //HTTP Error
            } else {
                ResponseCall.Error(
                    response.code(),
                    response.errorBody()?.string() ?: ""
                )
            }
            //HTTP Exception
        } catch (e: Exception) {
            return ResponseCall.Exception(e)
        }
    }
}