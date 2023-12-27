package com.stiven.languageapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stiven.languageapp.model.Letter
import kotlinx.coroutines.flow.Flow

/**
 * DAO interface for Letters table in database. Executes CRUD
 * operations in Student Tables.
 * */
@Dao
interface LetterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLetter(letter: Letter)

    @Query("DELETE FROM Letters WHERE similarTo = :similarTo")
    fun deleteSimilarTo(similarTo: String)

    @Query("SELECT * FROM Letters")
    fun getAllLetters(): Flow<List<Letter>>

    @Query("DELETE FROM letters")
    fun deleteAll()
}