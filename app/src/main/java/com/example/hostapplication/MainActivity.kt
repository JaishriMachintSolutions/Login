package com.example.hostapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.loginlib.login.common.BaseActivity
import com.mymoney.login.view.LoginActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val intent = Intent(this, LoginActivity::class.java)

        Handler().postDelayed(Runnable {
            intent.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

        }, 3000)
    }
}

