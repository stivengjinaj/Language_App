package com.stiven.languageapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.model.Letter
import com.stiven.languageapp.repositories.LetterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Class that creates the view-model for Letter table.
 *
 * @param application maintains global application state.
 * */
class LetterViewModel(application: Application): AndroidViewModel(application) {

    private val app = application
    private val repository: LetterRepository
    private val _dataList = MutableLiveData<List<Letter>>()
    var dataList: LiveData<List<Letter>> = _dataList

    init {
        val letterDao = AppDatabase.getDatabase(application).letterDao()
        repository = LetterRepository(letterDao)
        viewModelScope.launch {
            repository.getLetters(AppDatabase.getDatabase(application))
                .collect{dataList ->
                    _dataList.value = dataList
                }
        }
    }

    /**
     * Function that inserts a letter and its similarity in database.
     *
     * @param letter letter object to insert.
     * */
    fun insertLetter(letter: Letter){
        viewModelScope.launch (Dispatchers.IO){
            repository.insertLetter(letter)
        }
    }

    /**
     * Function that deletes a similarity.
     *
     * @param similarTo similarity to delete.
     * */
    fun deleteSimilarTo(similarTo: String){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteSimilarTo(similarTo)
        }
    }
    /**
     * Function to delete all data.
     * */
    fun deleteAll(){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAll()
        }
    }
}