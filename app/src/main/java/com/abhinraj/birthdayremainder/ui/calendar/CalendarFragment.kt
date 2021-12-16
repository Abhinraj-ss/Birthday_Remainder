package com.abhinraj.birthdayremainder.ui.calendar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders



class CalendarFragment : Fragment() {

    private lateinit var calendarViewModel: CalendarViewModel

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        calendarViewModel =
                ViewModelProviders.of(this).get(CalendarViewModel::class.java)

        val root = inflater.inflate(com.abhinraj.birthdayremainder.R.layout.fragment_calendar, container, false)



        val textView: TextView = root.findViewById(com.abhinraj.birthdayremainder.R.id.text_gallery)
        calendarViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = "To be implimented."
        })
        return root
    }

}