package com.telogaspar.sports_sync_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.telogaspar.core.data.di.Route
import com.telogaspar.core.presentation.compose.MyNavigationBar
import com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose.BreedScreen
import com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose.breedDetail.BreedDetailScreen
import com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose.favoriteBreed.FavoriteBreedsScreen
import com.telogaspar.sports_sync_app.feature.sportsevent.presentation.viewmodel.BreedListViewModel
import com.telogaspar.sports_sync_app.ui.theme.SportssyncappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportssyncappTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: BreedListViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Route.BREEDS

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            MyNavigationBar(
                selectedItem = currentRoute,
                onItemSelected = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Route.BREEDS,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Route.BREEDS) {
                BreedScreen(
                    viewModel = viewModel,
                    onBreedClick = { breedId ->
                        navController.navigate("${Route.BREED_DETAIL}/$breedId")
                    }
                )
            }
            composable(Route.FAVORITE_BREEDS) {
                FavoriteBreedsScreen(viewModel = viewModel)
            }
            composable(
                route = "${Route.BREED_DETAIL}/{breedId}",
                arguments = listOf(
                    navArgument("breedId") {
                        type = NavType.StringType
                    }
                )
            ) {
                val breedId = it.arguments?.getString("breedId") ?: ""
                BreedDetailScreen(viewModel = viewModel, breedId = breedId)
            }
        }
    }
}
