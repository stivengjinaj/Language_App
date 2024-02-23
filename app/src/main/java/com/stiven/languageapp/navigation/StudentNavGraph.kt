package com.stiven.languageapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stiven.languageapp.screens.Lessons
import com.stiven.languageapp.model.BottomBarScreens
import com.stiven.languageapp.screens.Dictionary
import com.stiven.languageapp.screens.Exercises
import com.stiven.languageapp.utils.LearningType
import com.stiven.languageapp.utils.QuestionType
import com.stiven.languageapp.viewmodels.BlankQuizViewModel
import com.stiven.languageapp.viewmodels.LetterViewModel
import com.stiven.languageapp.viewmodels.LettersLearntViewModel
import com.stiven.languageapp.viewmodels.QuizAnswerViewModel
import com.stiven.languageapp.viewmodels.QuizViewModel
import com.stiven.languageapp.viewmodels.SpeechToTextViewModel
import com.stiven.languageapp.viewmodels.StudentViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import com.stiven.languageapp.viewmodels.WordViewModel

/**
 * Navigation graph for StudentPanel. Contains pages like: Lessons,
 * Exercises, Dictionary and Logout.
 *
 * @param rootNavController root's navigation controller used to go back when student logs out.
 * @param navController student panel's navigation controller.
 * @param studentViewModel student's view-model.
 * @param textToSpeechViewModel text to speech view-model.
 * @param studentId id used to get student's information in order to personalize StudentPanel.
 * @param speechToTextViewModel speech to text view-model.
 * @param letterViewModel letter's view-model.
 * @param lettersLearntViewModel Letters learnt ViewModel.
 * @param quizViewModel Quiz ViewModel.
 * @param quizAnswerViewModel Quiz answer ViewModel.
 * @param blankQuizViewModel Fill in quiz ViewModel.
 * @param wordViewModel Words ViewModel.
 * */
@Composable
fun StudentNavGraph(
    rootNavController: NavHostController,
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    textToSpeechViewModel: TextToSpeechViewModel,
    studentId: String,
    speechToTextViewModel: SpeechToTextViewModel,
    letterViewModel: LetterViewModel,
    lettersLearntViewModel: LettersLearntViewModel,
    quizViewModel: QuizViewModel,
    quizAnswerViewModel: QuizAnswerViewModel,
    blankQuizViewModel: BlankQuizViewModel,
    wordViewModel: WordViewModel
) {
    //USE THIS FOR THE REAL FUNCTIONING.
    /*insertStudentPoints(
        studentId = studentId,
        lettersLearntViewModel = lettersLearntViewModel,
        quizAnswerViewModel = quizAnswerViewModel,
        studentViewModel = studentViewModel
    )*/
    //USE THIS FUNCTION TO DEMONSTRATE.
    demoInsertStudentPoints(
        studentId = studentId,
        lettersLearntViewModel = lettersLearntViewModel,
        quizAnswerViewModel = quizAnswerViewModel,
        studentViewModel = studentViewModel
    )

    val student = studentViewModel.dataList.value?.find { it.id.toString() == studentId}
    NavHost(
        navController = navController,
        startDestination = BottomBarScreens.Lessons.route
    ){
        composable(route = BottomBarScreens.Lessons.route){
            if (student != null) {
                Lessons(
                    navController = navController,
                    studentViewModel = studentViewModel,
                    studentId = studentId,
                    studentPoints = student.points
                )
            }
        }
        composable(route = FirstCloudRoutes.FIRST_CLOUD){
            FirstCloudNavGraph(
                studentId = studentId,
                rootNavController = rootNavController,
                studentViewModel = studentViewModel ,
                navController = rememberNavController(),
                textToSpeechViewModel = textToSpeechViewModel,
                speechToTextViewModel = speechToTextViewModel,
                lettersLearntViewModel = lettersLearntViewModel,
                letterViewModel = letterViewModel
            )
        }
        composable(route = SecondCloudNavGraph.SECOND_CLOUD){
            SecondCloudNavGraph(
                studentId = studentId,
                rootNavController = rootNavController,
                studentViewModel = studentViewModel,
                navController = rememberNavController(),
                textToSpeechViewModel = textToSpeechViewModel,
                lettersLearntViewModel = lettersLearntViewModel
            )
        }
        composable(route = ThirdCloudNavGraph.THIRD_CLOUD){
            ThirdCloudNavGraph(
                studentId = studentId,
                rootNavController = rootNavController,
                studentViewModel = studentViewModel,
                navController = rememberNavController(),
                textToSpeechViewModel = textToSpeechViewModel,
                quizViewModel = quizViewModel,
                quizAnswerViewModel = quizAnswerViewModel
            )
        }
        composable(route = FourthCloudNavGraph.FOURTH_CLOUD){
            FourthCloudNavGraph(
                studentId = studentId,
                rootNavController = rootNavController,
                studentViewModel = studentViewModel,
                navController = rememberNavController(),
                textToSpeechViewModel = textToSpeechViewModel,
                blankQuizViewModel = blankQuizViewModel,
                quizAnswerViewModel = quizAnswerViewModel
            )
        }
        composable(route = BottomBarScreens.Exercises.route){
            Exercises(
                studentNavController = navController,
                textToSpeechViewModel = textToSpeechViewModel
            )
        }

        composable(route = BottomBarScreens.Dictionary.route){
            Dictionary(
                wordViewModel = wordViewModel,
                textToSpeechViewModel = textToSpeechViewModel
            )
        }

        composable(route = BottomBarScreens.Logout.route){
            rootNavController.popBackStack()
            rootNavController.navigate(Graph.MAIN+"/classroom")
        }
    }
}

