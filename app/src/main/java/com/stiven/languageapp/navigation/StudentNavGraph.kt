package com.stiven.languageapp.navigation

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
    insertStudentPoints(
        studentId = studentId,
        lettersLearntViewModel = lettersLearntViewModel,
        quizAnswerViewModel = quizAnswerViewModel,
        studentViewModel = studentViewModel
    )
    NavHost(
        navController = navController,
        startDestination = BottomBarScreens.Lessons.route
    ){
        composable(route = BottomBarScreens.Lessons.route){
            Lessons(
                rootNavController = rootNavController,
                navController = navController,
                studentViewModel = studentViewModel,
                textToSpeechViewModel = textToSpeechViewModel,
                speechToTextViewModel = speechToTextViewModel,
                studentId = studentId
            )
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
                studentNavController = navController
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

fun insertStudentPoints(
    studentId: String,
    lettersLearntViewModel: LettersLearntViewModel,
    quizAnswerViewModel: QuizAnswerViewModel,
    studentViewModel: StudentViewModel
){
    val lettersPronounced = lettersLearntViewModel.dataList.value?.filter { it.studentId == studentId && it.learningType == LearningType.PRONOUNCING }?.size?.let {
        if(it != 0){
            mapValue(it, 1.0, 22.0, 1.0, 50.0)
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
            mapValue(it, 1.0, 1.0, 5.0, 100.0)
        }else 0
    }?.toInt()
    //val lettersPoints = lettersLearntViewModel.dataList.value?.filter { it.studentId == studentId }?.size
    //val lettersPoints = lettersWritten?.let { lettersPronounced?.plus(it) }
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