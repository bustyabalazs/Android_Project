package com.example.android_project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class Profile (
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val name:String,
    val picture: String,
    val address: String,
    val phone: String,
    val email:String
)