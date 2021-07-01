package com.onlineshop.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.analytics.FirebaseAnalytics
import com.onlineshop.MainActivity
import com.onlineshop.R
import com.onlineshop.ui.AuthenticatedShared
import com.onlineshop.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edt_email
import kotlinx.android.synthetic.main.activity_login.edt_password
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var authenticatedShared: AuthenticatedShared
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authenticatedShared = AuthenticatedShared(this)
        analytics = FirebaseAnalytics.getInstance(this)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        //fungsi cek pernah login atau tidak
        authenticatedShared.getToken().let {
            if(!authenticatedShared.getToken().equals("")){

                if(authenticatedShared.getCity().isNullOrEmpty()) {
                    callProfile(authenticatedShared.getToken().toString())

                }else{

                    val params = Bundle()
                    params.putString("Login_city", authenticatedShared.getCity().toString())
                    analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, params)
                    var i = Intent(this, MainActivity::class.java)
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

                    startActivity(i)
                    finish()
                }
            }
        }


        viewModel.getData()?.observe(this, Observer {token->

            if(token!=null){
                authenticatedShared.createSession(token)
                callProfile(token)

            }else{
                Toast.makeText(this,"Email atau Password Salah", Toast.LENGTH_LONG).show()
            }

        })

        viewModel.getDataProfile()?.observe(this, Observer { dataProfile->
            dataProfile?.let {

                val params = Bundle()
                params.putString("Login_city",dataProfile.city)
                analytics.logEvent(FirebaseAnalytics.Event.LOGIN, params)

                pgLogin.visibility = View.GONE
                authenticatedShared.createProfile(it)
                var i  = Intent(this, MainActivity::class.java)
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

                startActivity(i)
                finish()
            }
        })


        btn_login.setOnClickListener {
            when {
                edt_email.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Silahkan Isi terlebih dahulu email nya", Toast.LENGTH_SHORT).show()
                }
                edt_password.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Silahkan Isi terlebih dahulu passwordnya nya", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    pgLogin.visibility = View.VISIBLE
                    viewModel.callData(edt_email.text.toString(), edt_password.text.toString())
                }
            }
        }

        tv_register.setOnClickListener {
            var i: Intent =  Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }


    }

    private fun callProfile(token: String) {
        viewModel.callDataProfile("bearer "+token)
    }
}