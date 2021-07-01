package com.onlineshop.ui.myOrder

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.onlineshop.network.ApiNetwork
import com.onlineshop.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MyOrderRepository {
    private val apiService: ApiService = ApiNetwork.connectApi()

    //untuk menyimpan token
    var dataOrder = MutableLiveData<OrderResponse>()

    val compositeDisposable= CompositeDisposable()

    fun callApiOrder(auth: String?, idPelanggan: Int?){
        val disposable= apiService.getTransaksi(auth, idPelanggan)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<Response<OrderResponse>> {
                override fun accept(t: Response<OrderResponse>?) {

                    dataOrder.postValue(t?.body())

                }
            }, object : Consumer<Throwable> {
                override fun accept(t: Throwable?) {
                    Log.e("masuk", "error")
                }
            }
            )
        compositeDisposable.add(disposable)
    }

    fun clearComposite(){
        compositeDisposable.clear()
    }
}