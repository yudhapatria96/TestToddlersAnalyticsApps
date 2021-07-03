package com.onlineshop.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.onlineshop.MainActivity
import com.onlineshop.R
import com.onlineshop.ui.AuthenticatedShared
import com.onlineshop.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var authenticationShared: AuthenticatedShared
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        analytics = FirebaseAnalytics.getInstance(this)
        authenticationShared =
            AuthenticatedShared(this)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        viewModel.getData()?.observe(this, Observer { token ->

            recordRegister()
            setToken(token)
        })

        viewModel.getDataError()?.observe(this, {
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
        })

        btn_register.setOnClickListener {
            when {
                edt_email.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Silahkan Isi terlebih dahulu field email", Toast.LENGTH_SHORT).show()
                }
                edt_password.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Silahkan Isi terlebih dahulu field password", Toast.LENGTH_SHORT).show()
                }
                edt_name.text.isNullOrEmpty() ->{
                    Toast.makeText(this, "Silahkan Isi terlebih dahulu field nama", Toast.LENGTH_SHORT).show()
                }
                edt_city.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Silahkan Isi terlebih dahulu field email", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    viewModel.callData(edt_email.text.toString(), edt_password.text.toString(), edt_name.text.toString(), edt_city.text.toString())
                }
            }
        }

    }

    private fun recordRegister() {
        val params = Bundle()
        params.putString("user_register",edt_city.text.toString())
        analytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, params)
    }

    private fun setToken(token: String?){
        token?.let { authenticationShared.createSession(it) }
//        Util.nextActivity(this, MainActivity::class.java)
        var i  = Intent(this, LoginActivity::class.java)

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(i)
        finish()
    }


}