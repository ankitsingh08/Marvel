package com.example.marvel

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
//import androidx.compose.material3.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marvel.ui.theme.MarvelTheme
import com.example.marvel.view.CharacterBottomNav
import com.example.marvel.view.CharacterDetailScreen
import com.example.marvel.view.CollectionScreen
import com.example.marvel.view.LibraryScreen
import com.example.marvel.viewmodel.CollectionDbViewModel
import com.example.marvel.viewmodel.LibraryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val libraryViewModel by viewModels<LibraryViewModel>()
    private val collectionDbViewModel by viewModels<CollectionDbViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    CharacterScaffold(navController, libraryViewModel, collectionDbViewModel)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CharacterScaffold(navController: NavHostController, libraryViewModel: LibraryViewModel, collectionDbViewModel: CollectionDbViewModel) {
        //val scaffoldState = rememberScaffoldState()
        val context = LocalContext.current

        Scaffold(
            //scaffoldState = scaffoldState,
            bottomBar = { CharacterBottomNav(navController = navController)}
        ) { paddingValues ->
            NavHost(navController = navController, startDestination = Destination.Library.route) {
                composable(Destination.Library.route) {
                    LibraryScreen(navController = navController, libraryViewModel, paddingValues = paddingValues)
                }
                composable(Destination.Collection.route) {
                    CollectionScreen(collectionDbViewModel, navController)
                }
                composable(Destination.Character.route) { navBackStackEntry ->
                    val id = navBackStackEntry.arguments?.getString("characterId")?.toIntOrNull()

                    if (id == null) {
                        Toast.makeText(context, "Character id is required", Toast.LENGTH_SHORT).show()
                    } else {
                        libraryViewModel.retrieveSingleCharacter(id)
                        CharacterDetailScreen(
                            libraryViewModel = libraryViewModel,
                            collectionDbViewModel = collectionDbViewModel,
                            paddingValues = paddingValues,
                            navController = navController
                        )
                    }
                }
            }
        }

    }
}
