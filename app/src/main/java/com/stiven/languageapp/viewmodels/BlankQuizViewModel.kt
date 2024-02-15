package com.stiven.languageapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.model.BlankQuiz
import com.stiven.languageapp.repositories.BlankQuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for blank question quiz.
 *
 * @param application Global application state.
 * */
class BlankQuizViewModel(application: Application): AndroidViewModel(application) {
    private val repository: BlankQuestionRepository
    private val _dataList = MutableLiveData<List<BlankQuiz>>()
    var dataList: LiveData<List<BlankQuiz>> = _dataList

    /**
     * Function called when the view-model is called. Initializer.
     * */
    init {
        val blankQuizDao = AppDatabase.getDatabase(application).blankQuizDao()
        repository = BlankQuestionRepository(blankQuizDao)
        viewModelScope.launch {
            repository.getAllBlankQuiz(AppDatabase.getDatabase(application))
                .collect{ dataList ->
                    _dataList.value = dataList
                }
        }
    }
    /**
     * Function that handles question insertion.
     *
     * @param blankQuiz blank quiz question to insert.
     * */
    fun insertQuizQuestion(blankQuiz: BlankQuiz){
        viewModelScope.launch (Dispatchers.IO){
            repository.insertBlankQuiz(blankQuiz)
        }
    }
    /**
     * Function that handles question update.
     *
     * @param blankQuiz blank quiz question to update.
     * */
    fun updateBlankQuiz(blankQuiz: BlankQuiz){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateBlankQuiz(blankQuiz)
        }
    }
    /**
     * Function that handles question delete.
     *
     * @param blankQuizQuestion blank quiz question to delete.
     * */
    fun deleteBlankQuiz(blankQuizQuestion: String){
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteBlankQuiz(blankQuizQuestion)
        }
    }
}