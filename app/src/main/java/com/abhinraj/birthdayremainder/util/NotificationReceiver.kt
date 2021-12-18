package com.abhinraj.birthdayremainder.util

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.abhinraj.birthdayremainder.R
import com.abhinraj.birthdayremainder.activity.MainActivity

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        val channelId = "${context!!.packageName}-${context.getString(R.string.app_name)}"
        val name = p1?.getStringExtra("name")
        val age = p1?.getIntExtra("age",0)
        val gender = p1?.getStringExtra("gender")
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
            if (age != null) {
                setContentTitle("${name} is about to ${age+1}.")
            }
            setContentText("message")
            setStyle(NotificationCompat.BigTextStyle().bigText("Make ${pronoun} birthday memorable."))
            priority = NotificationCompat.PRIORITY_HIGH
            setAutoCancel(true)
            setContentIntent(
                PendingIntent.getActivity(
                    context, // Context from onReceive method.
                    0,
                    Intent(context, MainActivity::class.java), // Activity you want to launch onClick.
                    0
                ))
        }
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(1001, notificationBuilder.build())

    }
}