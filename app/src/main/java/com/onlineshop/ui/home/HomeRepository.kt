package com.onlineshop.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.onlineshop.network.ApiNetwork
import com.onlineshop.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class HomeRepository {
    private val apiService: ApiService = ApiNetwork.connectApi()

    //untuk menyimpan token
    var dataProduct = MutableLiveData<ProductResponse>()
    var dataCategory = MutableLiveData<CategoryResponse>()
    val compositeDisposable= CompositeDisposable()

    fun callApiProduct(auth: String?){
        val disposable= apiService.getProduct(auth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<Response<ProductResponse>> {
                override fun accept(t: Response<ProductResponse>?) {

                    dataProduct.postValue(t?.body())

                }
            }, object : Consumer<Throwable> {
                override fun accept(t: Throwable?) {
                    Log.e("masuk", "error")
                }
            }
            )
        compositeDisposable.add(disposable)
    }

    fun callApiCategory(auth: String?){
        val disposable= apiService.getCategory(auth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<Response<CategoryResponse>> {
                override fun accept(t: Response<CategoryResponse>?) {

                    dataCategory.postValue(t?.body())

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