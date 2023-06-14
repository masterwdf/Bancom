package com.example.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "company")
data class CompanyEntity(
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = "catchPhrase")
    @SerializedName("catchPhrase")
    val catchPhrase: String,

    @ColumnInfo(name = "bs")
    @SerializedName("bs")
    val bs: String
)