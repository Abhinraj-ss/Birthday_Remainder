package com.abhinraj.birthdayremainder.util

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.abhinraj.birthdayremainder.R
import com.abhinraj.birthdayremainder.activity.MainActivity

class NotificationService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
        return null
    }


    override fun onCreate() {
    }

    @SuppressLint("WrongConstant")
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val channelId = "${this.packageName}-${this.getString(R.string.app_name)}"
        val bundle = intent.getBundleExtra("data")
        val name = bundle!!.getString("name")
        val age = bundle.getInt("age",0)
        val gender = bundle.getString("gender")
        val noOfDays= bundle.getInt("days", 0)
        System.out.println("${name} ${age} ${gender} ${noOfDays}")
        val flags = intent.flags
        var pronoun =""
        if (gender=="Male"){
            pronoun="his"
        }else if(gender=="Female"){
            pronoun ="her"
        }else if(gender=="Prefer not to say"){
            pronoun ="their"
        }
        val notificationBuilder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.drawable.ic_baseline_cake_24)
            setContentTitle("${name} is going to be ${age+1} in next ${noOfDays} days.")
            setContentText("message")
            setStyle(NotificationCompat.BigTextStyle().bigText("Make ${pronoun} birthday memorable."))
            priority = NotificationCompat.PRIORITY_HIGH
            setAutoCancel(true)
            color = NotificationCompat.COLOR_DEFAULT
            setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            setContentIntent(
                PendingIntent.getActivity(
                    this@NotificationService, // Context from onReceive method.
                    0,
                    Intent(this@NotificationService, MainActivity::class.java), // Activity you want to launch onClick.
                    flags
                ))
        }
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(1001, notificationBuilder.build())
        return START_STICKY
    }



}