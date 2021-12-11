package com.abhinraj.birthdayremainder.database
import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [BirthdayEntity::class], version = 1, exportSchema = false)
abstract class BirthdayDatabase : RoomDatabase() {

    abstract fun birthdayDao(): BirthdayDao

}

