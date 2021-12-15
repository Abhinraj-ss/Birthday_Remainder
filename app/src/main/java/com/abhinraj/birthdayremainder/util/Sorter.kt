package com.abhinraj.birthdayremainder.util

import android.annotation.SuppressLint
import com.abhinraj.birthdayremainder.ui.home.Birthdays
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator

class Sorter {
    companion object {
      /*  var bdayComparator = Comparator<Birthdays> { bday1, bday2->
            @SuppressLint("SimpleDateFormat")
            val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            val diffOne:String
            val diffTwo:String
            val dobList = bday1.dob.split("/"," ",":").toList()
            val currentList = currentDate.split("/"," ",":").toList()
            val diffList= arrayListOf<Int>()
          /*  for (j in 1 downTo 0 step -1){
                when(j){
                    1->{if (currentList[j].toInt() -dobList[j].toInt()<0){
                        diffList.add(currentList[j].toInt() -dobList[j].toInt()+12)
                    }}
                    0->{if()
                        if (currentList[j].toInt() -dobList[j].toInt()<0){

                    }}
                }
                diffList.add(currentList[j].toInt() -dobList[j].toInt())
            }
            /*
            if (diffOne.compareTo(diffTwo) == 0) {
                val costOne = bday1.age
                val costTwo = bday2.age
                costOne.compareTo(costTwo)
            } else {
                nameOne.compareTo(nameTwo)
            }
        }*/
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
