package com.onlineshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.onlineshop.ui.AuthenticatedShared
import com.onlineshop.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    lateinit var authenticatedShared: AuthenticatedShared
    lateinit var analytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        authenticatedShared = AuthenticatedShared(this)
        analytics = FirebaseAnalytics.getInstance(this)

        if(!authenticatedShared.getCity().isNullOrEmpty() && !authenticatedShared.getToken().isNullOrEmpty()){
            val params = Bundle()
            params.putString("Login_city",authenticatedShared.getCity())
            analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, params)
            Handler(Looper.getMainLooper()).postDelayed({

                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            }, 3000)
        }else{
            Handler(Looper.getMainLooper()).postDelayed({

                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }, 3000)
        }



    }
}