package com.example.data.util

object Constant {
    const val DATABASE_NAME = "bancom_db"
    const val TIME_OUT = 180L

    object ErrorMessage {
        const val GENERAL_ERROR_MESSAGE = "Ocurrió un error inesperado"
        const val UNAUTHORIZED_MESSAGE = "Su sesión ha expirado"
        const val DATABASE_ERROR_MESSAGE = "Ocurrió un error en la base de datos local"
    }

    object HttpCode {
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
    }
}