package com.abhinraj.birthdayremainder.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.abhinraj.birthdayremainder.database.BirthdayEntity
import com.abhinraj.birthdayremainder.ui.home.HomeFragment
import com.abhinraj.birthdayremainder.ui.home.HomeRecyclerAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

import android.view.View.OnTouchListener
import com.abhinraj.birthdayremainder.R


class AddNewActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var etName: EditText
    lateinit var etDob : EditText
    lateinit var picker: DatePickerDialog
    lateinit var gender: Spinner
    lateinit var time: EditText
    lateinit var unittime: Spinner
    lateinit var btnAdd:Button
    var age by Delegates.notNull<Int>()
    private var day by Delegates.notNull<Int>()
    var month by Delegates.notNull<Int>()
    var year by Delegates.notNull<Int>()
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
    val currentDate = sdf.format(Date())





    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new)


        val backgroundList = HomeFragment.BirthdaysAsync(this).execute().get()

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Add your friend"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        etName=findViewById(R.id.etName)
        etDob=findViewById(R.id.etDob)
        etDob.inputType = InputType.TYPE_NULL
        gender=findViewById(R.id.spGender)
        time = findViewById(R.id.etNotify)
        unittime =findViewById(R.id.spTime)
        btnAdd=findViewById(R.id.btnAdd)

        etDob.setOnClickListener(View.OnClickListener {

            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
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
            val today = Calendar.getInstance()
            val now = today.timeInMillis
            picker.datePicker.setMaxDate(now)
            picker.show()

        })


        unittime.setOnTouchListener(OnTouchListener { v, event ->
            val imm =
                applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
            false
        })
        btnAdd.setOnClickListener {

            var dob = etDob.getText().toString()+" 00:00:00"
            if (dob[1].toString() == "/")
                dob = "0"+dob

            val dobList = dob.split("/"," ",":").toList()
            val currentList = currentDate.split("/"," ",":").toList()
            val diffList= arrayListOf<Int>()
            for (i in 0..5){
                diffList.add(currentList[i].toInt() -dobList[i].toInt())
            }

            age = diffList[2]


            System.out.println("${diffList[2]}"+" "+ "${diffList[1]}"+" "+ "${diffList[0]}"+" "+ "${diffList[3]}"+" "+ "${diffList[4]}"+" "+"${diffList[5]}")


            if (diffList[1]==0){
                if (diffList[0]==0){
                    if (diffList[3]==0){
                        if (diffList[4]==0){
                            if (diffList[5]==0&& month==12 && day==31){
                                age+=1
                            }
                            else if(diffList[5]<0){
                                age-=1
                            }
                        }
                        else if(diffList[4]<0){
                            age-=1
                        }
                    }
                    else if(diffList[3]<0){
                        age-=1
                    }
                }
                else if(diffList[0]<0){
                    age-=1
                }
            }
            else if(diffList[1]<0){
                age-=1
            }


            val birthdayEntity = BirthdayEntity(
                backgroundList.size,
                etName.getText().toString(),
                dob,
                age,
                gender.getSelectedItem().toString(),
                time.text.toString().toInt(),
                unittime.getSelectedItem().toString()
            )
            System.out.println(birthdayEntity)

            if (!HomeRecyclerAdapter.DBAsyncTask(applicationContext, birthdayEntity, 1).execute().get()) {
                val async =
                    HomeRecyclerAdapter.DBAsyncTask(applicationContext, birthdayEntity, 2).execute()
                val result = async.get()
                if (result) {
                    Toast.makeText(
                        applicationContext,
                        "Added Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }



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
    /*class BirthdaysAsync(context: Context) : AsyncTask<Void, Void, <BirthdayEntity>>() {

        val db = Room.databaseBuilder(context, BirthdayDatabase::class.java, "bday-db").build()

        override fun doInBackground(vararg params: Void?): <BirthdayEntity> {

            return db.birthdayDao().getAllBirthdays()
        }

    }*/

}


