package com.abhinraj.birthdayremainder.activity

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.PendingIntent.*
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import androidx.core.app.NotificationManagerCompat
import com.abhinraj.birthdayremainder.R
import com.abhinraj.birthdayremainder.util.NotificationHelper
import com.abhinraj.birthdayremainder.util.NotificationReceiver
import android.widget.TextView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern


class AddNewActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var etName: EditText
    lateinit var etDob : EditText
    lateinit var picker: DatePickerDialog
    lateinit var picker1: TimePickerDialog
    lateinit var gender: Spinner
    lateinit var etNotify: EditText
    lateinit var etNotifyTime: EditText

    lateinit var btnAdd:Button
    var age by Delegates.notNull<Int>()
    private var day by Delegates.notNull<Int>()
    var month by Delegates.notNull<Int>()
    var year by Delegates.notNull<Int>()
    var hour by Delegates.notNull<Int>()
    var minute by Delegates.notNull<Int>()

    @SuppressLint("SimpleDateFormat")
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDate = sdf.format(Date())
    var isAllFieldsChecked : Boolean = false




    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new)


        val backgroundList = HomeFragment.BirthdaysAsync(this).execute().get()

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Add New Profile"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        etName=findViewById(R.id.etName)
        etDob=findViewById(R.id.etDob)
        etDob.inputType = InputType.TYPE_NULL
        gender=findViewById(R.id.spGender)
        etNotify =findViewById(R.id.etNotify)
        etNotifyTime = findViewById(R.id.etNotifyTime)
        btnAdd=findViewById(R.id.btnAdd)


        NotificationHelper.createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_HIGH,
            true,
            getString(R.string.app_name),
            "App notification channel."
        )



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
                { view, year, monthOfYear, dayOfMonth -> etDob.setText(String.format("%02d/%02d/%04d",dayOfMonth , (monthOfYear + 1) , year)) },
                year,
                month,
                day
            )
            val today = Calendar.getInstance()
            val now = today.timeInMillis
            picker.datePicker.setMaxDate(now)
            picker.show()

        })



        etNotify.setOnClickListener(View.OnClickListener {

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
                { view, year, monthOfYear, dayOfMonth -> etNotify.setText(String.format("%02d/%02d/%04d",dayOfMonth , (monthOfYear + 1) , year)) },
                year,
                month,
                day
            )

            val today = Calendar.getInstance()
            val now = today.timeInMillis
            picker.datePicker.setMinDate(now)
            picker.datePicker.setMaxDate(now+31540000000)

            picker.show()



        })

        etNotifyTime.setOnClickListener {
            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }

            val cldr: Calendar = Calendar.getInstance()
            hour = cldr.get(Calendar.HOUR_OF_DAY)
            minute = cldr.get(Calendar.MINUTE)
            //time picker dialog
            picker1 = TimePickerDialog(
                this,
                {view,hourOfDay,minute->etNotifyTime.setText(String.format("%02d:%02d",hourOfDay,minute))},hour,minute,true
            )
            picker1.show()

        }

        btnAdd.setOnClickListener {
            isAllFieldsChecked = checkAllFields()
            if (isAllFieldsChecked){

                val dob = etDob.getText().toString()+" 00:00:00"
                val notify = etNotify.getText().toString()+" "+ etNotifyTime.getText().toString()
                val date1 = Calendar.getInstance()
                val date2 = Calendar.getInstance()
                val dobList = dob.split("/"," ",":").toList()
                val currentList = currentDate.split("/"," ",":").toList()
                val notifyList = notify.split("/"," ",":").toList()


                date1.clear()
                date1.set(notifyList[2].toInt(),notifyList[1].toInt(),notifyList[0].toInt())
                date2.clear()
                date2.set(currentList[2].toInt(),dobList[1].toInt(),dobList[0].toInt())
                if(date1>date2){
                    date2.clear()
                    date2.set(currentList[2].toInt()+1,dobList[1].toInt(),dobList[0].toInt())
                }
                val diff = date2.timeInMillis - date1.timeInMillis
                System.out.println(diff)




                val diffList= arrayListOf<Int>()
                for (i in 0..5){
                    diffList.add(currentList[i].toInt() -dobList[i].toInt())
                }
                age = diffList[2]

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
                    notify
                )
                System.out.println(birthdayEntity)

                if (!HomeRecyclerAdapter.DBAsyncTask(applicationContext, birthdayEntity, 1).execute().get()) {
                    val async =
                        HomeRecyclerAdapter.DBAsyncTask(applicationContext, birthdayEntity, 2).execute()
                    val result = async.get()
                }
                Toast.makeText(this,
                    "Alarm Triggered",
                    Toast.LENGTH_LONG).show()

                if(sendAlarmNotification(this,etName.getText().toString(),age,gender.getSelectedItem().toString(),diff.toLong(),backgroundList.size)){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }
    }



    override fun onSupportNavigateUp(): Boolean {
      val intent = Intent(this, MainActivity::class.java)
      startActivity(intent)
      return true
    }


    private fun checkAllFields():Boolean {
        if (etName.length() == 0) {
            etName.setError("Specify the Name")
            return false;
        }

        if (etDob.length() == 0) {
            etDob.setError("Specify the DOB")
            return false;
        }
        val errorText = gender.getSelectedView() as TextView

        if (gender.selectedItem.toString()== "Select") {

            errorText.error = ""
            return false;
        }

        if (etNotify.length() == 0) {
            etNotify.setError(" Specify the Date")
            return false;
        }
        if (etNotifyTime.length() == 0) {
            etNotifyTime.setError("Specify the Time")
            return false;
        }
        // after all validation return true.
        return true;
    }

    private fun sendAlarmNotification(context: Context, name:String, age :Int, gender:String,noOfmillis:Long):Boolean{
        val noOfDays =noOfmillis.toFloat() / (86400000)
        val args = Bundle()
        args.putString("name",name)
        args.putInt("age",age)
        args.putString("gender",gender)
        args.putInt("days",noOfDays.toInt())
        val intent = Intent(this.applicationContext, NotificationReceiver::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("data",args)
            /*putExtra("name",name)
            putExtra("age",age)
            putExtra("gender",gender)
            putExtra("days",noOfDays.toInt())*/
        }
        System.out.println("${name} ${age} ${gender} ${noOfDays}")

        val pendingIntent = getBroadcast(context.applicationContext, 0, intent, 0)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val currentime = System.currentTimeMillis()
        val ten =  1000*20

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,currentime+ten,pendingIntent)

        return true
    }
}


