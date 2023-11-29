package com.stiven.languageapp.repositories

import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.dao.WordDao
import com.stiven.languageapp.entities.Word
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {

    suspend fun insertWord(word: Word) {
        wordDao.insertWord(word)
    }

    fun getWordsFromDatabase(database: AppDatabase): Flow<List<Word>> {
        return database.wordDao().getAllWords()
    }

    fun updateWord(word: Word){
        wordDao.updateWord(word)
    }

    fun updateAll(words: List<Word>){
        wordDao.updateAll(words)
    }

    fun deleteWord(word: Word){
        wordDao.deleteWord(word)
    }
}