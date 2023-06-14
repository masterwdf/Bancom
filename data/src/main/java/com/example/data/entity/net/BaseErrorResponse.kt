package com.example.data.entity.net

import com.google.gson.annotations.SerializedName

data class BaseErrorResponse(
    @SerializedName("errorCode")
    var errorCode: Int = -1,

    @SerializedName("errorMessage")
    var errorMessage: String = String()
)
