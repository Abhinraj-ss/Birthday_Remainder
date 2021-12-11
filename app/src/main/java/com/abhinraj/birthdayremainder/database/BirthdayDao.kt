package com.abhinraj.birthdayremainder.database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/* Dao for accessing the data present inside the DB*/

@Dao
interface BirthdayDao{

    @Insert
    fun insertRestaurant(restaurantEntity: BirthdayEntity)

    @Delete
    fun deleteRestaurant(restaurantEntity: BirthdayEntity)

    @Query("SELECT * FROM birthdays")
    fun getAllRestaurants(): List<BirthdayEntity>

    @Query("SELECT * FROM birthdays WHERE id = :bdId")
    fun getRestaurantById(bdId: String): BirthdayEntity
}