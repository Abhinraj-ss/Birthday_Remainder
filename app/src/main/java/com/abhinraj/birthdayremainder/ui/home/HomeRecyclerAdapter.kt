package com.abhinraj.birthdayremainder.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.abhinraj.birthdayremainder.R
import com.abhinraj.birthdayremainder.database.BirthdayDatabase
import com.abhinraj.birthdayremainder.database.BirthdayEntity
import com.abhinraj.birthdayremainder.ui.details.DetailsActivity
import java.util.ArrayList

class HomeRecyclerAdapter(val context: Context, val birthdays: ArrayList<Birthdays>):RecyclerView.Adapter<HomeRecyclerAdapter.UpBirthdaysViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpBirthdaysViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.home_one_row,parent,false)
        return UpBirthdaysViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UpBirthdaysViewHolder, position: Int) {
            val bdayObject = birthdays[position]
            holder.name.text = bdayObject.name
            holder.dob.text = bdayObject.dob.subSequence(0,10).toString()
            holder.age.text = bdayObject.age.toString()+"*"


        holder.cardBirthdays.setOnClickListener {
            /*val fragment = DetailsFragment()


            fragment.arguments = args
            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame, fragment)
            transaction.commit()
            (context as AppCompatActivity).supportActionBar?.title = holder.name.text.toString()*/
            val args = Bundle()
            args.putString("name", bdayObject.name)
            args.putString("age", bdayObject.age.toString()+" year(s)")
            args.putString("dob", bdayObject.dob)
            args.putString("gender", bdayObject.gender)
            args.putString("time", bdayObject.time.toString()+" "+bdayObject.unittime)
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("data",args)
            startActivity(context,intent,args)
        }
    }



    override fun getItemCount(): Int {
        return birthdays.size
    }
    class UpBirthdaysViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById(R.id.txtPersonName) as TextView
        val dob = view.findViewById(R.id.txtDob) as TextView
        val age = view.findViewById(R.id.txtAge) as TextView
        val cardBirthdays = view.findViewById(R.id.home_one_row) as CardView
        /*val personImage = view.findViewById(R.id.imgPerson) as ImageView*/
    }

    class DBAsyncTask(context: Context, private val birthdayEntity: BirthdayEntity, private val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {

        private val db = Room.databaseBuilder(context, BirthdayDatabase::class.java, "bday-db").build()

        override fun doInBackground(vararg params: Void?): Boolean {


            when (mode) {

                1 -> {
                    val bday: BirthdayEntity? =
                        db.birthdayDao().getBirthdayById(birthdayEntity.id.toString())
                    db.close()
                    return bday != null
                }

                2 -> {
                    db.birthdayDao().insertBirthday(birthdayEntity)
                    db.close()
                    return true
                }

                3 -> {
                    db.birthdayDao().deleteBirthday(birthdayEntity)
                    db.close()
                    return true
                }
                4 -> {
                    db.birthdayDao().updateBirthdaysById(birthdayEntity.age,birthdayEntity.id.toString())
                    db.close()
                    return true
                }
            }

            return false
        }

    }

}