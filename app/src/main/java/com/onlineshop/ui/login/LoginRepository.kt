package com.onlineshop.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.onlineshop.network.ApiNetwork
import com.onlineshop.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class LoginRepository {

    private val apiService: ApiService = ApiNetwork.connectApi()

    //untuk menyimpan token
    var dataToken = MutableLiveData<String>()

    //untuk menyimpan data response profile
    var dataProfile = MutableLiveData<ProfileResponse>()

    val compositeDisposable= CompositeDisposable()

    fun callApiLogin(email: String?, password: String?){
        val jsonLogin = JsonObject()
        jsonLogin.addProperty("email", email)
        jsonLogin.addProperty("password", password)
        val disposable= apiService.getAuth(jsonLogin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<Response<LoginResponse>> {
                override fun accept(t: Response<LoginResponse>?) {

                    dataToken.postValue(t?.body()?.access_token)

                }
            }, object : Consumer<Throwable> {
                override fun accept(t: Throwable?) {
                    Log.e("masuk", t?.message.toString())
                }
            }
            )
        compositeDisposable.add(disposable)
    }

    fun callApiProfile(auth: String?){
        val disposable= apiService.getProfile(auth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<Response<ProfileResponse>> {
                override fun accept(t: Response<ProfileResponse>?) {

                    dataProfile.postValue(t?.body())
                }
            }, object : Consumer<Throwable> {
                override fun accept(t: Throwable?) {
                    Log.e("masuk", t.toString())
                }
            }
            )
        compositeDisposable.add(disposable)
    }

    fun clearComposite(){
        compositeDisposable.clear()
    }

}