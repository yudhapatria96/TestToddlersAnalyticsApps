package com.onlineshop.ui.detailProduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.onlineshop.R
import com.onlineshop.ui.AuthenticatedShared
import kotlinx.android.synthetic.main.activity_detail_product.*
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*

class DetailProductActivity : AppCompatActivity() {

    private var image: String? = null
    private lateinit var viewModel: DetailProdukViewModel
    private lateinit var authenticationShared: AuthenticatedShared
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        analytics = FirebaseAnalytics.getInstance(this)

        authenticationShared =
            AuthenticatedShared(this)
        viewModel = ViewModelProvider(this).get(DetailProdukViewModel::class.java)

        viewModel.getData()?.observe(this, Observer {data->
            data.let {
                if(data.status){





                    Toast.makeText(DetailProductActivity@this,"Pesanan berhasil dibuat", Toast.LENGTH_LONG).show()
                    pg_detail_product.visibility = View.GONE
                    finish()

                }
            }

        })

        viewModel.getWishlist()?.observe(this, Observer {data->
            data.let {
                if(data.status){
                    Toast.makeText(DetailProductActivity@this,"Berhasil Masuk Wishlist", Toast.LENGTH_LONG).show()
                    pg_detail_product.visibility = View.GONE

                }
            }

        })

        tvNamaProduk.text=intent.getStringExtra("namaProduk")
        tvHargaProduk.text = "Rp. "+ (intent.getIntExtra("hargaProduk",0)).toString()
        stockBarang.text = "Stok: " + (intent.getIntExtra("stok",0)).toString()
        detailProduk.text = intent.getStringExtra("detailProduk");
        image= intent.getStringExtra("logo")
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        sdf.format(Date())
        var order_code: String = sdf.toString()
        Glide.with(this)
            .load( image)
            .into(ivDetailProduct)
        btnBeli.setOnClickListener {
            pg_detail_product.visibility = View.VISIBLE
            viewModel.callData("bearer " + authenticationShared.getToken(), intent.getIntExtra("idProduk",0).toString(),intent.getStringExtra("namaProduk"),
                (intent.getIntExtra("hargaProduk",0)).toString(), authenticationShared.getIdUser().toString(),
                1, intent.getStringExtra("category"), intent.getStringExtra("size"), intent.getStringExtra("jenis_kelamin"), authenticationShared.getCity()
            )

            //analytics
            val params = Bundle()
            params.putString("Product_Name_Transaction", intent.getStringExtra("namaProduk").toString())
            params.putString("Product_Category_Transaction", intent.getStringExtra("category").toString())
            params.putString("Product_Size_Transaction", intent.getStringExtra("size").toString())
            params.putString("Product_Gender_Transaction", intent.getStringExtra("jenis_kelamin").toString())
            params.putString("Product_City_Transaction", authenticationShared.getCity())

            analytics.logEvent("Transaction", params)

        }


        iv_wishlist.setOnClickListener {
                viewModel.callWishlist("bearer " + authenticationShared.getToken(), intent.getIntExtra("idProduk", 0).toString(),intent.getStringExtra("namaProduk"),
                intent.getIntExtra("hargaProduk",0).toString(), authenticationShared.getIdUser().toString(), intent.getIntExtra("stok",0),
                    intent.getStringExtra("category"),intent.getStringExtra("category"),intent.getStringExtra("jenis_kelamin"),
                    intent.getStringExtra("detailProduk"),intent.getStringExtra("logo"))


            val params = Bundle()
            params.putString("Product_Name_Wishlist", intent.getStringExtra("namaProduk").toString())
            params.putString("Product_Category_Wishlist", intent.getStringExtra("category").toString())
            params.putString("Product_Size_Transaction", intent.getStringExtra("size").toString())
            params.putString("Product_Gender_Transaction", intent.getStringExtra("jenis_kelamin").toString())
            params.putString("Product_City_Transaction", authenticationShared.getCity())
            analytics.logEvent(FirebaseAnalytics.Event.ADD_TO_WISHLIST, params)
        }

    }


}