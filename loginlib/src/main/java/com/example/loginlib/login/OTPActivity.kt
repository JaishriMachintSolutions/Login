package com.mymoney.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.loginlib.R
import com.example.loginlib.login.DashBoardActivity
import com.example.loginlib.login.common.BaseActivity

class OTPActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        val btnSubmit: Button = findViewById(R.id.btnSubmit)
        val edt1: EditText = findViewById(R.id.edt1)
        val edt2: EditText = findViewById(R.id.edt2)
        val edt3: EditText = findViewById(R.id.edt3)
        val edt4: EditText = findViewById(R.id.edt4)

        btnSubmit.setOnClickListener {
          //  Toast.makeText(this, "Login Successful!!", Toast.LENGTH_SHORT).show()

            Log.d("Login Message: ", "Sucess")

            val dashBoardIntent =  Intent(this, DashBoardActivity::class.java)
            startActivity(dashBoardIntent)
        }

        //GenericTextWatcher here works only for moving to next EditText when a number is entered
//first parameter is the current EditText and second parameter is next EditText
        edt1.addTextChangedListener(GenericTextWatcher(edt1, edt2))
        edt2.addTextChangedListener(GenericTextWatcher(edt2, edt3))
        edt3.addTextChangedListener(GenericTextWatcher(edt3, edt4))
        edt4.addTextChangedListener(GenericTextWatcher(edt4, null))

//GenericKeyEvent here works for deleting the element and to switch back to previous EditText
//first parameter is the current EditText and second parameter is previous EditText
        edt1.setOnKeyListener(GenericKeyEvent(edt1, null))
        edt2.setOnKeyListener(GenericKeyEvent(edt2, edt1))
        edt3.setOnKeyListener(GenericKeyEvent(edt3, edt2))
        edt4.setOnKeyListener(GenericKeyEvent(edt4, edt3))


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    class GenericKeyEvent internal constructor(private val currentView: EditText, private val previousView: EditText?) : View.OnKeyListener {
        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.edt1 && currentView.text.isEmpty()) {
                //If current is empty then previous EditText's number will also be deleted
                previousView!!.text = null
                previousView.requestFocus()
                return true
            }
            return false
        }


    }

    class GenericTextWatcher internal constructor(private val currentView: View, private val nextView: View?) : TextWatcher {
        override fun afterTextChanged(editable: Editable) { // TODO Auto-generated method stub
            val text = editable.toString()
            when (currentView.id) {
                R.id.edt1 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.edt2 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.edt3 -> if (text.length == 1) nextView!!.requestFocus()
                //You can use EditText4 same as above to hide the keyboard
            }
        }

        override fun beforeTextChanged(
                arg0: CharSequence,
                arg1: Int,
                arg2: Int,
                arg3: Int
        ) { // TODO Auto-generated method stub
        }

        override fun onTextChanged(
                arg0: CharSequence,
                arg1: Int,
                arg2: Int,
                arg3: Int
        ) { // TODO Auto-generated method stub
        }

    }


}