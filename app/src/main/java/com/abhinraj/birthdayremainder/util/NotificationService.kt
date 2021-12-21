package com.abhinraj.birthdayremainder.util

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.IBinder
import android.provider.Settings
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media.session.MediaButtonReceiver
import com.abhinraj.birthdayremainder.R
import com.abhinraj.birthdayremainder.activity.MainActivity

class NotificationService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
        return null
    }


    override fun onCreate() {
        NotificationHelper.createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_HIGH,
            true,
            getString(R.string.app_name),
            "App notification channel."
        )
    }

    @SuppressLint("WrongConstant", "UnspecifiedImmutableFlag")
    override fun onStartCommand(intent: Intent, flag: Int, startId: Int): Int {
        val order = intent.getStringExtra("order")
        if (order == "kill"){
            System.out.println("kill")
            stopForeground(true)
        }
        else {
            val channelId = "${this.packageName}-${this.getString(R.string.app_name)}"
            val bundle = intent.getBundleExtra("data")
            val name = bundle!!.getString("name")
            val age = bundle.getInt("age", 0)
            val gender = bundle.getString("gender")
            val noOfDays = bundle.getInt("days", 0)

            System.out.println("${name} ${age} ${gender} ${noOfDays}")
            val notifyIntent = Intent(
                this@NotificationService,
                MainActivity::class.java
            ).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("clear","clear")

            }
            var pronoun = ""
            if (gender == "Male") {
                pronoun = "his"
            } else if (gender == "Female") {
                pronoun = "her"
            } else if (gender == "Prefer not to say") {
                pronoun = "their"
            }
            val notificationBuilder = NotificationCompat.Builder(this, channelId).apply {
                setSmallIcon(R.drawable.ic_baseline_cake_24)
                setContentTitle("${name} is going to be ${age + 1} in next ${noOfDays} days.")
                setContentText("Make ${pronoun} birthday memorable.")
                setStyle(
                    NotificationCompat.BigTextStyle().bigText("Make ${pronoun} birthday memorable.")
                )

                priority = NotificationCompat.PRIORITY_HIGH
                setAutoCancel(true)
                color = NotificationCompat.COLOR_DEFAULT
                setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                setContentIntent(
                    PendingIntent.getActivity(
                        this@NotificationService, // Context from onReceive method.
                        0,notifyIntent, // Activity you want to launch onClick.
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                )
            }
            val notificationManager = NotificationManagerCompat.from(this)
            //notificationManager.notify(1001, notificationBuilder.build())
            startForeground(1001, notificationBuilder.build())
        }
        return START_STICKY
    }



}