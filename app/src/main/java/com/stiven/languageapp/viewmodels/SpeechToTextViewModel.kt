package com.stiven.languageapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stiven.languageapp.model.SpeechToText
import kotlinx.coroutines.launch

/**
 * Class the handles speech-to-text engine operations and
 * captures the spoken text.
 * */
class SpeechToTextViewModel(private val stt: SpeechToText) : ViewModel() {
    var state = mutableStateOf("")


    init {
        viewModelScope.launch {
            with(stt) {
                text.collect { result ->
                    send(SpeechActions.Update(result))
                }
            }
        }
    }

    /**
     * Function to send a command to the engine.
     *
     * @param action the action to perform
     * */
    fun send(action: SpeechActions) {
        when (action) {
            SpeechActions.StartRecord -> {
                stt.start()
            }

            SpeechActions.EndRecord -> {
                stt.stop()
                state.value = stt.text.value
            }
            is SpeechActions.Update -> {
                state.value = action.text
            }
        }
    }
}

/**
 * Sealed class that contains the operations the engine is capable of.
 * StartRecord starts recording.
 * EndRecord stops the recording.
 * Update updates the text being recorded.
 * */
sealed class SpeechActions {
    data object StartRecord : SpeechActions()
    data object EndRecord : SpeechActions()
    data class Update(val text: String): SpeechActions()
}