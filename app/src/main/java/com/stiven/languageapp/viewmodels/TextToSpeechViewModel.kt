package com.stiven.languageapp.viewmodels

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import java.util.Locale

/**
 * View-Model that handles text-to-speech functionality
 * */
class TextToSpeechViewModel : ViewModel(){

    private  var  textToSpeech: TextToSpeech? = null

    /**
     * Function to be called in order to use text-to-speech
     *
     * @param context application context
     * @param text text to be spoken
     * */
    fun textToSpeech(context: Context, text: String){
        textToSpeech = TextToSpeech(
            context
        ) {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech?.let { txtToSpeech ->
                    txtToSpeech.language = Locale.getDefault()
                    txtToSpeech.setSpeechRate(1.0f)
                    txtToSpeech.speak(
                        text,
                        TextToSpeech.QUEUE_ADD,
                        null,
                        null
                    )
                }
            }
        }
    }
}