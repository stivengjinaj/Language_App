package com.stiven.languageapp.repositories

import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.dao.BlankQuizDao
import com.stiven.languageapp.model.BlankQuiz
import kotlinx.coroutines.flow.Flow

class BlankQuestionRepository(private val blankQuizDao: BlankQuizDao) {
    /**
     * Function that gets all blank quiz questions present in database.
     *
     * @param database application database.
     * @return returns an async sequence of data in form of a list.
     * */
    fun getAllBlankQuiz(database: AppDatabase): Flow<List<BlankQuiz>> {
        return blankQuizDao.getAllBlankQuiz()
    }
    /**
     * Function used to insert a blank quiz question in database.
     *
     * @param blankQuiz blank quiz question to insert.
     * */
    suspend fun insertBlankQuiz(blankQuiz: BlankQuiz){
        blankQuizDao.insertQuizQuestion(blankQuiz)
    }
    /**
     * Function that updates a blank quiz question.
     *
     * @param blankQuiz blank quiz question to update.
     * */
    suspend fun updateBlankQuiz(blankQuiz: BlankQuiz){
        blankQuizDao.updateQuestion(blankQuiz)
    }
    /**
     * Function that deletes a blank quiz question.
     *
     * @param blankQuizQuestion blank quiz question to be deleted.
     * */
    fun deleteBlankQuiz(blankQuizQuestion: String){
        blankQuizDao.deleteBlankQuiz(blankQuizQuestion)
    }
}