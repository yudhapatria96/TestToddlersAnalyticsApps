package com.onlineshop.ui.categoryProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onlineshop.ui.home.ProductResponse

class CategoryProductViewModel : ViewModel() {

    private val repository= CategoryProductRepository()
    fun callData(auth: String?, category: String?) = repository.callApiProduct(auth, category)
    fun getDataProduct(): MutableLiveData<ProductResponse>? = repository.dataProduct

    fun callDataAll(auth: String?) = repository.callApiProductall(auth)

    fun callDatabyJK(auth: String?, jk: String?) = repository.callApiProductByJK(auth, jk)


    fun clearComposite() = repository.clearComposite()
}