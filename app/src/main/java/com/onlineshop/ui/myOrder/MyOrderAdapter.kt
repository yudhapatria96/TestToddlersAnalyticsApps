package com.onlineshop.ui.myOrder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.onlineshop.R
import kotlinx.android.synthetic.main.item_order.view.*

class MyOrderAdapter (val context: Context, var dataItem: List<DataOrder>) : RecyclerView.Adapter<MyOrderAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataItem[position]
        holder.tvNamaOrder.text= data.product_name
        holder.tvHargaOrder.text= "Rp. " + data.price.toString()
        holder.tvInvoice.text= data.order_code
        holder.tvJumlahBeli.text = "Jumlah Pembelian: "+data.qty.toString()


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage = itemView.img_order
        val tvNamaOrder = itemView.tv_nama_produk
        val tvHargaOrder = itemView.tv_harga_produk
        val orderContainer = itemView.orderContainer
        val tvInvoice = itemView.tv_invoice
        val tvJumlahBeli = itemView.tv_jumlah_dibeli
    }


}