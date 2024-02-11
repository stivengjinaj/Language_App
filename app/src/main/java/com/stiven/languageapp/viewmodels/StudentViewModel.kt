package com.stiven.languageapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.model.Student
import com.stiven.languageapp.repositories.StudentRepository
import com.stiven.languageapp.utils.Languages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * View-model that interacts with Student's table in database.
 * Other functions make use of this view-model if they need
 * data about all students, single student or want to delete
 * a/all student(s) or insert in/from database.
 *
 * @param application maintains global application state.
 * */
class StudentViewModel(application: Application): AndroidViewModel(application) {

    private val app = application
    private val repository: StudentRepository
    private val _dataList = MutableLiveData<List<Student>>()
    private val _singleStudent = MutableLiveData<Student>()
    var dataList: LiveData<List<Student>> = _dataList
    val singleStudent: LiveData<Student> = _singleStudent

    /**
     * Function called when the view-model is called. Initializer.
     * */
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

    /**
     * Function to check if a student with the same course as the one
     * being inserted exists.
     *
     * @param student student to check if exists.
     * @return true if a student with the same course exists, false otherwise.
     * */
    fun userCourseExists(student: Student): Boolean{
        return dataList.value?.find { it.name == student.name && it.course == student.course } != null
    }

    /**
     * Function that insert a student in database.
     *
     * @param student student to be inserted.
     * */
    fun insertStudent(student: Student){
        viewModelScope.launch (Dispatchers.IO) {
            repository.insertStudent(student)
        }
    }

    /**
     * Function that updates student's points.
     *
     * @param studentId student to update.
     * @param points student's new points.
     * */
    fun updateStudent(studentId: String, points: Int){
        viewModelScope.launch (Dispatchers.IO) {
            repository.updateStudent(studentId, points)
        }
    }

    /**
     * Function that deletes a student from database.
     *
     * @param studentName name of the student to be deleted.
     * @param course language the student is learning.
     * */
    fun deleteStudent(studentName: String, course: Languages){
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteStudent(studentName, course)
        }
    }

    /**
     * Function that deletes all the student from database.
     * */
    fun deleteAllStudents(){
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteAllStudents()
        }
    }
}