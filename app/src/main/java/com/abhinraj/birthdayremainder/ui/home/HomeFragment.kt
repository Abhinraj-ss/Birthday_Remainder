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
            var age = diffList[2]


            System.out.println("${diffList[2]}"+" "+ "${diffList[1]}"+" "+ "${diffList[0]}"+" "+ "${diffList[3]}"+" "+ "${diffList[4]}"+" "+"${diffList[5]}")


            if (diffList[1]==0){
                if (diffList[0]==0){
                    if (diffList[3]==0){
                        if (diffList[4]==0){
                            if (diffList[5]==0&& dobList[1].toInt()==12 && dobList[0].toInt()==31){
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