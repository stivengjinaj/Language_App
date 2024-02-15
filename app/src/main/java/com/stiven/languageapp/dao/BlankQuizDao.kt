package com.stiven.languageapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.stiven.languageapp.model.BlankQuiz
import kotlinx.coroutines.flow.Flow

/**
 *  DAO interface for Blank Quiz table in database. Executes CRUD
 *  operations in Quiz Tables.
 * */
@Dao
interface BlankQuizDao {

    @Insert
    suspend fun insertQuizQuestion(blankQuiz: BlankQuiz)

    @Update
    suspend fun updateQuestion(blankQuiz: BlankQuiz)

    @Query("DELETE FROM blank_quiz WHERE question = :blankQuizQuestion")
    fun deleteBlankQuiz(blankQuizQuestion: String)

    @Query("SELECT * FROM blank_quiz")
    fun getAllBlankQuiz(): Flow<List<BlankQuiz>>
}