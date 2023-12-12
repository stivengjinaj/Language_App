package com.stiven.languageapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.stiven.languageapp.model.Word
import kotlinx.coroutines.flow.Flow

/**
 * DAO interface for Words table in database. Executes CRUD
 *  *  operations in Words Tables.
 * */
@Dao
interface WordDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: Word)

    @Update
    fun updateWord(word: Word)

    @Update
    fun updateAll(words: List<Word>)

    @Delete
    fun deleteWord(word: Word)

    @Query("SELECT * FROM Words")
    fun getAllWords(): Flow<List<Word>>
}