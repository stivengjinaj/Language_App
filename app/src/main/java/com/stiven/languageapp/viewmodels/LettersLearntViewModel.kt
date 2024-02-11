package com.stiven.languageapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.model.LetterLearnt
import com.stiven.languageapp.repositories.LetterLearntRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LettersLearntViewModel (application: Application): AndroidViewModel(application) {

    private val app = application
    private val repository: LetterLearntRepository
    private val _dataList = MutableLiveData<List<LetterLearnt>>()
    var dataList: LiveData<List<LetterLearnt>> = _dataList

    init {
        val lettersLearntDao = AppDatabase.getDatabase(application).lettersLearntDao()
        repository = LetterLearntRepository(lettersLearntDao)
        viewModelScope.launch {
            repository.getAllLettersLearnt(AppDatabase.getDatabase(application))
                .collect { dataList ->
                    _dataList.value = dataList
                }
        }
    }

    fun insertLetterLearnt(letterLearnt: LetterLearnt){
        viewModelScope.launch (Dispatchers.IO) {
            repository.insertLetterLearnt(letterLearnt)
        }
    }

    fun deleteAllLetters(){
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}