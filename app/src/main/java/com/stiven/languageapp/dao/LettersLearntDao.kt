package com.stiven.languageapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stiven.languageapp.model.LetterLearnt
import kotlinx.coroutines.flow.Flow

/**
 * DAO interface for letters learnt by the students.
 * */
@Dao
interface LettersLearntDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLetterLearnt(letterLearnt: LetterLearnt)

    @Query("DELETE FROM LetterLearnt")
    fun deleteAll()

    @Query("SELECT * FROM LetterLearnt")
    fun getAllLettersLearnt(): Flow<List<LetterLearnt>>
}