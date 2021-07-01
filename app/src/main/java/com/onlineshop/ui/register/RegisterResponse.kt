package com.onlineshop.ui.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse (
    @SerializedName("access_token") val access_token : String,
    @SerializedName("token_type") val token_type : String,
    @SerializedName("expires_in") val expires_in : Int
)