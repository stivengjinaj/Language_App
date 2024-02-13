package com.stiven.languageapp.model

/**
 * Data class made to simplify the information
 * a single question contains.
 * */
data class SingleQuestion (
    val question: String,
    val options: List<String>,
    val answer: Int
)