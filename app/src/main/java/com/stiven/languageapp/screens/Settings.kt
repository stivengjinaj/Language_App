package com.stiven.languageapp.screens

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
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
import com.stiven.languageapp.R
import com.stiven.languageapp.utils.Languages
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Settings(){
    val context = LocalContext.current
    val screenSize = LocalConfiguration.current.screenHeightDp / 20
    Column(modifier = Modifier.fillMaxSize()){
        Spacer(modifier = Modifier.height((screenSize + 50).dp))
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
        // Method for android 13+
        context.getSystemService(LocaleManager::class.java).applicationLocales = LocaleList.forLanguageTags(languageString)
    } else {
        // Method for android 12-
        val locale = Locale(languageString)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        context.createConfigurationContext(configuration)
    }
}