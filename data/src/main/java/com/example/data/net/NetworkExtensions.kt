package com.example.data.net

import android.util.Log
import com.example.data.entity.net.BaseResponse
import com.example.data.exception.BaseException
import com.example.domain.entity.EventResult
import com.google.gson.Gson
import retrofit2.Response

fun <T, R> Response<T>?.validateResponse(
    transform: T.() -> R,
): EventResult<R> {
    try {
        this?.let { response ->
            val errorBody: String? = response.errorBody()?.string()
            if (response.isSuccessful && errorBody.isNullOrEmpty()) {
                val responseBody: T? = response.body()
                return responseBody?.let { data ->
                    EventResult.Success(transform.invoke(data))
                }?: kotlin.run {
                    Log.i(
                        "BaseResponse ",
                        "responseBody:: Error con codigo diferente de 200 "
                    )
                    EventResult.Error(BaseException.GeneralException())
                }
            } else {
                Log.i("BaseResponse ", "responseBody:: ERROR")
                val responseBody = Gson().fromJson(errorBody, BaseResponse::class.java)
                Log.i(
                    "BaseResponse ",
                    "responseBody:: responseBody" + responseBody.error?.errorMessage
                )
                if (code() == 401) {
                    return EventResult.Error(BaseException.UnAuthorizeException(code().toString()))
                }

                Log.i("BaseResponse ", "responseBody:: responseBody   code()" + code())
                return EventResult.Error(BaseException.GeneralException(responseBody.error?.errorMessage!!))
            }
        } ?: kotlin.run {
            Log.i("BaseResponse ", "responseBody:: ERROR 2")
            return EventResult.Error(BaseException.GeneralException())
        }
    } catch (ex: Exception) {
        Log.i("BaseResponse ", "responseBody:: ERROR 3")
        return EventResult.Error(BaseException.GeneralException())
    }
}