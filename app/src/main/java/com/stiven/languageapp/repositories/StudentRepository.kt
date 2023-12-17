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

    /**
     * Function used to insert a student in database.
     *
     * @param student student to insert.
     * */
    suspend fun insertStudent(student: Student){
        studentDao.insertStudent(student)
    }

    /**
     * Function that gets all students present in database.
     *
     * @param database application database.
     * @return returns an async sequence of data in form of a list.
     * */
    fun getStudentsFromDatabase(database: AppDatabase): Flow<List<Student>>{
        return database.studentDao().getAllStudents()
    }

    /**
     * Function that deletes a student. It searches the student to delete
     * by name.
     *
     * @param studentName student to be deleted name.
     * */
    fun deleteStudent(studentName: String){
        studentDao.deleteStudent(studentName)
    }

    /**
     * Function that deletes all students in database.
     * */
    fun deleteAllStudents(){
        studentDao.deleteAll()
    }
}