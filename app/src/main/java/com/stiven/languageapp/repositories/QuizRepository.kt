package com.stiven.languageapp.repositories

import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.dao.QuizDao
import com.stiven.languageapp.model.Quiz
import kotlinx.coroutines.flow.Flow

/**
 * Class (implementation of the interface) that makes use of quiz DAO
 * to execute the operations in the database.
 *
 * @param quizDao student's DAO
 * */
class QuizRepository(private val quizDao: QuizDao) {
    /**
     * Function that gets all quiz questions present in database.
     *
     * @param database application database.
     * @return returns an async sequence of data in form of a list.
     * */
    fun getAllQuizQuestions(database: AppDatabase): Flow<List<Quiz>>{
        return quizDao.getAllQuizQuestions()
    }
    /**
     * Function used to insert a quiz question in database.
     *
     * @param quizQuestion question to insert.
     * */
    suspend fun insertQuizQuestion(quizQuestion: Quiz){
        quizDao.insertQuizQuestion(quizQuestion)
    }
    /**
     * Function that updates a quiz question.
     *
     * @param quizQuestion new updated question.
     * */
    suspend fun updateQuizQuestion(quizQuestion: Quiz){
        quizDao.updateQuizQuestion(quizQuestion)
    }
    /**
     * Function that deletes a quiz question.
     *
     * @param quizQuestion quiz question to be deleted.
     * */
    fun deleteQuizQuestion(quizQuestion: String){
        quizDao.deleteQuizQuestion(quizQuestion)
    }
}