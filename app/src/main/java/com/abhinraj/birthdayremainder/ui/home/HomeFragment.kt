package com.abhinraj.birthdayremainder.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhinraj.birthdayremainder.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var recyclerHome: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    val list= arrayListOf<String>("1","2","3","4","5","6","7","8","9","10")
    lateinit var recyclerAdapter: HomeRecyclerAdapter
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        recyclerHome= root.findViewById(R.id.recycler_home)
        layoutManager= LinearLayoutManager(activity)
        recyclerAdapter= HomeRecyclerAdapter(activity as Context,list)
        recyclerHome.adapter=recyclerAdapter
        recyclerHome.layoutManager=layoutManager
        return root
    }
}