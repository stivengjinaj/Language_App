package com.stiven.languageapp.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Word model for dictionary words.
 *
 * @param word word.
 * @param language word's language.
 * @param learnt 1 if student learnt it, 0 otherwise.
 * */
@Entity(tableName = "words")
data class Word(
    @PrimaryKey
    var word: String,
    var language: String,
    var learnt: Int
)