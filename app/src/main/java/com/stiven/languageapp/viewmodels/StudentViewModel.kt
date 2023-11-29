package com.stiven.languageapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.entities.Student
import com.stiven.languageapp.repositories.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentViewModel(application: Application): AndroidViewModel(application) {

    private val app = application
    private val repository: StudentRepository
    private val _dataList = MutableLiveData<List<Student>>()
    private val _singleStudent = MutableLiveData<Student>()
    val dataList: LiveData<List<Student>> = _dataList
    val singleStudent: LiveData<Student> = _singleStudent

    init {
        val studentDao = AppDatabase.getDatabase(application).studentDao()
        repository = StudentRepository(studentDao)
        viewModelScope.launch {
            repository.getStudentsFromDatabase(AppDatabase.getDatabase(application))
                .collect { dataList ->
                    _dataList.value = dataList
                }
        }
    }

    fun userCourseExists(student: Student): Boolean{
        return dataList.value?.find { it.name == student.name && it.course == student.course } != null
    }

    fun insertStudent(student: Student){
        viewModelScope.launch (Dispatchers.IO) {
            repository.insertStudent(student)
        }
    }

    fun deleteStudent(student: Student){
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteStudent(student)
        }
    }
}