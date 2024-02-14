package com.stiven.languageapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.stiven.languageapp.model.Quiz
import kotlinx.coroutines.flow.Flow

/**
 *  DAO interface for Quiz table in database. Executes CRUD
 *  operations in Quiz Tables.
 * */
@Dao
interface QuizDao {
    @Insert
    suspend fun insertQuizQuestion(quizQuestion: Quiz)
    @Update
    suspend fun updateQuestion(quizQuestion: Quiz)
    @Query("DELETE FROM quiz WHERE question = :quizQuestion")
    fun deleteQuizQuestion(quizQuestion: String)
    @Query("SELECT * FROM quiz")
    fun getAllQuizQuestions(): Flow<List<Quiz>>
}