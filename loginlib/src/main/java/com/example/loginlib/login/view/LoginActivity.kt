package com.mymoney.login.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.loginlib.R
import com.example.loginlib.databinding.ActivityLoginBinding
import com.example.loginlib.login.common.BaseActivity
import com.mymoney.login.viewmodel.LoginViewModel
import kotlinx.coroutines.*


class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val activityLoginBinding: ActivityLoginBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_login
        )

        activityLoginBinding.setLifecycleOwner(this);
        val loginViewModel: LoginViewModel = ViewModelProviders.of(this).get(
            LoginViewModel::class.java
        )

        activityLoginBinding.loginViewModel = loginViewModel

        var codes = arrayOf<String?>("+1", "+91")
        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item,
            codes
        )
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        activityLoginBinding.spnCountryCode.adapter = ad

        activityLoginBinding.spnCountryCode.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }

            }


        loginViewModel.getUser().observe(this, Observer {

            when {
                activityLoginBinding.edtMobileNo.equals("") || activityLoginBinding.edtMobileNo.length() != 10 -> {
                    activityLoginBinding.edtMobileNo.error = "Please enter valid mobile number!"
                    activityLoginBinding.edtMobileNo.requestFocus()
                }
                else -> {
                    val scope = CoroutineScope(Job() + Dispatchers.Main)
                    scope.launch {
                        loginViewModel.onClick(activityLoginBinding.btnLogin, applicationContext)
                    }
                    scope.cancel()
                }
            }

        })
    }


}