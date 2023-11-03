package com.example.borutoapp.presentation.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.borutoapp.navigation.Screen
import com.example.borutoapp.presentation.common.ListContent
import com.example.borutoapp.presentation.components.RatingWidget
import com.example.borutoapp.ui.theme.LARGE_PADDING
import com.example.borutoapp.ui.theme.statusBarColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.lifecycle.HiltViewModel

@ExperimentalCoilApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val allHeroes = homeViewModel.getAllHeroes.collectAsLazyPagingItems()

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = MaterialTheme.colorScheme.statusBarColor)

    Scaffold(
        topBar = {
            HomeTopBar(onSearchClicked = {
                navController.navigate(Screen.Search.route)
            })
        },/*{
        paddingValues ->
        val padding = paddingValues.calculateTopPadding()
        RatingWidget(modifier = Modifier.padding(top = padding), rating = 3.1)
    }*/
    content = {
        paddingValues ->
        //RatingWidget(modifier = Modifier.padding(top = paddingValues.calculateTopPadding()), rating = 3.1)
            paddingValues.calculateTopPadding()
            ListContent(
                    heroes = allHeroes,
                    navController = navController,
                    modifier = Modifier.padding(paddingValues)
                )

    }
    )
}