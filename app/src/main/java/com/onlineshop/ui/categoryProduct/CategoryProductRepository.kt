package com.onlineshop.ui.categoryProduct

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.onlineshop.network.ApiNetwork
import com.onlineshop.network.ApiService
import com.onlineshop.ui.home.ProductResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class CategoryProductRepository {
    private val apiService: ApiService = ApiNetwork.connectApi()

    //untuk menyimpan token
    var dataProduct = MutableLiveData<ProductResponse>()
    val compositeDisposable= CompositeDisposable()

    fun callApiProduct(auth: String?, category: String?){
        val disposable= apiService.getProductbyCategory(auth, category)
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

    fun callApiProductall(auth: String?){
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

    fun callApiProductByJK(auth: String?, jk: String?){
        val disposable= apiService.getProductbyJK(auth, jk)
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

    fun callApiProductBySearch(auth: String?, search: String?){
        val disposable= apiService.getProductbySearch(auth, search)
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

    fun clearComposite(){
        compositeDisposable.clear()
    }
}