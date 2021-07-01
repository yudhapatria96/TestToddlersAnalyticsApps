package com.onlineshop.ui.home

import com.google.gson.annotations.SerializedName

data class ProductResponse (
    @SerializedName("data") val data : List<DataProduct>,
    @SerializedName("message") val message : String,
    @SerializedName("status") val status : Boolean


)

data class DataProduct (

    @SerializedName("id") val id : Int,
    @SerializedName("product_name") val product_name : String,
    @SerializedName("details") val details : String,
    @SerializedName("logo") val logo : String,
    @SerializedName("price") val price : Int,
    @SerializedName("stock") val stock : Int,
    @SerializedName("category") val category : String,
    @SerializedName("size") val size : String,
    @SerializedName("jenis_kelamin") val jenis_kelamin : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String
)