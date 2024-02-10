package com.stiven.languageapp.screens.firstCloud

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.stiven.languageapp.navigation.FirstCloudGraph
import com.stiven.languageapp.navigation.Graph
import com.stiven.languageapp.view.LogoBannerNavigation

/**
 * Introduction to the exercises taught in this section.
 *
 * @param navController Current cloud navigation controller.
 * @param rootNavController Root navigation controller.
 * @param studentId Student's id.
 * */
@Composable
fun Introduction(
    navController: NavHostController,
    rootNavController: NavHostController,
    studentId: String
){
    val screenSize = LocalConfiguration.current.screenWidthDp
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LogoBannerNavigation(
            screenSize = screenSize,
            onBackButtonClick = {
                rootNavController.navigate(Graph.LESSONS+"/$studentId")
            }
        )
        Spacer(modifier = Modifier.height((screenSize / 6).dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Button(onClick = {
                navController.navigate(FirstCloudGraph.ALPHABET_PRONOUNCING)
            }) {
                Text(
                    "NEXT"
                )
            }
        }
    }
}