package com.example.data.exception

import com.example.data.util.Constant
import java.io.IOException

sealed class BaseException(
    errorMessage: String
) : IOException(errorMessage) {

    data class GeneralException(
        var errorMessage: String = Constant.ErrorMessage.GENERAL_ERROR_MESSAGE,
    ) : BaseException(errorMessage)

    data class AirplaneException(
        var errorMessage: String
    ) : BaseException(errorMessage)

    data class NetworkException(
        var errorMessage: String
    ) : BaseException(errorMessage)

    data class BadRequestException(
        var errorMessage: String
    ) : BaseException(errorMessage)

    data class UnAuthorizeException(
        var errorMessage: String = Constant.ErrorMessage.UNAUTHORIZED_MESSAGE
    ) : BaseException(errorMessage)

    data class DatabaseException(
        var errorMessage: String = Constant.ErrorMessage.DATABASE_ERROR_MESSAGE,
    ) : BaseException(errorMessage)

    data class CustomException(
        var errorMessage: String
    ) : BaseException(errorMessage)
}