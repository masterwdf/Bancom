package com.example.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "address")
data class AddressEntity(
    @ColumnInfo(name = "street")
    @SerializedName("street")
    val street: String,

    @ColumnInfo(name = "suite")
    @SerializedName("suite")
    val suite: String,

    @ColumnInfo(name = "city")
    @SerializedName("city")
    val city: String,

    @ColumnInfo(name = "zipcode")
    @SerializedName("zipcode")
    val zipcode: String,

    //@ColumnInfo(name = "geo")
    //@SerializedName("geo")
    //val geo: GeoEntity
)