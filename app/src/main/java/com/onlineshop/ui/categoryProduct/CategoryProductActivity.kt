package com.onlineshop.ui.categoryProduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.onlineshop.R
import com.onlineshop.ui.AuthenticatedShared
import com.onlineshop.ui.home.*
import kotlinx.android.synthetic.main.activity_category_product.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.ll_home
import kotlinx.android.synthetic.main.fragment_home.rv_product
import kotlinx.android.synthetic.main.fragment_home.view.*

class CategoryProductActivity : AppCompatActivity() {

    private lateinit var viewModel: CategoryProductViewModel
    private lateinit var authenticatedShared: AuthenticatedShared
    private lateinit var adapter: CategoryProductAdapter
    private var dataProduct = arrayListOf<DataProduct>()
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_product)

        viewModel =
            ViewModelProviders.of(this).get(CategoryProductViewModel::class.java)

        authenticatedShared =
            AuthenticatedShared(this)
        adapter= CategoryProductAdapter(this, dataProduct)

        analytics = FirebaseAnalytics.getInstance(this)

        viewModel.getDataProduct()?.observe(this,  { data->
            data.let {
                ph_cp.visibility = View.GONE

                if(data.data.isNullOrEmpty()){
                    tv_product_zonk.visibility = View.VISIBLE
                }else{
                        dataProduct.addAll(data.data)
                        adapter.notifyDataSetChanged()
                        rv_product_category.visibility = View.VISIBLE
                        ll_home_category.visibility = View.VISIBLE
                    }


            }

        })
        val layoutManger = GridLayoutManager(this, 2)
        rv_product_category.layoutManager = layoutManger
        rv_product_category.adapter = adapter
        val params = Bundle()
        if(intent.getBooleanExtra("search",false)){
            viewModel.callDatabySearch(
                "bearer " + authenticatedShared.getToken(),
                intent.getStringExtra("category").toString()
            )
            params.putString("Search_Product",intent.getStringExtra("category").toString())

        }else {
            if (intent.getStringExtra("category").toString().equals("All Category")) {
                viewModel.callDataAll(
                    "bearer " + authenticatedShared.getToken()
                )
            } else if (intent.getStringExtra("category").toString()
                    .equals("Boy") || intent.getStringExtra("category").toString().equals("Girl")
            ) {
                if (intent.getStringExtra("category").toString().equals("Boy")) {
                    viewModel.callDatabyJK(
                        "bearer " + authenticatedShared.getToken(),
                        "pria"
                    )
                } else {
                    viewModel.callDatabyJK(
                        "bearer " + authenticatedShared.getToken(),
                        "wanita"
                    )


                }
                params.putString("Gender", intent.getStringExtra("category").toString())

            } else {
                viewModel.callData(
                    "bearer " + authenticatedShared.getToken(),
                    intent.getStringExtra("category").toString()
                )


            }
            params.putString("name_category",intent.getStringExtra("category").toString())

        }

        analytics.logEvent("CategoryClickInterest", params)

        iv_back.setOnClickListener {
            finish()
        }

        tv_name_category.text =  intent.getStringExtra("category").toString()
    }
}