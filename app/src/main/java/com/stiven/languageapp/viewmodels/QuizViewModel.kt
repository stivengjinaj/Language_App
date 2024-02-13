package com.stiven.languageapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.model.Quiz
import com.stiven.languageapp.repositories.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizViewModel(application: Application): AndroidViewModel(application) {

    private val repository: QuizRepository
    private val _dataList = MutableLiveData<List<Quiz>>()
    var dataList: LiveData<List<Quiz>> = _dataList

    /**
     * Function called when the view-model is called. Initializer.
     * */
    init {
        val quizDao = AppDatabase.getDatabase(application).quizDao()
        repository = QuizRepository(quizDao)
        viewModelScope.launch {
            repository.getAllQuizQuestions(AppDatabase.getDatabase(application))
                .collect{ dataList ->
                    _dataList.value = dataList
                }
        }
    }
    /**
     * Function that handles question insertion.
     *
     * @param quizQuestion question to insert.
     * */
    fun insertQuizQuestion(quizQuestion: Quiz){
        viewModelScope.launch (Dispatchers.IO){
            repository.insertQuizQuestion(quizQuestion)
        }
    }
    /**
     * Function that handles question update.
     *
     * @param quizQuestion question to update.
     * */
    fun updateQuizQuestion(quizQuestion: Quiz){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateQuizQuestion(quizQuestion)
        }
    }
    /**
     * Function that handles question delete.
     *
     * @param quizQuestion question to delete.
     * */
    fun deleteQuizQuestion(quizQuestion: String){
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteQuizQuestion(quizQuestion)
        }
    }
}