package com.abhinraj.birthdayremainder.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.abhinraj.birthdayremainder.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        val channelId = "${context!!.packageName}-${context.getString(R.string.app_name)}"

        val notificationBuilder =NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_acc_person_default) // 3
            setContentTitle("title") // 4
            setContentText("message") // 5
            setStyle(NotificationCompat.BigTextStyle().bigText("bigText")) // 6
            priority = NotificationCompat.PRIORITY_DEFAULT // 7
            setAutoCancel(true) // 8
        }
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(1001, notificationBuilder.build())



}

}