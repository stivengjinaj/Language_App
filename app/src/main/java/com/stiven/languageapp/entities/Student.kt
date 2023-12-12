package com.stiven.languageapp.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stiven.languageapp.utils.Languages

/**
 * Student model containing the personal data of the student.
 *
 * @param id auto-generated primary key.
 * @param name student's name.
 * @param course the course the student is taking.
 * @param picture student's memoji.
 * @param points student's points.
 * */
@Entity(tableName = "Students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val course: Languages,
    val picture: Int,
    val points: Int
)