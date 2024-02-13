package com.stiven.languageapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Letter model containing similarities of each letter
 * that speech-to-text engine can misunderstand.
 *
 * @param similarTo similarity of the letter.
 * @param letter the single letter.
 * */
@Entity(tableName = "letters")
data class Letter (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "similarTo")
    val similarTo: String,
    @ColumnInfo(name = "letter")
    val letter: String
)
