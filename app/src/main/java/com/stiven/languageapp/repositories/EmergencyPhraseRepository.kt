package com.stiven.languageapp.repositories

import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.dao.EmergencyPhraseDao
import com.stiven.languageapp.model.EmergencyPhrase
import kotlinx.coroutines.flow.Flow

/**
 * Class that uses emergencyPhraseDao to execute operations in the database.
 *
 * @param emergencyPhraseDao emergency phrases DAO.
 * */
class EmergencyPhraseRepository(private val emergencyPhraseDao: EmergencyPhraseDao) {
    /**
     * Function used to insert phrase.
     *
     * @param phrase phrase to insert.
     * */
    suspend fun insertEmergencyPhrase(phrase: EmergencyPhrase){
        emergencyPhraseDao.insertEmergencyPhrase(phrase)
    }
    /**
     * Function to update phrase.
     *
     * @param phrase phrase to update
     * */
    fun updateEmergencyPhrase(phrase: EmergencyPhrase){
        emergencyPhraseDao.updateEmergencyPhrase(phrase)
    }
    /**
     * Function to delete a phrase.
     *
     * @param phrase phrase to delete.
     * */
    fun deleteEmergencyPhrase(phrase: EmergencyPhrase){
        emergencyPhraseDao.deleteEmergencyPhrase(phrase)
    }
    /**
     * Function to get all phrases.
     *
     * @param database application database.
     * @return return a flow list of emergency phrases.
     * */
    fun getAllPhrases(database: AppDatabase): Flow<List<EmergencyPhrase>>{
        return database.emergencyPhraseDao().getAllPhrases()
    }
}