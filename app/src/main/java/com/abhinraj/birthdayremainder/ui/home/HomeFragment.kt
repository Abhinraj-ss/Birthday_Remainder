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

class HomeFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
    private lateinit var rlLoading: RelativeLayout
    lateinit var recyclerHome: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    private val list = arrayListOf<Birthdays>()
    private lateinit var recyclerAdapter: HomeRecyclerAdapter
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
        val size = backgroundList.size

        for (i in backgroundList){

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