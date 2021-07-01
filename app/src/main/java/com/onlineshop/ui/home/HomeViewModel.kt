package com.onlineshop.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val repository= HomeRepository()
    fun callData(auth: String?) = repository.callApiProduct(auth)
    fun getDataProduct(): MutableLiveData<ProductResponse>? = repository.dataProduct

    fun callCategory(auth: String?) = repository.callApiCategory(auth)
    fun getDataCategory(): MutableLiveData<CategoryResponse>? = repository.dataCategory


    fun clearComposite() = repository.clearComposite()
}