package com.abhinraj.birthdayremainder

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
import kotlinx.android.synthetic.main.activity_add_new.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates
import android.view.MotionEvent

import android.view.View.OnTouchListener





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

        System.out.println(" C DATE is  "+currentDate)
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
            picker.show()

        })


        unittime.setOnTouchListener(OnTouchListener { v, event ->
            val imm =
                applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
            false
        })
        btnAdd.setOnClickListener {
            Toast.makeText(
                this@AddNewActivity,
                "\"Selected Date: \" ${etDob.getText()}",
                Toast.LENGTH_SHORT
            ).show()

            var dob = etDob.getText().toString()+" 00:00:00"
            if (dob[1].toString() == "/")
                dob = "0"+dob
            System.out.println("Difference is "+ dob)

            val dobList = dob.split("/"," ",":").toList()
            val currentList = currentDate.split("/"," ",":").toList()
            val diffList= arrayListOf<Int>()
            for (i in 0..5){
                diffList.add(currentList[i].toInt() -dobList[i].toInt())
            }



            var diffYears = currentDate.substring(6,9).toInt()-dob.substring(6,9).toInt()
            var diffMonths = currentDate.substring(3,4).toInt()-dob.substring(3,4).toInt()
            var diffDays = currentDate.substring(0,1).toInt()-dob.substring(0,1).toInt()
            var diffHours = currentDate.substring(11,12).toInt()-dob.substring(11,12).toInt()
            var diffMinutes = currentDate.substring(14,15).toInt()-dob.substring(14,15).toInt()
            var diffSecs = currentDate.substring(17,18).toInt()-dob.substring(17,18).toInt()


            diffDays = diffList[0]
            diffMonths = diffList[1]
            diffYears =diffList[2]
            diffHours = diffList[3]
            diffMinutes = diffList[4]
            diffSecs = diffList[5]
            age = diffYears


            System.out.println("${diffYears}"+" "+ "${diffMonths}"+" "+ "${diffDays}"+" "+ "${diffHours}"+" "+ "${diffMinutes}"+" "+"${diffSecs}")


            if (diffMonths==0){
                if (diffDays==0){
                    if (diffHours==0){
                        if (diffMinutes==0){
                            if (diffSecs==0&& month==12 && day==31){
                                age+=1
                            }
                            else if(diffSecs<0){
                                age-=1
                            }
                        }
                        else if(diffMinutes<0){
                            age-=1
                        }
                    }
                    else if(diffHours<0){
                        age-=1
                    }
                }
                else if(diffDays<0){
                    age-=1
                }
            }
            else if(diffMonths<0){
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


