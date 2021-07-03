package com.onlineshop.network

import com.google.gson.JsonObject
import com.onlineshop.ui.detailProduct.PostWishlistResponse
import com.onlineshop.ui.detailProduct.TransaksiResponse
import com.onlineshop.ui.home.CategoryResponse
import com.onlineshop.ui.home.ProductResponse
import com.onlineshop.ui.login.LoginResponse
import com.onlineshop.ui.login.ProfileResponse
import com.onlineshop.ui.myOrder.OrderResponse
import com.onlineshop.ui.profile.ResponseUpdateProfile
import com.onlineshop.ui.register.RegisterResponse
import com.onlineshop.ui.wishlist.WishlistResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("api/auth/login")
    fun getAuth(@Body data : JsonObject): Observable<Response<LoginResponse>>

    @POST("api/auth/me")
    fun getProfile(@Header("Authorization") token: String?): Observable<Response<ProfileResponse>>

    @POST("api/auth/register")
    fun postRegister(@Body data: JsonObject): Observable<Response<RegisterResponse>>

    @GET("api/product")
        fun getProduct(@Header("Authorization") token: String?): Observable<Response<ProductResponse>>

    @GET("api/product")
    fun getProductbyCategory(@Header("Authorization") token: String?, @Query("category") category: String?): Observable<Response<ProductResponse>>

    @GET("api/product")
    fun getProductbyJK(@Header("Authorization") token: String?, @Query("jenis_kelamin") jenis_kelamin: String?): Observable<Response<ProductResponse>>


    @GET("api/product")
    fun getProductbySearch(@Header("Authorization") token: String?, @Query("search") search: String?): Observable<Response<ProductResponse>>


    @POST("api/order")
    fun postTransaksi(@Header("Authorization") token: String?, @Body data: JsonObject): Observable<Response<TransaksiResponse>>

    @GET("api/order")
    fun getTransaksi(@Header("Authorization") token: String?, @Query("id_pelanggan") idPelanggan: Int?): Observable<Response<OrderResponse>>

    @PUT("api/auth/userupdate")
    fun updateUser(@Header("Authorization") token: String?, @Body data: JsonObject): Observable<Response<ResponseUpdateProfile>>

    @GET("api/wishlist")
    fun getWishlist(@Header("Authorization") token: String?, @Query("id_pelanggan") idPelanggan: Int?): Observable<Response<WishlistResponse>>

    @GET("api/category")
    fun getCategory(@Header("Authorization") token: String?): Observable<Response<CategoryResponse>>

    @POST("api/wishlist")
    fun postWishlist(@Header("Authorization") token: String?, @Body data: JsonObject): Observable<Response<PostWishlistResponse>>
}