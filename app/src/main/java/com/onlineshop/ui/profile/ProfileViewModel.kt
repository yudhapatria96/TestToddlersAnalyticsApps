package com.onlineshop.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val repository = ProfileRepository()
    fun callData(auth:String?,email: String?, name: String?, id: String?) = repository.callApiUpdate(auth,email,name,id)
    fun getData(): MutableLiveData<ResponseUpdateProfile>? = repository.dataProfileUpdate
    fun clearComposite() = repository.clearComposite()
}