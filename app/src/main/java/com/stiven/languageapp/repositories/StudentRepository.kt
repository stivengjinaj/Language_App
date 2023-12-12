package com.stiven.languageapp.repositories

import com.stiven.languageapp.AppDatabase
import com.stiven.languageapp.dao.StudentDao
import com.stiven.languageapp.model.Student
import kotlinx.coroutines.flow.Flow

/**
 * Class (implementation of the interface) that makes use of student DAO
 * to execute the operations in the database.
 *
 * @param studentDao student's DAO
 * */
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