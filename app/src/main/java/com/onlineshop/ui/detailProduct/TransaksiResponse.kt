package com.onlineshop.ui.detailProduct

import com.google.gson.annotations.SerializedName

data class TransaksiResponse (
    @SerializedName("message") val message : String,
    @SerializedName("status") val status : Boolean
)