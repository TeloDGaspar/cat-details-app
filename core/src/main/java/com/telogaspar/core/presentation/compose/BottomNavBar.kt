package com.telogaspar.core.presentation.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.telogaspar.core.data.di.Route

@Composable
fun MyNavigationBar(selectedItem: String, onItemSelected: (String) -> Unit) {
    val items = listOf(
        Route.BREEDS to Icons.Filled.Home,
        Route.FAVORITE_BREEDS to Icons.Filled.Favorite,
    )

    NavigationBar(containerColor = Color.White) {
        items.forEach { (route, icon) ->
            NavigationBarItem(
                icon = { Icon(imageVector = icon, contentDescription = route) },
                label = { Text(text = route.replaceFirstChar { it.uppercase() }) },
                selected = selectedItem == route,
                onClick = { onItemSelected(route) }
            )
        }
    }
}
