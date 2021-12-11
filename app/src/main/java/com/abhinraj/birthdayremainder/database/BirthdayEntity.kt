package com.abhinraj.birthdayremainder.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "birthdays")
data class BirthdayEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "dob") val dob: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "time") val time: Int,
    @ColumnInfo(name = "unittime") val unittime: String
)