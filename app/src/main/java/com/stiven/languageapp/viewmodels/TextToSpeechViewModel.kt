package com.stiven.languageapp.viewmodels

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import java.util.Locale

/**
 * View-Model that handles text-to-speech functionality
 * */
class TextToSpeechViewModel : ViewModel(){

    private var  textToSpeech: TextToSpeech? = null
    private var italianTextToSpeech: TextToSpeech? = null

    /**
     * Function to be called in order to use text-to-speech
     *
     * @param context application context
     * @param text text to be spoken
     * */
    fun textToSpeech(context: Context, text: String){
        textToSpeech = TextToSpeech(context) {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech?.let { textToSpeech ->
                    textToSpeech.setLanguage(Locale.getDefault())
                    textToSpeech.setSpeechRate(1.0f)
                    textToSpeech.speak(text, TextToSpeech.QUEUE_ADD,null, null)
                }
            }
        }
    }
    /**
     * Function to be called in order to use italian only text-to-speech
     *
     * @param context application context
     * @param text text to be spoken
     * */
    fun italianTextToSpeech(context: Context, text: String){
        italianTextToSpeech = TextToSpeech(context) {
            if (it == TextToSpeech.SUCCESS){
                textToSpeech?.let { textToSpeech ->
                    textToSpeech.setLanguage(Locale.ITALIAN)
                    textToSpeech.setSpeechRate(1.0f)
                    textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, null)
                }
            }
        }
    }

    /**
     * Function to be called in order to use custom language text-to-speech
     *
     * @param context application context
     * @param text text to be spoken.
     * @param language to use.
     * */
    fun customTextToSpeech(
        context: Context,
        text: String,
        language: Locale
    ){
        italianTextToSpeech = TextToSpeech(context) {
            if (it == TextToSpeech.SUCCESS){
                textToSpeech?.let { textToSpeech ->
                    textToSpeech.setLanguage(language)
                    textToSpeech.setSpeechRate(1.0f)
                    textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, null)
                }
            }
        }
    }
}