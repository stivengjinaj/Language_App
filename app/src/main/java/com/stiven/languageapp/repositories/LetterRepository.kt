package com.stiven.languageapp.repositories

import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.dao.LetterDao
import com.stiven.languageapp.model.Letter
import kotlinx.coroutines.flow.Flow

/**
 * Class (implementation of the interface) that makes use of letter DAO
 * to execute the operations in the database.
 *
 * @param letterDao letter's DAO
 * */
class LetterRepository(private val letterDao: LetterDao) {

    /**
     * Function that inserts a letter and its similarity in database.
     *
     * @param letter letter object to insert.
     * */
    suspend fun insertLetter(letter: Letter){
        letterDao.insertLetter(letter)
    }
    /**
     * Function that updates a letter and its similarity.
     *
     * @param letter letter to update
     * */
    suspend fun updateLetter(letter: Letter){
        letterDao.updateLetter(letter)
    }
    /**
     * Function that gets all letters present in database.
     *
     * @param database application database.
     * @return returns an async sequence of data in form of a list of object Letter.
     * */
    fun getLetters(database: AppDatabase): Flow<List<Letter>>{
        return database.letterDao().getAllLetters()
    }

    /**
     * Function that deletes a similarity.
     *
     * @param similarTo similarity to delete.
     * */
    fun deleteSimilarTo(similarTo: String){
        letterDao.deleteSimilarTo(similarTo)
    }

    /**
     * Function to delete all data.
     * */
    fun deleteAll(){
        letterDao.deleteAll()
    }
}