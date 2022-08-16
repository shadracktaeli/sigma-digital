package za.co.codevue.shared.extensions

import com.google.gson.Gson
import retrofit2.Call
import za.co.codevue.shared.exceptions.ServerException
import za.co.codevue.shared.models.network.ApiErrorResponse

/** Makes a synchronous Retrofit call without callbacks */
internal fun <ResponseType : Any> Call<ResponseType>.callApi(): ResponseType {
    try {
        val response = this.execute()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            /** Try parsing error body to [ApiErrorResponse] */
            try {
                val errorResponse: ApiErrorResponse? = Gson().fromJson(
                    response.errorBody()?.string(),
                    ApiErrorResponse::class.java
                )

                if (errorResponse != null) {
                    throw ServerException(
                        code = response.code(),
                        errorCode = errorResponse.code.valueOrDefault(),
                        errorMessage = errorResponse.message.valueOrDefault()
                    )
                } else {
                    throw ServerException(
                        code = response.code(),
                        errorMessage = response.message()
                    )
                }
            } catch (exception: Exception) {
                if (exception is ServerException) {
                    throw exception
                } else {
                    throw ServerException(
                        code = response.code(),
                        errorMessage = exception.toString()
                    )
                }
            }
        }
    } catch (exception: Exception) {
        throw exception
    }
}