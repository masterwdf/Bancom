package com.example.bancom.common

class StringUtil {

    companion object {
        fun isNullOrEmpty(value: String?): Boolean {
            return value == null || "" == value.trim { it <= ' ' }
        }
    }
}