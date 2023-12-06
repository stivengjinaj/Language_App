package com.stiven.languageapp.repositories

import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.dao.StudentDao
import com.stiven.languageapp.entities.Student
import kotlinx.coroutines.flow.Flow

class StudentRepository(private val studentDao: StudentDao) {

    suspend fun insertStudent(student: Student){
        studentDao.insertStudent(student)
    }

    fun getStudentsFromDatabase(database: AppDatabase): Flow<List<Student>>{
        return database.studentDao().getAllStudents()
    }

    fun deleteStudent(studentName: String){
        studentDao.deleteStudent(studentName)
    }

    fun deleteAllStudents(){
        studentDao.deleteAll()
    }
}