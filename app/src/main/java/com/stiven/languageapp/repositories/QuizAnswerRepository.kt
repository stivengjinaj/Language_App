package com.stiven.languageapp.repositories

import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.dao.QuizAnswerDao
import com.stiven.languageapp.model.QuizAnswer
import kotlinx.coroutines.flow.Flow

class QuizAnswerRepository(private val quizAnswerDao: QuizAnswerDao) {
    /**
     * Function that gets all quiz answers present in database.
     *
     * @param database application database.
     * @return returns an async sequence of data in form of a list.
     * */
    fun getAllQuizAnswers(database: AppDatabase): Flow<List<QuizAnswer>> {
        return quizAnswerDao.getAllQuizAnswers()
    }
    /**
     * Function used to insert a quiz answer in database.
     *
     * @param quizAnswer quiz answer to insert.
     * */
    suspend fun insertQuizAnswer(quizAnswer: QuizAnswer){
        quizAnswerDao.insertQuizAnswer(quizAnswer)
    }
    /**
     * Function that updates a quiz answer.
     *
     * @param quizAnswer quiz answer to update.
     * */
    suspend fun updateQuizAnswer(quizAnswer: QuizAnswer){
        quizAnswerDao.updateQuizAnswer(quizAnswer)
    }

}