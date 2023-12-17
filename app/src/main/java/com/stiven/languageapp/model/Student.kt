package com.stiven.languageapp.model

import androidx.room.ColumnInfo
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
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "course")
    val course: Languages,
    @ColumnInfo(name = "picture")
    val picture: Int,
    @ColumnInfo(name = "points")
    val points: Int
)