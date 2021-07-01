package com.onlineshop.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel(){

    private val repository = LoginRepository()
    fun callData(email: String?, password: String?) = repository.callApiLogin(email, password)
    fun callDataProfile(auth: String?) = repository.callApiProfile(auth)
    fun getData(): MutableLiveData<String>? = repository.dataToken
    fun getDataProfile(): MutableLiveData<ProfileResponse>? = repository.dataProfile
    fun clearComposite() = repository.clearComposite()

}