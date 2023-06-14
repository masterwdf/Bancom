package com.example.data.entity.net

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("success")
    var success: Boolean? = false,

    @SerializedName("message")
    var message: String?,

    @SerializedName("code")
    var code: Int? = -1,

    @SerializedName("data")
    var data: T,

    @SerializedName("error")
    var error: BaseErrorResponse?
)