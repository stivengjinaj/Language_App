package com.stiven.languageapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.entities.Word
import com.stiven.languageapp.repositories.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordViewModel(application: Application): AndroidViewModel(application) {

    private val app = application
    private val repository: WordRepository
    private val _dataList = MutableLiveData<List<Word>>()
    private val _singleWord = MutableLiveData<Word>()
    val dataList: LiveData<List<Word>> = _dataList
    val singleWord: LiveData<Word> = _singleWord

    init {
        val wordDao = AppDatabase.getDatabase(application).wordDao()
        repository = WordRepository(wordDao)
        viewModelScope.launch {
            repository.getWordsFromDatabase(AppDatabase.getDatabase(application))
                .collect { dataList ->
                    _dataList.value = dataList
                }
        }
    }

    fun getSingleWord(word: String): Word? {
        return dataList.value?.find { it.word == word }
    }

    fun insertWord(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertWord(word)
        }
    }

    fun updateWord(word: Word){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateWord(word)
        }
    }

    fun updateAll(words: List<Word>){
        viewModelScope.launch (Dispatchers.IO) {
            repository.updateAll(words)
        }
    }

    fun deleteWord(word: Word){
        viewModelScope.launch( Dispatchers.IO ) {
            repository.deleteWord(word)
        }
    }
}