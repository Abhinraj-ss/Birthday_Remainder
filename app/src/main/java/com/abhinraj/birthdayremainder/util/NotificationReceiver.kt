package com.abhinraj.birthdayremainder.util

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.abhinraj.birthdayremainder.R
import com.abhinraj.birthdayremainder.activity.MainActivity




class NotificationReceiver : BroadcastReceiver() {
    @SuppressLint("WrongConstant", "UnsafeProtectedBroadcastReceiver", "ResourceAsColor")
    override fun onReceive(context: Context, intent: Intent) {
        val args:Bundle= intent.getBundleExtra("data")
        val notification = Intent(context, NotificationService::class.java).apply{
            putExtra("data",args)
        }
        ContextCompat.startForegroundService(context,notification)
        /*
val channelId = "${context!!.packageName}-${context.getString(R.string.app_name)}"
        val name = intent.getStringExtra("name")
        val age = intent.getIntExtra("age",0)
        val gender = intent.getStringExtra("gender")
        val noOfDays= intent.getIntExtra("days", 0)
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
        val notificationBuilder =NotificationCompat.Builder(context, channelId).apply {
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
                    context, // Context from onReceive method.
                    0,
                    Intent(context, MainActivity::class.java), // Activity you want to launch onClick.
                    flags
                ))
        }
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(1001, notificationBuilder.build())
*/
    }
}