package com.stiven.languageapp.repositories

import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.dao.LettersLearntDao
import com.stiven.languageapp.model.LetterLearnt
import kotlinx.coroutines.flow.Flow

class LetterLearntRepository(private val lettersLearntDao: LettersLearntDao) {

    suspend fun insertLetterLearnt(letterLearnt: LetterLearnt){
        lettersLearntDao.insertLetterLearnt(letterLearnt)
    }

    fun getAllLettersLearnt(database: AppDatabase): Flow<List<LetterLearnt>> {
        return database.lettersLearntDao().getAllLettersLearnt()
    }

    fun deleteAll(){
        lettersLearntDao.deleteAll()
    }
}