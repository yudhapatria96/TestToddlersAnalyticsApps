package com.onlineshop.ui.wishlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.onlineshop.R
import com.onlineshop.ui.AuthenticatedShared
import com.onlineshop.ui.home.DataProduct
import com.onlineshop.ui.home.HomeAdapter
import com.onlineshop.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_wishlist.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class WishlistActivity : AppCompatActivity() {
    private lateinit var adapter: WishlistAdapter
    private var dataProduct = arrayListOf<DataWishlist>()
    private lateinit var viewModel: WishlistViewModel
    private lateinit var authenticatedShared: AuthenticatedShared
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        authenticatedShared = AuthenticatedShared(this)

        viewModel = ViewModelProvider(this).get(WishlistViewModel::class.java)
        adapter= WishlistAdapter(this, dataProduct)

        viewModel.getDataProduct()?.observe(this, Observer {dataProducts->
            dataProducts.let {
                if(dataProducts.status) {
                    dataProduct.addAll(dataProducts.data)
                    adapter.notifyDataSetChanged()
                    rv_product.visibility = View.VISIBLE

                }else{
                   tv_wishlist_kosong.visibility = View.VISIBLE
                }

                pg_home.visibility = View.GONE


            }
        })

        val layoutManger = GridLayoutManager(this, 2)
        rv_product.layoutManager = layoutManger
        rv_product.adapter = adapter

        viewModel.callData("bearer " + authenticatedShared.getToken(), authenticatedShared.getIdUser())

    }
}