package com.stiven.languageapp.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey
    var word: String,
    var language: String,
    var learnt: Int
)