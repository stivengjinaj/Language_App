package com.stiven.languageapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stiven.languageapp.utils.QuestionType

@Entity(tableName = "quizAnswer")
data class QuizAnswer(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "studentId")
    val studentId: String,
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name = "questionType")
    val questionType: QuestionType
)
