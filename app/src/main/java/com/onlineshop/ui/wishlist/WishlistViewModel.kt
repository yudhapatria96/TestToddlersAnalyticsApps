package com.onlineshop.ui.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WishlistViewModel : ViewModel() {

    private val repository= WishlistRepository()
    fun callData(auth: String?, idPelanggan: Int?) = repository.callApiProduct(auth, idPelanggan)
    fun getDataProduct(): MutableLiveData<WishlistResponse>? = repository.dataProduct
    fun clearComposite() = repository.clearComposite()
}