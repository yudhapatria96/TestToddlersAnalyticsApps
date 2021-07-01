package com.onlineshop.ui.detailProduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailProdukViewModel :ViewModel() {
    private val repository = TransaksiRepository()
    fun callData(auth: String?, product_id: String?, product_name: String?, price:String?, pelanggan_id:String?, qty:Int?, category:String?, size:String?, jenis_kelamin:String?, city:String?)
            = repository.callTransaksi(auth,product_id,product_name,price,pelanggan_id,qty,category,size,jenis_kelamin,city)

    fun getData(): MutableLiveData<TransaksiResponse>? = repository.dataTransaksi



    fun callWishlist(auth: String?, product_id: String?, product_name: String?, price:String?, pelanggan_id:String?,
                     stock:Int?, category:String?, size:String?, jenis_kelamin:String?, details:String?, logo:String?)
            = repository.callWishlist(auth,product_id,product_name,price,pelanggan_id,stock,category,size,jenis_kelamin,details,logo)

    fun getWishlist(): MutableLiveData<PostWishlistResponse>? = repository.dataWishlist



    fun clearComposite() = repository.clearComposite()
}