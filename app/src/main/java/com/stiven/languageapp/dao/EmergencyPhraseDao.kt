package com.stiven.languageapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.stiven.languageapp.model.EmergencyPhrase
import kotlinx.coroutines.flow.Flow

/**
 * DAO interface for Emergency table in database. Executes CRUD
 *  *  operations in Words Tables.
 * */
@Dao
interface EmergencyPhraseDao {
    @Insert
    suspend fun insertEmergencyPhrase(phrase: EmergencyPhrase)
    @Update
    fun updateEmergencyPhrase(phrase: EmergencyPhrase)
    @Delete
    fun deleteEmergencyPhrase(phrase: EmergencyPhrase)
    @Query("SELECT * FROM emergency")
    fun getAllPhrases(): Flow<List<EmergencyPhrase>>
}