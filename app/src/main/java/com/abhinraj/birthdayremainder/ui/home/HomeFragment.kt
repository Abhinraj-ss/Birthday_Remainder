package com.abhinraj.birthdayremainder.ui.home

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.abhinraj.birthdayremainder.R
import com.abhinraj.birthdayremainder.database.BirthdayDatabase
import com.abhinraj.birthdayremainder.database.BirthdayEntity
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
    private lateinit var rlLoading: RelativeLayout
    lateinit var recyclerHome: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    private val list = arrayListOf<Birthdays>()
    private lateinit var recyclerAdapter: HomeRecyclerAdapter
    val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)


        val ex = Birthdays(1, "Abhin Raj", "06/11/2000", 21,"Male", 20, "minutes")
        val ex1 = Birthdays(2, "Surabi Suresh", "06/11/2001",20, "Female", 20, "minutes")
        list.add(ex)
        list.add(ex1)


        val backgroundList = BirthdaysAsync(activity as Context).execute().get()

        for (i in backgroundList){

            val dobList = i.dob.split("/"," ",":").toList()
            val currentList = currentDate.split("/"," ",":").toList()
            val diffList= arrayListOf<Int>()
            for (i in 0..5){
                diffList.add(currentList[i].toInt() -dobList[i].toInt())
            }
            var diffYears = currentDate.substring(6,9).toInt()-i.dob.substring(6,9).toInt()
            var diffMonths = currentDate.substring(3,4).toInt()-i.dob.substring(3,4).toInt()
            var diffDays = currentDate.substring(0,1).toInt()-i.dob.substring(0,1).toInt()
            var diffHours = currentDate.substring(11,12).toInt()-i.dob.substring(11,12).toInt()
            var diffMinutes = currentDate.substring(14,15).toInt()-i.dob.substring(14,15).toInt()
            var diffSecs = currentDate.substring(17,18).toInt()-i.dob.substring(17,18).toInt()


            diffDays = diffList[0]
            diffMonths = diffList[1]
            diffYears =diffList[2]
            diffHours = diffList[3]
            diffMinutes = diffList[4]
            diffSecs = diffList[5]
            var age = diffYears


            System.out.println("${diffYears}"+" "+ "${diffMonths}"+" "+ "${diffDays}"+" "+ "${diffHours}"+" "+ "${diffMinutes}"+" "+"${diffSecs}")


            if (diffMonths==0){
                if (diffDays==0){
                    if (diffHours==0){
                        if (diffMinutes==0){
                            if (diffSecs==0&& dobList[1].toInt()==12 && dobList[0].toInt()==31){
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
            i.age = age
            val async =
                HomeRecyclerAdapter.DBAsyncTask(activity as Context, i, 4).execute()
            val result = async.get()
        }

        progressBar = root?.findViewById(R.id.progressBar) as ProgressBar
        rlLoading = root.findViewById(R.id.rlLoading) as RelativeLayout
        /*rlLoading.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE*/
        if (backgroundList.isEmpty()) {
            rlLoading.visibility = View.GONE
            rlLoading.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            rlLoading.visibility = View.INVISIBLE
            rlLoading.visibility = View.GONE
            progressBar.visibility = View.GONE
            System.out.println(backgroundList)
            for (i in backgroundList) {
                list.add(
                    Birthdays(
                        i.id,
                        i.name,
                        i.dob.subSequence(0,10).toString(),
                        i.age,
                        i.gender,
                        i.time,
                        i.unittime
                    )
                )
            }
        }

            recyclerHome = root.findViewById(R.id.recycler_home)
            layoutManager = LinearLayoutManager(activity)
            recyclerAdapter = HomeRecyclerAdapter(activity as Context, list)
            recyclerHome.adapter = recyclerAdapter
            recyclerHome.layoutManager = layoutManager
            return root
        }

        class BirthdaysAsync(context: Context) : AsyncTask<Void, Void, List<BirthdayEntity>>() {

            val db = Room.databaseBuilder(context, BirthdayDatabase::class.java, "bday-db").build()

            override fun doInBackground(vararg params: Void?): List<BirthdayEntity> {

                return db.birthdayDao().getAllBirthdays()
            }

        }

}