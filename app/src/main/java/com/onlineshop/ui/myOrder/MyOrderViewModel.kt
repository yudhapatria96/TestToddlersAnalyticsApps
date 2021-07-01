package com.onlineshop.ui.myOrder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyOrderViewModel : ViewModel() {

    private val repository= MyOrderRepository()
    fun callData(auth: String?, idPelanggan: Int?) = repository.callApiOrder(auth, idPelanggan)
    fun getDataProduct(): MutableLiveData<OrderResponse>? = repository.dataOrder
    fun clearComposite() = repository.clearComposite()
}