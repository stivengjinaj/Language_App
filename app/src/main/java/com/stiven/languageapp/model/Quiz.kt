package com.stiven.languageapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Object that contains information about a question
 * of the quiz in the third cloud.
 * */
@Entity(tableName = "quiz")
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name = "firstEn")
    val firstEn: String,
    @ColumnInfo(name = "firstFr")
    val firstFr: String,
    @ColumnInfo(name = "secondEn")
    val secondEn: String,
    @ColumnInfo(name = "secondFr")
    val secondFr: String,
    @ColumnInfo(name = "thirdEn")
    val thirdEn: String,
    @ColumnInfo(name = "thirdFr")
    val thirdFr: String,
    @ColumnInfo(name = "answer")
    val answer: String
)
