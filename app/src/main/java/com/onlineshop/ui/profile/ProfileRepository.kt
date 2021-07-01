package com.onlineshop.ui.profile

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

class ProfileRepository {

    private val apiService: ApiService = ApiNetwork.connectApi()


    //untuk menyimpan data response profile
    var dataProfileUpdate = MutableLiveData<ResponseUpdateProfile>()

    val compositeDisposable= CompositeDisposable()

    fun callApiUpdate(auth:String?, email: String?, name: String?, id: String?){
        val jsonUpdate= JsonObject()
        jsonUpdate.addProperty("email", email)
        jsonUpdate.addProperty("name", name)
        jsonUpdate.addProperty("idUser", id)

        val disposable= apiService.updateUser(auth,jsonUpdate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<Response<ResponseUpdateProfile>> {
                override fun accept(t: Response<ResponseUpdateProfile>?) {
                    Log.d("apayaaa", t?.body()?.message.toString())
                    dataProfileUpdate.postValue(t?.body())

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