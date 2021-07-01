package com.onlineshop.ui.wishlist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.onlineshop.R
import com.onlineshop.ui.detailProduct.DetailProductActivity
import com.onlineshop.ui.home.DataProduct
import kotlinx.android.synthetic.main.item_produk.view.*

class WishlistAdapter (val context: Context, var dataItem: List<DataWishlist>) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataItem[position]
        holder.tvNamaProduct.text= data.product_name
        holder.tvHargaProduct.text= "Rp. " + data.price.toString()

        Glide.with(context)
            .load(data.logo)
            .into(holder.ivImage)


        holder.productContainer.setOnClickListener {
            var i = Intent(context, DetailProductActivity::class.java)
            i.putExtra("namaProduk",data.product_name )
            i.putExtra("hargaProduk", data.price)
            i.putExtra("stok", data.stock)
            i.putExtra("logo", data.logo)
            i.putExtra("detailProduk", data.details)
            i.putExtra("idProduk", data.id)
            i.putExtra("category", data.category)
            i.putExtra("size", data.size)
            i.putExtra("jenis_kelamin", data.jenis_kelamin)

            context.startActivity(i)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage = itemView.img_product
        val tvNamaProduct = itemView.tv_nama_produk
        val tvHargaProduct = itemView.tv_harga_produk
        val productContainer = itemView.productContainer
    }


}