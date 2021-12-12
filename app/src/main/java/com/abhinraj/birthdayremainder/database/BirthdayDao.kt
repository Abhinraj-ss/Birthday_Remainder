package com.abhinraj.birthdayremainder.database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/* Dao for accessing the data present inside the DB*/

@Dao
interface BirthdayDao{

    @Insert
    fun insertBirthday(restaurantEntity: BirthdayEntity)

    @Delete
    fun deleteBirthday(restaurantEntity: BirthdayEntity)

    @Query("SELECT * FROM birthdays")
    fun getAllBirthdays(): List<BirthdayEntity>

    @Query("SELECT * FROM birthdays WHERE id = :bdId")
    fun getBirthdayById(bdId: String): BirthdayEntity
}