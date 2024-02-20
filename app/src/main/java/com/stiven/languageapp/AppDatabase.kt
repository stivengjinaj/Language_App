package com.stiven.languageapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stiven.languageapp.dao.BlankQuizDao
import com.stiven.languageapp.dao.EmergencyPhraseDao
import com.stiven.languageapp.dao.LetterDao
import com.stiven.languageapp.dao.LettersLearntDao
import com.stiven.languageapp.dao.QuizAnswerDao
import com.stiven.languageapp.dao.QuizDao
import com.stiven.languageapp.dao.StudentDao
import com.stiven.languageapp.dao.WordDao
import com.stiven.languageapp.model.BlankQuiz
import com.stiven.languageapp.model.EmergencyPhrase
import com.stiven.languageapp.model.Letter
import com.stiven.languageapp.model.LetterLearnt
import com.stiven.languageapp.model.Quiz
import com.stiven.languageapp.model.QuizAnswer
import com.stiven.languageapp.model.Student
import com.stiven.languageapp.model.Word

/**
 * Abstract class that will be used to create the database.
 * */
@Database(
    entities = [
        Word::class,
        Student::class,
        Letter::class,
        LetterLearnt::class,
        BlankQuiz::class,
        Quiz::class,
        QuizAnswer::class,
        EmergencyPhrase::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun studentDao(): StudentDao
    abstract fun letterDao(): LetterDao
    abstract fun lettersLearntDao(): LettersLearntDao
    abstract fun quizDao(): QuizDao
    abstract fun quizAnswerDao(): QuizAnswerDao
    abstract fun blankQuizDao(): BlankQuizDao
    abstract fun emergencyPhraseDao(): EmergencyPhraseDao
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
                ).createFromAsset("database/app_database.db").build()

                INSTANCE = instance
                return instance
            }
        }
    }
}