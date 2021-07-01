package com.onlineshop.ui.profile

import com.google.gson.annotations.SerializedName

data class ResponseUpdateProfile (
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : List<DataProfileUpdate>


)
data class DataProfileUpdate (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("email") val email : String,
    @SerializedName("email_verified_at") val email_verified_at : String,
    @SerializedName("password") val password : String,
    @SerializedName("remember_token") val remember_token : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String
)