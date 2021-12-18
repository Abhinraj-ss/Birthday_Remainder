package com.abhinraj.birthdayremainder.util

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.media.audiofx.Equalizer
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.abhinraj.birthdayremainder.R
import com.abhinraj.birthdayremainder.activity.MainActivity
import android.os.SystemClock
import android.os.SystemClock.elapsedRealtime
import android.provider.Settings


object NotificationHelper {


     @SuppressLint("WrongConstant")
     fun createNotificationChannel(context: Context, importance: Int, showBadge: Boolean, name: String, description: String) {
        // 1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // 2
            val channelId = "${context.packageName}-$name"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

            // 3
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }/*
    fun createSampleDataNotification(context: Context){
        // 1
        val channelId = "${context.packageName}-${context.getString(R.string.app_name)}"
        // 2
        val notificationBuilder = NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_acc_person_default) // 3
            setContentTitle("title") // 4
            setContentText("message") // 5
            setStyle(NotificationCompat.BigTextStyle().bigText("bigText")) // 6
            priority = NotificationCompat.PRIORITY_DEFAULT // 7
            setAutoCancel(true) // 8
            // 1
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
// 2
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
// 3
            setContentIntent(pendingIntent)

        }
// 1
        val notificationManager = NotificationManagerCompat.from(context)
// 2
        notificationManager.notify(1001, notificationBuilder.build())

    }
*/

}