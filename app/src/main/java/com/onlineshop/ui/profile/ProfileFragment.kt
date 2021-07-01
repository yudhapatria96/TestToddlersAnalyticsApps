package com.onlineshop.ui.profile

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.onlineshop.R
import com.onlineshop.ui.AuthenticatedShared
import com.onlineshop.ui.detailProduct.DetailProductActivity
import com.onlineshop.ui.login.LoginActivity
import com.onlineshop.ui.wishlist.WishlistActivity
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var notificationsViewModel: ProfileViewModel
    private lateinit var authenticationShared: AuthenticatedShared

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        notificationsViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        authenticationShared = AuthenticatedShared(requireContext())

        root.et_nama.setText(authenticationShared.getNamaUser())
        root.et_email.setText(authenticationShared.getEmailUser())
        root.et_city.setText(authenticationShared.getCity())
        notificationsViewModel.getData()?.observe(viewLifecycleOwner, Observer {dataProfile->

            if(dataProfile!=null) {
                Toast.makeText(requireContext(), dataProfile.message.toString(), Toast.LENGTH_LONG).show()
                if(dataProfile.data.size!=0) {

                    authenticationShared.updateProfile(dataProfile.data?.get(0))

                }

            }
        })

        root.btn_save.setOnClickListener {
            if(root.et_password.text.toString().equals(authenticationShared.getPassword())){
                notificationsViewModel.callData("bearer " + authenticationShared.getToken(),root.et_email.text.toString(),
                    root.et_nama.text.toString(), authenticationShared.getIdUser().toString())
                Log.d("apanihhh", authenticationShared.getIdUser().toString())
            }else{
                Toast.makeText(requireContext(), "Password Salah", Toast.LENGTH_LONG).show()
            }
        }

        root.btn_logout.setOnClickListener {
            logout()
        }

        root.btn_keranjang.setOnClickListener {

        }

        root.btn_wishlist.setOnClickListener {
            var i = Intent(context, WishlistActivity::class.java)
            startActivity(i)
        }
        return root
    }

    private fun logout() {
        val builder =
            AlertDialog.Builder(requireContext())
        builder.setMessage("Apakah anda yakin akan logout?")
        builder.setPositiveButton(
            "Ya"
        ) { dialogInterface: DialogInterface?, i: Int ->
            authenticationShared.closeSession()
            var i = Intent(requireContext(), LoginActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

            startActivity(i)
            requireActivity().finish()
        }
        builder.setNegativeButton(
            "Tidak"
        ) { dialogInterface: DialogInterface, i: Int -> dialogInterface.dismiss() }
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}