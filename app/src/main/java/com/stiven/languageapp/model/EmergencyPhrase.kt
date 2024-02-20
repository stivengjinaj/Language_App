package com.stiven.languageapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stiven.languageapp.utils.Languages

@Entity(tableName = "emergency")
data class EmergencyPhrase(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "phrase")
    val phrase: String,
    @ColumnInfo(name = "language")
    val language: Languages,
    @ColumnInfo(name = "context")
    val context: String
)
