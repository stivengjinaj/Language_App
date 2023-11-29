package com.stiven.languageapp.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stiven.languageapp.utils.Languages

@Entity(tableName = "Students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val course: Languages,
    val picture: Int,
    val points: Int
)