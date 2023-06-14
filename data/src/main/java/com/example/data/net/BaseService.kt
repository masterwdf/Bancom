package com.example.data.net

import com.example.data.exception.BaseException
import com.example.data.exception.ExpiredSessionException

class BaseService {

    companion object {
        fun getError(error: Throwable): Throwable {
            return try {
                when (error) {
                    is BaseException.UnAuthorizeException -> {
                        ExpiredSessionException()
                    }

                    else -> {
                        error
                    }
                }
            } catch (ex: Exception) {
                BaseException.GeneralException(ex.message.toString())
            }
        }
    }
}