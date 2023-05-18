package com.example.marvel.view

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.marvel.Destination
import com.example.marvel.R

@Composable
fun CharacterBottomNav(navController: NavHostController) {
    NavigationBar(tonalElevation = 5.dp) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination

        val iconLibrary = painterResource(id = R.drawable.ic_library)
        val itemCollection = painterResource(id = R.drawable.ic_collections)

        NavigationBarItem(
            selected = currentDestination?.route == Destination.Library.route,
            onClick = { navController.navigate(Destination.Library.route) {
                popUpTo(Destination.Library.route)
                launchSingleTop = true
            } },
            icon = { Icon(painter = iconLibrary, contentDescription = null)},
            label = { Text(text = Destination.Library.route)}
        )

        NavigationBarItem(
            selected = currentDestination?.route == Destination.Collection.route,
            onClick = { navController.navigate(Destination.Collection.route) {
                popUpTo(Destination.Collection.route)
                launchSingleTop = true
            } },
            icon = { Icon(painter = itemCollection, contentDescription = null)},
            label = { Text(text = Destination.Collection.route)}
        )
    }
}