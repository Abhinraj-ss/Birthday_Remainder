package com.abhinraj.birthdayremainder.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.abhinraj.birthdayremainder.ui.home.Birthdays
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.Comparator

class Sorter {
    companion object {
       @RequiresApi(Build.VERSION_CODES.O)
       var bdayComparator = Comparator<Birthdays> { bday1, bday2->
           @SuppressLint("SimpleDateFormat")
           val sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")
           val currentYear= LocalDate.now().year
           val currentMonth= LocalDate.now().month
           val currentDate= LocalDate.now().dayOfMonth
           val dob1 :String
           val dob2 :String
           if (bday1.dob.substring(3,5).toInt() < currentMonth.value || bday1.dob.substring(0,2).toInt() < currentDate){
               dob1 = bday1.dob.substring(0,6)+currentYear.plus(1)+bday1.dob.substring(10)
           }
           else{
               dob1 = bday1.dob.substring(0,6)+currentYear+bday1.dob.substring(10)

           }

           if (bday2.dob.substring(3,5).toInt() < currentMonth.value || bday2.dob.substring(0,2).toInt() < currentDate){
               dob2 = bday2.dob.substring(0,6)+currentYear.plus(1)+bday2.dob.substring(10)
           }
           else{
               dob2 = bday2.dob.substring(0,6)+currentYear+bday2.dob.substring(10)

           }

           val diffOne: LocalDate? =LocalDate.parse(dob1,sdf)
            val diffTwo: LocalDate? = LocalDate.parse(dob2,sdf)

            if (diffOne!!.compareTo(diffTwo) == 0) {
                val nameOne = bday1.name
                val nameTwo = bday2.name
                nameOne.compareTo(nameTwo)
            } else {
                diffOne!!.compareTo(diffTwo)
            }
        }



        var ageComparator = Comparator<Birthdays> { bday1, bday2 ->
            val ageOne = bday1.age
            val ageTwo = bday2.age
            if (ageOne.compareTo(ageTwo) == 0) {
                nameComparator.compare(bday1, bday2)
            } else {
                ageOne.compareTo(ageTwo)
            }
        }

        var nameComparator = Comparator<Birthdays> { bday1, bday2->
            val nameOne = bday1.name
            val nameTwo = bday2.name
            if (nameOne.compareTo(nameTwo) == 0) {
                val costOne = bday1.age
                val costTwo = bday2.age
                costOne.compareTo(costTwo)
            } else {
                nameOne.compareTo(nameTwo)
            }
        }
    }
}
