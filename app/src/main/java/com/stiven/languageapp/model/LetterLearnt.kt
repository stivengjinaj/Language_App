package com.stiven.languageapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stiven.languageapp.utils.LearningType

/**
 * Entity that keeps track of learnt letters by students.
 * */
@Entity(tableName = "LetterLearnt")
data class LetterLearnt (
    @PrimaryKey(autoGenerate = true)
    val dataId: Int = 0,
    @ColumnInfo(name = "letterLearnt")
    val letterLearnt: String,
    @ColumnInfo(name = "studentId")
    val studentId: String,
    @ColumnInfo(name = "learningType")
    val learningType: LearningType
)