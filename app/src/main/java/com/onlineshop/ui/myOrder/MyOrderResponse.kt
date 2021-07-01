package com.onlineshop.ui.myOrder

import com.google.gson.annotations.SerializedName

data class OrderResponse (
    @SerializedName("message") val message : String,
    @SerializedName("status") val status : Boolean,
    @SerializedName("data") val data : List<DataOrder>
)

data class DataOrder (

    @SerializedName("id") val id : Int,
    @SerializedName("order_code") val order_code : String,
    @SerializedName("product_id") val product_id : Int,
    @SerializedName("product_name") val product_name : String,
    @SerializedName("price") val price : Int,
    @SerializedName("pelanggan_id") val pelanggan_id : Int,
    @SerializedName("qty") val qty : Int,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String
)