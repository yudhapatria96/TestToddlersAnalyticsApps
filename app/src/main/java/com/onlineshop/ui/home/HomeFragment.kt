package com.onlineshop.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.onlineshop.R
import com.onlineshop.ui.AuthenticatedShared
import com.onlineshop.ui.categoryProduct.CategoryProductActivity
import com.onlineshop.ui.wishlist.WishlistActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var authenticatedShared: AuthenticatedShared
    private lateinit var adapter:HomeAdapter
    private var search: String = ""
    private var dataProduct = arrayListOf<DataProduct>()
    private var dataCategory = arrayListOf<DataCategory>()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var gridLayoutManger : GridLayoutManager

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        authenticatedShared =
            AuthenticatedShared(requireContext())
        adapter= HomeAdapter(requireContext(), dataProduct)
        homeViewModel.getDataProduct()?.observe(viewLifecycleOwner, Observer { data->
            data.let {
                dataProduct.addAll(data.data)
                adapter.notifyDataSetChanged()
                root.rv_product.visibility = View.VISIBLE
                root.ll_home.visibility = View.VISIBLE
                ph_home.visibility = View.GONE
            }

        })

        categoryAdapter = CategoryAdapter(requireContext(), dataCategory)

        homeViewModel.getDataCategory()?.observe(viewLifecycleOwner, Observer { datasCategory->
            datasCategory.let {
                dataCategory.addAll(datasCategory.data)
                categoryAdapter.notifyDataSetChanged()
                root.rv_product.visibility = View.VISIBLE
                root.ll_home.visibility = View.VISIBLE
                ph_home.visibility = View.GONE
            }
        })

        val layoutManger = GridLayoutManager(requireContext(), 2)
        root.rv_product.layoutManager = layoutManger
        root.rv_product.adapter = adapter

        gridLayoutManger = GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)
        root.rv_category.layoutManager = gridLayoutManger
        root.rv_category.adapter = categoryAdapter

        homeViewModel.callCategory("bearer "+  authenticatedShared.getToken())
        homeViewModel.callData("bearer "+  authenticatedShared.getToken())

        root.img_notification.setOnClickListener {
            startActivity(Intent(requireContext(), WishlistActivity::class.java))
        }
        root.et_search.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                var i = Intent(context, CategoryProductActivity::class.java)
                i.putExtra("category", root.et_search.text.substring(0, 1).toUpperCase() +root.et_search.text.substring(1))
                i.putExtra("search", true)
                context?.startActivity(i)
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as
                        InputMethodManager
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                return@OnEditorActionListener true
            }
            false
        })

        return root
    }
}