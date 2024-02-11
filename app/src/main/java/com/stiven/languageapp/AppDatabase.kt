package com.stiven.languageapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stiven.languageapp.dao.LetterDao
import com.stiven.languageapp.dao.LettersLearntDao
import com.stiven.languageapp.dao.StudentDao
import com.stiven.languageapp.dao.WordDao
import com.stiven.languageapp.model.Letter
import com.stiven.languageapp.model.LetterLearnt
import com.stiven.languageapp.model.Student
import com.stiven.languageapp.model.Word

/**
 * Abstract class that will be used to create the database.
 * */
@Database(entities = [Word::class, Student::class, Letter::class, LetterLearnt::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun studentDao(): StudentDao
    abstract fun letterDao(): LetterDao
    abstract fun lettersLearntDao(): LettersLearntDao
    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}