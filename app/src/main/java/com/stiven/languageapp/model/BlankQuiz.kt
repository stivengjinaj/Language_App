package com.stiven.languageapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Object that contains information about a question
 * of the quiz in the fourth cloud.
 * */
@Entity(tableName = "blank_quiz")
data class BlankQuiz (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name = "first")
    val first: String,
    @ColumnInfo(name = "second")
    val second: String,
    @ColumnInfo(name = "third")
    val third: String,
    @ColumnInfo(name = "answer")
    val answer: String
)