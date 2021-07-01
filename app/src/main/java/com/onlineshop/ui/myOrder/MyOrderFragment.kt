package com.onlineshop.ui.myOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.onlineshop.R
import com.onlineshop.ui.AuthenticatedShared
import kotlinx.android.synthetic.main.fragment_my_order.view.*

class MyOrderFragment : Fragment() {

    private lateinit var myOrderViewModel: MyOrderViewModel
    private lateinit var myOrderAdapter: MyOrderAdapter
    private var dataOrder = arrayListOf<DataOrder>()
    private lateinit var authenticationShared: AuthenticatedShared
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_my_order, container, false)

        myOrderAdapter = MyOrderAdapter(requireContext(), dataOrder)
        authenticationShared =
           AuthenticatedShared(requireContext())

        myOrderViewModel = ViewModelProvider(this).get(MyOrderViewModel::class.java)

        myOrderViewModel.getDataProduct()?.observe(viewLifecycleOwner, Observer{data->
            dataOrder.addAll(data.data)
            myOrderAdapter.notifyDataSetChanged()
            root.pg_my_order.visibility = View.GONE
            root.rv_my_order.visibility = View.VISIBLE
        })

        val layoutManger = LinearLayoutManager(requireContext())
        root.rv_my_order.layoutManager = layoutManger
        root.rv_my_order.adapter = myOrderAdapter

        myOrderViewModel.callData("bearer "+ authenticationShared.getToken(), authenticationShared.getIdUser())


        return root
    }
}