package com.abhinraj.birthdayremainder.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abhinraj.birthdayremainder.R
import java.util.ArrayList

class HomeRecyclerAdapter(val context: Context, val itemList: ArrayList<String>):RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {
    class HomeViewHolder(view:View):RecyclerView.ViewHolder(view){
        val textView:TextView= view.findViewById(R.id.text_home_row)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.home_one_row,parent,false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val text= itemList[position]
        holder.textView.text=text
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}