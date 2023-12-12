package com.stiven.languageapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stiven.languageapp.model.Student
import kotlinx.coroutines.flow.Flow

/**
 *  DAO interface for Students table in database. Executes CRUD
 *  operations in Student Tables.
 * */
@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudent(student: Student)

    @Query("DELETE FROM Students WHERE name = :studentName")
    fun deleteStudent(studentName: String)

    @Query("DELETE FROM Students")
    fun deleteAll()

    @Query("SELECT * FROM Students")
    fun getAllStudents(): Flow<List<Student>>
}