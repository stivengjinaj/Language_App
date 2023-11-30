package com.stiven.languageapp.viewmodels

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import java.util.Locale

class TextToSpeechViewModel : ViewModel(){

    private  var  textToSpeech: TextToSpeech? = null

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