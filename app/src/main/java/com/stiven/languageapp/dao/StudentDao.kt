package com.stiven.languageapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stiven.languageapp.entities.Student
import kotlinx.coroutines.flow.Flow

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