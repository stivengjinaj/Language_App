package com.stiven.languageapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.stiven.languageapp.entities.Word
import kotlinx.coroutines.flow.Flow

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