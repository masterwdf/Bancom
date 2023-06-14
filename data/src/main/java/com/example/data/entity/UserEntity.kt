package com.example.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Long,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = "username")
    @SerializedName("username")
    val username: String,

    @ColumnInfo(name = "email")
    @SerializedName("email")
    val email: String,

    //@ColumnInfo(name = "address")
    //@SerializedName("address")
    //val address: AddressEntity,

    @ColumnInfo(name = "phone")
    @SerializedName("phone")
    val phone: String,

    @ColumnInfo(name = "website")
    @SerializedName("website")
    val website: String,

    //@ColumnInfo(name = "company")
    //@SerializedName("company")
    //val company: CompanyEntity
)