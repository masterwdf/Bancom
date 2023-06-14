package com.example.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "geo")
data class GeoEntity(
    @ColumnInfo(name = "lat")
    @SerializedName("lat")
    val lat: Double,

    @ColumnInfo(name = "lng")
    @SerializedName("lng")
    val lng: Double
)