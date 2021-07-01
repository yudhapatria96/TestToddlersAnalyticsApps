package com.onlineshop.ui.detailProduct

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.onlineshop.network.ApiNetwork
import com.onlineshop.network.ApiService
import com.onlineshop.ui.profile.ResponseUpdateProfile
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketException
import java.net.UnknownHostException

class TransaksiRepository {

    private val apiService: ApiService = ApiNetwork.connectApi()

    //untuk menyimpan token
    var dataTransaksi = MutableLiveData<TransaksiResponse>()
    var dataWishlist = MutableLiveData<PostWishlistResponse>()

    val compositeDisposable= CompositeDisposable()

    fun callTransaksi(auth: String?, product_id: String?, product_name: String?, price:String?, pelanggan_id:String?,
                      qty:Int?, category:String?, size:String?, jenis_kelamin:String?, city:String?){
        val jsonTransaksi = JsonObject()
        jsonTransaksi.addProperty("product_id", product_id)
        jsonTransaksi.addProperty("product_name", product_name)
        jsonTransaksi.addProperty("price", price)
        jsonTransaksi.addProperty("pelanggan_id", pelanggan_id)
        jsonTransaksi.addProperty("qty", qty)
        jsonTransaksi.addProperty("category", category)
        jsonTransaksi.addProperty("size", size)
        jsonTransaksi.addProperty("jenis_kelamin", jenis_kelamin)
        jsonTransaksi.addProperty("city", city)
        val disposable= apiService.postTransaksi(auth,jsonTransaksi)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<Response<TransaksiResponse>> {
                override fun accept(t: Response<TransaksiResponse>?) {
                    Log.d("apayaaa", t?.body()?.message.toString())
                    dataTransaksi.postValue(t?.body())
                }
                }, object : Consumer<Throwable> {
                override fun accept(t: Throwable?) {
                    Log.e("apayaaa", t?.printStackTrace().toString())

                }
            }
            )
        compositeDisposable.add(disposable)
    }

    fun callWishlist(auth: String?, product_id: String?, product_name: String?, price:String?, pelanggan_id:String?,
                      stock:Int?, category:String?, size:String?, jenis_kelamin:String?, details:String?, logo:String?){
        val jsonTransaksi = JsonObject()
        jsonTransaksi.addProperty("product_id", product_id)
        jsonTransaksi.addProperty("product_name", product_name)
        jsonTransaksi.addProperty("details", details)
        jsonTransaksi.addProperty("logo", logo)
        jsonTransaksi.addProperty("price", price)
        jsonTransaksi.addProperty("pelanggan_id", pelanggan_id)
        jsonTransaksi.addProperty("stock", stock)
        jsonTransaksi.addProperty("category", category)
        jsonTransaksi.addProperty("size", size)
        jsonTransaksi.addProperty("jenis_kelamin", jenis_kelamin)
        val disposable= apiService.postWishlist(auth,jsonTransaksi)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<Response<PostWishlistResponse>> {
                override fun accept(t: Response<PostWishlistResponse>?) {
                    Log.d("apayaaa", t?.body()?.message.toString())
                    dataWishlist.postValue(t?.body())
                }
            }, object : Consumer<Throwable> {
                override fun accept(t: Throwable?) {
                    Log.e("apayaaa", t?.printStackTrace().toString())

                }
            }
            )
        compositeDisposable.add(disposable)
    }

    fun clearComposite(){
        compositeDisposable.clear()
    }

}
