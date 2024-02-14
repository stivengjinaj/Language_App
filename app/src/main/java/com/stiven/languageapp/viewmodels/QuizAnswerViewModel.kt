package com.stiven.languageapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.model.QuizAnswer
import com.stiven.languageapp.repositories.QuizAnswerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizAnswerViewModel (application: Application): AndroidViewModel(application) {

    private val repository: QuizAnswerRepository
    private val _dataList = MutableLiveData<List<QuizAnswer>>()
    var dataList: LiveData<List<QuizAnswer>> = _dataList

    /**
     * Function called when the view-model is called. Initializer.
     * */
    init {
        val quizAnswerDao = AppDatabase.getDatabase(application).quizAnswerDao()
        repository = QuizAnswerRepository(quizAnswerDao)
        viewModelScope.launch {
            repository.getAllQuizAnswers(AppDatabase.getDatabase(application))
                .collect{ dataList ->
                    _dataList.value = dataList
                }
        }
    }

    fun insertQuizAnswer(quizAnswer: QuizAnswer){
        viewModelScope.launch (Dispatchers.IO){
            repository.insertQuizAnswer(quizAnswer)
        }
    }

    fun updateQuizAnswer(quizAnswer: QuizAnswer){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateQuizAnswer(quizAnswer)
        }
    }
}