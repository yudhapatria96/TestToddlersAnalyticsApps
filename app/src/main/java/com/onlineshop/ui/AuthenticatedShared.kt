package com.onlineshop.ui

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.onlineshop.ui.login.ProfileResponse
import com.onlineshop.ui.profile.DataProfileUpdate

class AuthenticatedShared(val context: Context?) {

    private val sharedPreferences: SharedPreferences? = context?.getSharedPreferences("shared_auth", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
    private val TAG_TOKEN: String = "token"
    private val TAG_ID_USER: String = "id_user"
    private val TAG_NAMA_USER: String = "nama_user"
    private val TAG_EMAIL_USER: String = "email_user"
    private val TAG_PASSWORD: String= "password_user"
    private val TAG_CITY_USER: String = "city_user"

    fun createSession(token: String){
        Log.e("datanya", token)
        editor?.putString(TAG_TOKEN, token)
        editor?.commit()
    }

    fun savePassword(password: String){
        password?.let {
            editor?.putString(TAG_PASSWORD, password)
        }
    }

    fun createProfile(profile: ProfileResponse){


        profile.id?.let {
            editor?.putInt(TAG_ID_USER, it)
        }

        profile.name?.let {
            editor?.putString(TAG_NAMA_USER, it)
        }

        profile.email?.let {
            editor?.putString(TAG_EMAIL_USER, it)
        }

        profile.city?.let {
            editor?.putString(TAG_CITY_USER, it)
        }

        editor?.commit()

    }


    fun updateProfile(profile: DataProfileUpdate){
        profile.id?.let {
            editor?.putInt(TAG_ID_USER, it)
        }

        profile.name?.let {
            editor?.putString(TAG_NAMA_USER, it)
        }

        profile.email?.let {
            editor?.putString(TAG_EMAIL_USER, it)
        }


        editor?.commit()

    }


    fun closeSession(){
        editor?.clear()
        editor?.commit()
    }


    fun getToken(): String? {
        return sharedPreferences?.getString(TAG_TOKEN, "")
    }

    fun getIdUser(): Int?{
        return sharedPreferences?.getInt(TAG_ID_USER, 0)
    }

    fun getNamaUser(): String?{
        return sharedPreferences?.getString(TAG_NAMA_USER, "")
    }
    fun getEmailUser(): String?{
        return sharedPreferences?.getString(TAG_EMAIL_USER, "")
    }

    fun getPassword(): String?{
        return sharedPreferences?.getString(TAG_PASSWORD, "")

    }

    fun getCity(): String?{
        return sharedPreferences?.getString(TAG_CITY_USER, "")
    }

}