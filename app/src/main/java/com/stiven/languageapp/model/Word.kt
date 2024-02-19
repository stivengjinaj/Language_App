package com.stiven.languageapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Word model for dictionary words.
 *
 * @param italian italian word.
 * @param english english word.
 * @param french frenchWord.
 * */
@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "english")
    val english: String,
    @ColumnInfo(name = "italian")
    val italian: String,
    @ColumnInfo(name = "french")
    val french: String
)