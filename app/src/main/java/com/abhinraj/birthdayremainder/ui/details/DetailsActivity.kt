package com.abhinraj.birthdayremainder.ui.details

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.abhinraj.birthdayremainder.MainActivity
import com.abhinraj.birthdayremainder.R
import com.abhinraj.birthdayremainder.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    lateinit var toolbar: Toolbar
    lateinit var txtName: TextView
    lateinit var txtDob : TextView
    lateinit var txtAge: TextView
    lateinit var txtGender: TextView
    lateinit var txtNotify: TextView
    lateinit var txtUnit: String



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
        txtName.text=bundle.getString("name", "") as String
        txtAge.text=bundle.getString("age", "") as String
        txtDob.text=bundle.getString("dob", "") as String
        txtGender.text=bundle.getString("gender", "") as String
        txtUnit=bundle.getString("unittime", "") as String
        txtNotify.text=bundle.getString("time", "") as String +" "+txtUnit


    }
    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return true
    }
}