package com.onlineshop.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    private val repository = RegisterRepository()
    fun callData(email: String?, password: String?, name: String?, city: String?) = repository.callApiRegister(email, password, name, city)
    fun getData(): MutableLiveData<String>? = repository.dataToken
    fun clearComposite() = repository.clearComposite()
}