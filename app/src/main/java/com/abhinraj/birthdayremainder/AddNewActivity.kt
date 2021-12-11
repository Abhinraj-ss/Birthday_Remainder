package com.abhinraj.birthdayremainder

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.abhinraj.birthdayremainder.ui.login.LoginActivity
import java.util.*
import kotlin.properties.Delegates


class AddNewActivity : AppCompatActivity() {
    /*val spGender: Spinner = findViewById(R.id.spGender)
    val spTime: Spinner = findViewById(R.id.spTime)*/
    lateinit var toolbar: Toolbar
    lateinit var etName:EditText
    lateinit var etDob : EditText
    lateinit var picker: DatePickerDialog
    lateinit var btnAdd:Button
    private var day by Delegates.notNull<Int>()
    var month by Delegates.notNull<Int>()
    var year by Delegates.notNull<Int>()


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Add your friend"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        etName=findViewById(R.id.etName)
        etDob=findViewById(R.id.etDob)
        etDob.inputType = InputType.TYPE_NULL
        btnAdd=findViewById(R.id.btnAdd)


        etDob.setOnClickListener(View.OnClickListener {

            val cldr: Calendar = Calendar.getInstance()
            day = cldr.get(Calendar.DAY_OF_MONTH)
            month = cldr.get(Calendar.MONTH)
            year = cldr.get(Calendar.YEAR)
            // date picker dialog
            picker = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth -> etDob.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year) },
                year,
                month,
                day
            )
            picker.show()

        })


/*
        val spGender = findViewById<View>(R.id.spGender) as Spinner
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this@AddNewActivity, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spGender.adapter = adapter
        spGender.setOnItemSelectedListener(this)


*/
        btnAdd.setOnClickListener {
            Toast.makeText(
                this@AddNewActivity,
                "\"Selected Date: \" ${etDob.getText()}",
                Toast.LENGTH_SHORT
            ).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

  /*  fun onItemSelected(arg0: AdapterView<*>?, arg1: View?, position: Int, id: Long) {
        Toast.makeText(
            applicationContext,
            "Selected User: " + users.get(position),
            Toast.LENGTH_SHORT
        ).show()
    }

    fun onNothingSelected(arg0: AdapterView<*>?) {
       Toast.makeText(
            this@AddNewActivity,
            "Field cannot be empty",
            Toast.LENGTH_SHORT
        ).show()
    }*/
    override fun onSupportNavigateUp(): Boolean {
      val intent = Intent(this, MainActivity::class.java)
      startActivity(intent)
      return true
    }
/* for exiting the app
    override fun onBackPressed() {
        super.onBackPressed()
    }*/
}


