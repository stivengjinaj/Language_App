package com.stiven.languageapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.model.Word
import com.stiven.languageapp.repositories.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * View-model that interacts with Word's table in database.
 * Other functions make use of this view-model if they need
 * data about all words, single words or want to delete
 * a/all word(s) or insert a word in/from database.
 *
 * @param application maintains global application state.
 * */
class WordViewModel(application: Application): AndroidViewModel(application) {

    private val app = application
    private val repository: WordRepository
    private val _dataList = MutableLiveData<List<Word>>()
    val dataList: LiveData<List<Word>> = _dataList
    /**
     * Function called when the view-model is called. Initializer.
     * */
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
    /**
     * Function that inserts a word in database.
     *
     * @param word word object to be inserted.
     * */
    fun insertWord(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertWord(word)
        }
    }
    /**
     * Function that updates a word in database.
     *
     * @param word to be updated.
     * */
    fun updateWord(word: Word){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateWord(word)
        }
    }
    /**
     * Function that updates Word's table in database.
     *
     * @param words list of all the words to be updated.
     * */
    fun updateAll(words: List<Word>){
        viewModelScope.launch (Dispatchers.IO) {
            repository.updateAll(words)
        }
    }
    /**
     * Function that deletes a word from database.
     *
     * @param word word to be deleted.
     * */
    fun deleteWord(word: Word){
        viewModelScope.launch( Dispatchers.IO ) {
            repository.deleteWord(word)
        }
    }
}