package com.stiven.languageapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.model.EmergencyPhrase
import com.stiven.languageapp.repositories.EmergencyPhraseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel that interacts with emergency table in database.
 *
 * @param application maintains global application state.
 * */
class EmergencyPhraseViewModel(application: Application): AndroidViewModel(application) {

    private val repository: EmergencyPhraseRepository
    private val _dataList = MutableLiveData<List<EmergencyPhrase>>()
    val dataList: LiveData<List<EmergencyPhrase>> = _dataList

    /**
     * Function called when the view-model is called. Initializer.
     * */
    init {
        val emergencyPhraseDao = AppDatabase.getDatabase(application).emergencyPhraseDao()
        repository = EmergencyPhraseRepository(emergencyPhraseDao)
        viewModelScope.launch {
            repository.getAllPhrases(AppDatabase.getDatabase(application))
                .collect {dataList ->
                    _dataList.value = dataList
                }
        }
    }
    /**
     * Function that inserts an emergency phrase in database.
     *
     * @param phrase phrase object to be inserted.
     * */
    fun insertEmergencyPhrase(phrase: EmergencyPhrase){
        viewModelScope.launch (Dispatchers.IO){
            repository.insertEmergencyPhrase(phrase)
        }
    }
    /**
     * Function that updates an emergency phrase in database.
     *
     * @param phrase to be updated.
     * */
    fun updateEmergencyPhrase(phrase: EmergencyPhrase){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateEmergencyPhrase(phrase)
        }
    }
    /**
     * Function that deletes a phrase from database.
     *
     * @param phrase word to be deleted.
     * */
    fun deleteEmergencyPhrase(phrase: EmergencyPhrase){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteEmergencyPhrase(phrase)
        }
    }
}