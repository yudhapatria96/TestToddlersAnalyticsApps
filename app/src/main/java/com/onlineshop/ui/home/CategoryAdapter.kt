package com.onlineshop.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.onlineshop.R
import com.onlineshop.ui.categoryProduct.CategoryProductActivity
import com.onlineshop.ui.detailProduct.DetailProductActivity


class CategoryAdapter(val context: Context?, val dataCategory: ArrayList<DataCategory>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataCategories = dataCategory[position]

        holder.imageCategory?.let {
            context?.let { context->
                Glide.with(context)
                    .load("https://onlineshopapi.herokuapp.com/"+ dataCategories.logo)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(it)

            }
        }

        holder.textCategory?.text = dataCategories.name

        holder.cl_category?.setOnClickListener {
            var i = Intent(context, CategoryProductActivity::class.java)
            i.putExtra("category", dataCategories.name )
            context?.startActivity(i)
        }

        }

    override fun getItemCount(): Int {
       return dataCategory.size
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageCategory: ImageView? = itemView.findViewById(R.id.iv_category)
        val textCategory: TextView? = itemView.findViewById(R.id.tv_category)
        val cl_category: ConstraintLayout? = itemView.findViewById(R.id.cl_category)
    }
}