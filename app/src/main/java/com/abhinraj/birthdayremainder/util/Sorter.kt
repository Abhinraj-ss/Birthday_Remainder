package com.abhinraj.birthdayremainder.util

import com.abhinraj.birthdayremainder.ui.home.Birthdays

class Sorter {
    companion object {
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
