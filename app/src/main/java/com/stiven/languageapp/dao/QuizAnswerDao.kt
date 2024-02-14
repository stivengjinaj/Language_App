package com.stiven.languageapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.stiven.languageapp.model.QuizAnswer
import kotlinx.coroutines.flow.Flow

/**
 *  DAO interface for Quiz Answers table in database. Executes CRUD
 *  operations in Quiz Answers Tables.
 * */
@Dao
interface QuizAnswerDao {
    @Insert
    suspend fun insertQuizAnswer(quizAnswer: QuizAnswer)

    @Update
    suspend fun updateQuizAnswer(quizAnswer: QuizAnswer)

    @Query("SELECT * FROM quizAnswer")
    fun getAllQuizAnswers(): Flow<List<QuizAnswer>>
}