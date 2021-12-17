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

        val notificationBuilder =NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_acc_person_default) // 3
            setContentTitle("title") // 4
            setContentText("message") // 5
            setStyle(NotificationCompat.BigTextStyle().bigText("bigText"))
            // 6
            priority = NotificationCompat.PRIORITY_HIGH // 7
            setAutoCancel(true) // 8
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