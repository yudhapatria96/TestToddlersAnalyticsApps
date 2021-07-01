package com.onlineshop.ui.home

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("data") val data : List<DataCategory>,
    @SerializedName("message") val message : String,
    @SerializedName("status") val status : Boolean
)

data class DataCategory (

    @SerializedName("id") val id : Int,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("name") val name : String,
    @SerializedName("logo") val logo : String
)