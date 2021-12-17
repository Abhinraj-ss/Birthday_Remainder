package com.abhinraj.birthdayremainder.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationManagerCompat
import com.abhinraj.birthdayremainder.R
import com.abhinraj.birthdayremainder.databinding.ActivityDetailsBinding
import com.abhinraj.birthdayremainder.util.NotificationHelper
import com.abhinraj.birthdayremainder.util.NotificationReceiver




class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    lateinit var toolbar: Toolbar
    lateinit var txtName: TextView
    lateinit var txtDob : TextView
    lateinit var txtAge: TextView
    lateinit var txtGender: TextView
    lateinit var txtNotify: TextView
    lateinit var btnNotify: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Details"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val bundle = intent.getBundleExtra("data")
        txtName = findViewById(R.id.txtName)
        txtAge = findViewById(R.id.txtAge)
        txtDob = findViewById(R.id.txtDob)
        txtGender = findViewById(R.id.txtGender)
        txtNotify = findViewById(R.id.txtNotify)
        btnNotify = findViewById(R.id.btnNotify)
        txtName.text=bundle!!.getString("name", "") as String
        txtAge.text=bundle.getString("age", "") as String
        txtDob.text=(bundle.getString("dob", "") as String).subSequence(0,10).toString()
        txtGender.text=bundle.getString("gender", "") as String
        txtNotify.text=bundle.getString("notify", "") as String


        NotificationHelper.createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT,
            false,
            getString(R.string.app_name),
            "App notification channel."
        )


        btnNotify.setOnClickListener {
            Toast.makeText(this, "Alarm Triggered", Toast.LENGTH_LONG).show()
            sendAlarmNotification(this)

        }

    }
    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return true
    }
    fun sendAlarmNotification(context: Context){
        val intent = Intent(this, NotificationReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

        val currentime = System.currentTimeMillis()
        val ten =  1000*10

        alarmManager.set(AlarmManager.RTC_WAKEUP,currentime+ten,pendingIntent)


    }
}