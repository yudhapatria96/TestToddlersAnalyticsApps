package com.onlineshop.ui.register

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.onlineshop.network.ApiNetwork
import com.onlineshop.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class RegisterRepository {
    private val apiService: ApiService = ApiNetwork.connectApi()

    //untuk menyimpan token
    var dataToken = MutableLiveData<String>()
    var dataError = MutableLiveData<String>()
    val compositeDisposable= CompositeDisposable()

    fun callApiRegister(email: String?, password: String?, name: String?, city: String?){
        val jsonLogin = JsonObject()
        jsonLogin.addProperty("email", email)
        jsonLogin.addProperty("password", password)
        jsonLogin.addProperty("name", name)
        jsonLogin.addProperty("password_confirmation", password)
        jsonLogin.addProperty("city", city)
        val disposable= apiService.postRegister(jsonLogin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<Response<RegisterResponse>> {
                override fun accept(t: Response<RegisterResponse>?) {

                    dataToken.postValue(t?.body()?.access_token)

                }
            }, object : Consumer<Throwable> {
                override fun accept(t: Throwable?) {
                   dataError.postValue(t?.message)
                }
            }
            )
        compositeDisposable.add(disposable)
    }

    fun clearComposite(){
        compositeDisposable.clear()
    }
}