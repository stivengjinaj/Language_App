package com.stiven.languageapp.utils

import kotlinx.coroutines.flow.StateFlow

/**
 * Speech-to-text engine interface.
 * */
interface SpeechToText {
    val text: StateFlow<String>
    fun start()
    fun stop()
}