/**
 * Function demonstrate how points work without completely
 * completing the exercises.
 *
 * @param studentId student id to check.
 * @param lettersLearntViewModel letters learnt viewModel.
 * @param quizAnswerViewModel answered quiz viewModel.
 * @param studentViewModel student viewModel
 * */
fun demoInsertStudentPoints(
    studentId: String,
    lettersLearntViewModel: LettersLearntViewModel,
    quizAnswerViewModel: QuizAnswerViewModel,
    studentViewModel: StudentViewModel
){
    val lettersPronounced = lettersLearntViewModel.dataList.value?.filter { it.studentId == studentId && it.learningType == LearningType.PRONOUNCING }?.size
    val lettersWritten = lettersLearntViewModel.dataList.value?.filter { it.studentId == studentId && it.learningType == LearningType.WRITTEN }?.size
    val quizAnswered = quizAnswerViewModel.dataList.value?.filter { it.studentId == studentId && it.questionType == QuestionType.TRANSLATE }?.size
    val blankAnswered = quizAnswerViewModel.dataList.value?.filter { it.studentId == studentId && it.questionType == QuestionType.FILL_BLANK }?.size
    if(lettersPronounced != null && lettersWritten != null && quizAnswered != null && blankAnswered != null){
        val pronounced = if(lettersPronounced >= 1) 50 else 0
        val written = if(lettersWritten >= 1) 50 else 0
        val translations = if(quizAnswered >= 1) 50 else 0
        val blank = if(blankAnswered >= 1) 50 else 0
        Log.d("NOT NULL", "${pronounced+written+translations+blank}")
        studentViewModel.updateStudent(studentId, pronounced+written+translations+blank)
    }else{
        Log.d("NULL", "NULL")
    }
}
/*
/**
 * Function used to insert student points based in his progress.
 *
 * @param studentId student id to check.
 * @param lettersLearntViewModel letters learnt viewModel.
 * @param quizAnswerViewModel answered quiz viewModel.
 * @param studentViewModel student viewModel
 * */
fun insertStudentPoints(
    studentId: String,
    lettersLearntViewModel: LettersLearntViewModel,
    quizAnswerViewModel: QuizAnswerViewModel,
    studentViewModel: StudentViewModel
){
    val lettersPronounced = lettersLearntViewModel.dataList.value?.filter { it.studentId == studentId && it.learningType == LearningType.PRONOUNCING }?.size?.let {
        if(it != 0){
            mapValue(it, 1.0, 21.0, 1.0, 50.0)
        }else 0
    }?.toInt()
    val lettersWritten = lettersLearntViewModel.dataList.value?.filter { it.studentId == studentId && it.learningType == LearningType.WRITTEN }?.size?.let {
        if(it != 0){
            mapValue(it, 1.0, 21.0, 1.0, 50.0)
        }else 0
    }?.toInt()
    val quizAnswered = quizAnswerViewModel.dataList.value?.filter { it.studentId == studentId && it.questionType == QuestionType.TRANSLATE }?.size?.let {
        if(it != 0){
            mapValue(it, 1.0, 12.0, 1.0, 50.0)
        }else 0
    }?.toInt()
    val blankAnswered = quizAnswerViewModel.dataList.value?.filter { it.studentId == studentId && it.questionType == QuestionType.FILL_BLANK }?.size?.let {
        if(it != 0){
            mapValue(it, 1.0, 4.0, 1.0, 50.0)
        }else 0
    }?.toInt()
    if(lettersPronounced != null && lettersWritten != null && quizAnswered != null && blankAnswered != null){
        studentViewModel.updateStudent(studentId, lettersPronounced+lettersWritten+quizAnswered+blankAnswered)
    }
}

fun mapValue(
    value: Int,
    fromIntervalStart: Double,
    fromIntervalEnd: Double,
    toIntervalStart: Double,
    toIntervalEnd: Double
): Double {
    // Check if the value is within the fromInterval
    if (value < fromIntervalStart || value > fromIntervalEnd) {
        throw IllegalArgumentException("Value is not within the fromInterval")
    }
    // Calculate the proportion of the value within the fromInterval
    val proportion = (value - fromIntervalStart) / (fromIntervalEnd - fromIntervalStart)

    // Map the proportion to the toInterval
    return toIntervalStart + proportion * (toIntervalEnd - toIntervalStart)
}
*/