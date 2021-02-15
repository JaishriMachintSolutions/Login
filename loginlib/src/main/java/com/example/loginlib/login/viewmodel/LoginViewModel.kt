package com.mymoney.login.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginlib.R
import com.mymoney.login.OTPActivity
import com.mymoney.login.model.LoginModel

class LoginViewModel : ViewModel() {


    val mobileNumber: MutableLiveData<String> = MutableLiveData()
    val countryCode: MutableLiveData<String> = MutableLiveData()

    private lateinit var user: MutableLiveData<LoginModel>

    fun getUser(): MutableLiveData<LoginModel> {
        /*if (user == null) {
            user = MutableLiveData<LoginModel>()
        }*/
        user = MutableLiveData<LoginModel>()
        return user
    }

    fun onClick(view: View, context: Context) {

        /*viewModelScope.launch {

        }*/

      //  view2.visibility = View.VISIBLE

        var loginModel: LoginModel =
            LoginModel(
                mobileNumber.value.toString(),"+91"
            )
        user.value = loginModel
        val otpIntent : Intent = Intent(context, OTPActivity::class.java)
        otpIntent.apply {
            when (loginModel.mobileNo.length) {
                10 -> {

                    this.flags = FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(this)

                    sendOTP(context)
                }
            }
        }

        //view2.visibility = View.GONE




    }

    fun sendOTP( context: Context){



        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        val CHANNEL_ID = "mymoneyID"
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system

            notificationManager.createNotificationChannel(channel)
        }

        // Create an explicit intent for an Activity in your app
        val intent = Intent(context, OTPActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("My Money")
            .setContentText("1234")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        notificationManager.notify(1, builder.build())



    }




}