package com.abhinraj.birthdayremainder.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.abhinraj.birthdayremainder.R
import java.util.ArrayList

class HomeRecyclerAdapter(val context: Context, val itemList: ArrayList<Birthdays>):RecyclerView.Adapter<HomeRecyclerAdapter.UpBirthdaysViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpBirthdaysViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.home_one_row,parent,false)
        return UpBirthdaysViewHolder(view)
    }

    override fun onBindViewHolder(holder: UpBirthdaysViewHolder, position: Int) {
        val text= itemList[position]
        /*holder.textView.text= text.toString()*/
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    class UpBirthdaysViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById(R.id.txtPersonName) as TextView
        val dob = view.findViewById(R.id.txtDob) as TextView
        val gender = view.findViewById(R.id.txtGender) as TextView
        val age = view.findViewById(R.id.txtAge) as TextView
        val ageText = view.findViewById(R.id.ageTxt) as TextView
        val cardRestaurant = view.findViewById(R.id.home_one_row) as CardView
        val favImage = view.findViewById(R.id.imgPerson) as ImageView
    }
}