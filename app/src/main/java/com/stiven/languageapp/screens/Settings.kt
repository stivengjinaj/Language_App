package com.stiven.languageapp.screens

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Update
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.stiven.languageapp.R
import com.stiven.languageapp.model.BlankQuiz
import com.stiven.languageapp.model.Letter
import com.stiven.languageapp.model.Quiz
import com.stiven.languageapp.model.Word
import com.stiven.languageapp.navigation.Graph
import com.stiven.languageapp.utils.Languages
import com.stiven.languageapp.viewmodels.BlankQuizViewModel
import com.stiven.languageapp.viewmodels.LetterViewModel
import com.stiven.languageapp.viewmodels.QuizViewModel
import com.stiven.languageapp.viewmodels.TextToSpeechViewModel
import com.stiven.languageapp.viewmodels.WordViewModel
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Settings(
    rootNavController: NavHostController,
    textToSpeechViewModel: TextToSpeechViewModel,
    letterViewModel: LetterViewModel,
    wordViewModel: WordViewModel,
    quizViewModel: QuizViewModel,
    blankQuizViewModel: BlankQuizViewModel
){
    val context = LocalContext.current
    val screenSize = LocalConfiguration.current.screenHeightDp / 20
    Column(modifier = Modifier.fillMaxSize()){
        Spacer(modifier = Modifier.height((screenSize + 50).dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = context.getString(R.string.play_walkthrough),
                color = MaterialTheme.colorScheme.inversePrimary,
                fontSize = (screenSize - 20).sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            IconButton(
                modifier = Modifier.size((screenSize + 40).dp),
                onClick = {
                    rootNavController.popBackStack()
                    rootNavController.navigate(Graph.TOUR)
                },
            ) {
                Icon(
                    Icons.Rounded.PlayArrow,
                    contentDescription = "PLAY",
                    tint = MaterialTheme.colorScheme.inversePrimary,
                    modifier = Modifier.size((screenSize + 40).dp).combinedClickable(
                        onClick = {
                            rootNavController.popBackStack()
                            rootNavController.navigate(Graph.TOUR)
                        },
                        onLongClick = {
                            textToSpeechViewModel.customTextToSpeech(
                                context,
                                context.getString(R.string.play_walkthrough),
                                Locale.getDefault()
                            )
                        }
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height((screenSize - 20).dp))
        Row (
            modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.combinedClickable(
                        onClick = {},
                        onLongClick = {
                            textToSpeechViewModel.customTextToSpeech(
                                context,
                                context.getString(R.string.change_language),
                                Locale.getDefault()
                            )
                        }
                    ),
                    text = context.getString(R.string.change_language),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontSize = (screenSize - 20).sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = painterResource(id = R.drawable.gb),
                        contentDescription = "GB FLAG",
                        modifier = Modifier
                            .size((screenSize).dp)
                            .combinedClickable(
                                onClick = {
                                    changeLanguage(Languages.ENGLISH, context)
                                }
                            )
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Image(
                        painter = painterResource(id = R.drawable.it),
                        contentDescription = "IT FLAG",
                        modifier = Modifier
                            .size((screenSize).dp)
                            .combinedClickable(
                                onClick = {
                                    changeLanguage(Languages.ITALIAN, context)
                                }
                            )
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Image(
                        painter = painterResource(id = R.drawable.fr),
                        contentDescription = "FR FLAG",
                        modifier = Modifier
                            .size((screenSize).dp)
                            .combinedClickable(
                                onClick = {
                                    changeLanguage(Languages.FRENCH, context)
                                }
                            )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height((screenSize + 20).dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier.size((screenSize + 30).dp),
                onClick = {
                onlineUpdate(
                    letterViewModel = letterViewModel,
                    wordViewModel = wordViewModel,
                    quizViewModel = quizViewModel,
                    blankQuizViewModel = blankQuizViewModel
                )
            }) {
                Icon(
                    imageVector = Icons.Rounded.Update,
                    modifier = Modifier.size((screenSize + 20).dp).combinedClickable(
                        onClick = {
                            onlineUpdate(
                                letterViewModel = letterViewModel,
                                wordViewModel = wordViewModel,
                                quizViewModel = quizViewModel,
                                blankQuizViewModel = blankQuizViewModel
                            )
                        },
                        onLongClick = {
                            textToSpeechViewModel.customTextToSpeech(
                                context,
                                context.getString(R.string.update_tts),
                                Locale.getDefault()
                            )
                        }
                    ),
                    contentDescription = "Update",
                    tint = MaterialTheme.colorScheme.inversePrimary
                )
            }
        }
    }
}
/**
 * Function used to change the language of the app.
 *
 * @param language the language to set.
 * @param context application context.
 * */
fun changeLanguage(language: Languages, context: Context) {
    val languageString = when (language) {
        Languages.ENGLISH -> "en"
        Languages.ITALIAN -> "it"
        Languages.FRENCH -> "fr"
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java).applicationLocales =
            LocaleList.forLanguageTags(languageString)
    } else {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(languageString)
        )
    }
}

/**
 * Function that updates application data from online database.
 *
 * @param letterViewModel ViewModel that handles letters table.
 * @param wordViewModel ViewModel that handles word table.
 * @param quizViewModel ViewModel that handles quiz table.
 * @param blankQuizViewModel ViewModel that handles blank quiz table.
 * */
fun onlineUpdate(
    letterViewModel: LetterViewModel,
    wordViewModel: WordViewModel,
    quizViewModel: QuizViewModel,
    blankQuizViewModel: BlankQuizViewModel
){
    val db = Firebase.firestore
    updateLetters(db, letterViewModel)
    updateWords(db, wordViewModel)
    updateQuiz(db, quizViewModel)
    updateBlankQuiz(db, blankQuizViewModel)
}
/**
 * Function that updates letters table in database.
 *
 * @param db FirebaseFirestore reference.
 * @param letterViewModel letters ViewModel.
 * */
private fun updateLetters(
    db: FirebaseFirestore,
    letterViewModel: LetterViewModel
){
    db.collection("letters")
        .document("Insert")
        .collection("Insert")
        .get()
        .addOnSuccessListener {
            it?.documents?.forEach { documentSnapshot ->
                val id = documentSnapshot.getDouble("id")?.toInt()
                val similarTo = documentSnapshot.getString("similarTo")
                val letter = documentSnapshot.getString("letter")
                if(letterViewModel.dataList.value?.find { it.id == id } == null) {
                    val letterToInsert = id?.let { it1 ->
                        Letter(
                            id = it1,
                            similarTo!!,
                            letter!!
                        )
                    }
                    if (letterToInsert != null) {
                        letterViewModel.insertLetter(letterToInsert)
                    }
                }
            }
        }
    db.collection("letters")
        .document("Delete")
        .collection("Delete")
        .get()
        .addOnSuccessListener {
            it?.documents?.forEach { documentSnapshot ->
                val similarTo = documentSnapshot.getString("similarTo")
                if(letterViewModel.dataList.value?.find { letter -> letter.similarTo == similarTo } != null && similarTo != null){
                    letterViewModel.deleteSimilarTo(similarTo)
                }
            }
        }
    db.collection("letters")
        .document("Update")
        .collection("Update")
        .get()
        .addOnSuccessListener {
            it?.documents?.forEach { documentSnapshot ->
                val id = documentSnapshot.getDouble("id")?.toInt()
                val similarTo = documentSnapshot.getString("similarTo")
                val letter = documentSnapshot.getString("letter")
                if(id != null && similarTo != null && letter != null &&
                    letterViewModel.dataList.value?.find { it.id == id } != null
                    ){
                    letterViewModel.updateLetter(
                        Letter(
                            id = id,
                            similarTo = similarTo,
                            letter = letter
                        )
                    )
                }
            }
        }
}
/**
 * Function that updates words table in database.
 *
 * @param db FirebaseFirestore reference.
 * @param wordViewModel word ViewModel.
 * */
private fun updateWords(
    db: FirebaseFirestore,
    wordViewModel: WordViewModel
){
    db.collection("word")
        .document("Insert")
        .collection("Insert")
        .get()
        .addOnSuccessListener {
            it?.documents?.forEach { documentSnapshot ->
                val id = documentSnapshot.getDouble("id")?.toInt()
                val italian = documentSnapshot.getString("italian")
                val english = documentSnapshot.getString("english")
                val french = documentSnapshot.getString("french")
                if(wordViewModel.dataList.value?.find { word ->  word.italian == italian } == null){
                    if(id != null && italian != null && english != null && french != null){
                        wordViewModel.insertWord(
                            Word(
                                id = id,
                                italian = italian,
                                english = english,
                                french = french
                            )
                        )
                    }
                }
            }
        }
    db.collection("word")
        .document("Update")
        .collection("Update")
        .get()
        .addOnSuccessListener {
            it?.documents?.forEach { documentSnapshot ->
                val id = documentSnapshot.getDouble("id")?.toInt()
                val italian = documentSnapshot.getString("italian")
                val english = documentSnapshot.getString("english")
                val french = documentSnapshot.getString("french")
                if(wordViewModel.dataList.value?.find { word -> word.id == id } != null){
                    if(id != null && italian != null && english != null && french != null){
                        wordViewModel.updateWord(Word(
                            id = id,
                            italian = italian,
                            english = english,
                            french = french
                        ))
                    }
                }
            }
        }
    db.collection("word")
        .document("Delete")
        .collection("Delete")
        .get()
        .addOnSuccessListener {
            it?.documents?.forEach { documentSnapshot ->
                val id = documentSnapshot.getDouble("id")?.toInt()
                val italian = documentSnapshot.getString("italian")
                val english = documentSnapshot.getString("english")
                val french = documentSnapshot.getString("french")
                if(wordViewModel.dataList.value?.find { word -> word.id == id } != null){
                    if(id != null && italian != null && english != null && french != null)
                        wordViewModel.deleteWord(Word(
                            id = id,
                            italian = italian,
                            english = english,
                            french = french
                        ))
                }
            }
        }
}
/**
 * Function that updates quiz table in database.
 *
 * @param db FirebaseFirestore reference.
 * @param quizViewModel quiz ViewModel.
 * */
private fun updateQuiz(
    db: FirebaseFirestore,
    quizViewModel: QuizViewModel
){
    db.collection("quiz")
        .document("Insert")
        .collection("Insert")
        .get()
        .addOnSuccessListener {
            it?.documents?.forEach { documentSnapshot ->
                val question = documentSnapshot.getString("question")
                val firstEn = documentSnapshot.getString("firstEn")
                val firstFr = documentSnapshot.getString("firstFr")
                val secondEn = documentSnapshot.getString("secondEn")
                val secondFr = documentSnapshot.getString("secondFr")
                val thirdEn = documentSnapshot.getString("thirdEn")
                val thirdFr = documentSnapshot.getString("thirdFr")
                val answer = documentSnapshot.getString("answer")
                if(question != null && quizViewModel.dataList.value?.find { quiz -> quiz.question == question } == null){
                    if(firstEn != null && firstFr != null &&
                        secondEn != null && secondFr != null &&
                        thirdEn != null && thirdFr != null && answer != null){
                        quizViewModel.insertQuizQuestion(Quiz(
                            id = 0,
                            question = question,
                            firstEn = firstEn,
                            firstFr = firstFr,
                            secondEn = secondEn,
                            secondFr = secondFr,
                            thirdEn = thirdEn,
                            thirdFr = thirdFr,
                            answer = answer
                        ))
                    }
                }
            }
        }
    db.collection("quiz")
        .document("Update")
        .collection("Update")
        .get()
        .addOnSuccessListener {
            it?.documents?.forEach { documentSnapshot ->
                val id = documentSnapshot.getDouble("id")?.toInt()
                val question = documentSnapshot.getString("question")
                val firstEn = documentSnapshot.getString("firstEn")
                val firstFr = documentSnapshot.getString("firstFr")
                val secondEn = documentSnapshot.getString("secondEn")
                val secondFr = documentSnapshot.getString("secondFr")
                val thirdEn = documentSnapshot.getString("thirdEn")
                val thirdFr = documentSnapshot.getString("thirdFr")
                val answer = documentSnapshot.getString("answer")
                if(question != null && quizViewModel.dataList.value?.find { quiz -> quiz.question == question } != null){
                    if(firstEn != null && firstFr != null &&
                        secondEn != null && secondFr != null &&
                        thirdEn != null && thirdFr != null && answer != null){
                        quizViewModel.updateQuizQuestion(Quiz(
                            id = 0,
                            question = question,
                            firstEn = firstEn,
                            firstFr = firstFr,
                            secondEn = secondEn,
                            secondFr = secondFr,
                            thirdEn = thirdEn,
                            thirdFr = thirdFr,
                            answer = answer
                        ))
                    }
                }
            }
        }
    db.collection("quiz")
        .document("Delete")
        .collection("Delete")
        .get()
        .addOnSuccessListener {
            it?.documents?.forEach { documentSnapshot ->
                val question = documentSnapshot.getString("question")
                if(question != null && quizViewModel.dataList.value?.find { quiz -> quiz.question == question } != null){
                    quizViewModel.deleteQuizQuestion(question)
                }
            }
        }
}
/**
 * Function that updates blank quiz table in database.
 *
 * @param db FirebaseFirestore reference.
 * @param blankQuizViewModel blankQuiz ViewModel.
 * */
private fun updateBlankQuiz(
    db: FirebaseFirestore,
    blankQuizViewModel: BlankQuizViewModel
){
    db.collection("blank_quiz")
        .document("Insert")
        .collection("Insert")
        .get()
        .addOnSuccessListener {
            it?.documents?.forEach { documentSnapshot ->
                val question = documentSnapshot.getString("question")
                val first = documentSnapshot.getString("first")
                val second = documentSnapshot.getString("second")
                val third = documentSnapshot.getString("third")
                val answer = documentSnapshot.getString("answer")
                if(question != null && blankQuizViewModel.dataList.value?.find { blankQuiz -> blankQuiz.question == question } == null){
                    if(first != null && second != null && third != null && answer != null){
                        blankQuizViewModel.insertQuizQuestion(
                            BlankQuiz(
                                id = 0,
                                question = question,
                                first = first,
                                second = second,
                                third = third,
                                answer = answer
                            )
                        )
                    }
                }
            }
        }
    db.collection("blank_quiz")
        .document("Update")
        .collection("Update")
        .get()
        .addOnSuccessListener {
            it?.documents?.forEach { documentSnapshot ->
                val id = documentSnapshot.getDouble("id")?.toInt()
                val question = documentSnapshot.getString("question")
                val first = documentSnapshot.getString("first")
                val second = documentSnapshot.getString("second")
                val third = documentSnapshot.getString("third")
                val answer = documentSnapshot.getString("answer")
                if(question != null && blankQuizViewModel.dataList.value?.find { blankQuiz -> blankQuiz.question == question } != null){
                    if(id != null && first != null && second != null && third != null && answer != null){
                        blankQuizViewModel.insertQuizQuestion(
                            BlankQuiz(
                                id = id,
                                question = question,
                                first = first,
                                second = second,
                                third = third,
                                answer = answer
                            )
                        )
                    }
                }
            }
        }
    db.collection("blank_quiz")
        .document("Delete")
        .collection("Delete")
        .get()
        .addOnSuccessListener {
            it?.documents?.forEach { documentSnapshot ->
                val question = documentSnapshot.getString("question")
                if(question != null){
                    blankQuizViewModel.deleteBlankQuiz(question)
                }
            }
        }
}

