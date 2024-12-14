package ie.tus.libloans.navbar

import ie.tus.libloans.navigation.Screen
import ie.tus.libloans.navigation.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ie.tus.libloans.R


@Composable
fun MainPageNavigationBar(
    navController: NavHostController
) {
    val icons = mapOf(
        Screen.WelcomeScreen to NavBarIcon(
            filledIcon = Icons.Filled.Book,
            outlinedIcon = Icons.Outlined.Book,
            label = stringResource(id = R.string.welcome)
        ),
        Screen.LoginScreen to NavBarIcon(
            filledIcon = Icons.Filled.Notifications,
            outlinedIcon = Icons.Outlined.Notifications,
            label = stringResource(id = R.string.login)
        ),

        Screen.RegisterScreen to NavBarIcon(filledIcon = Icons.Filled.Notifications,
        outlinedIcon = Icons.Outlined.Notifications,
        label = stringResource(id = R.string.register)
    )
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        screens.forEach { screen ->
            val isSelected = currentDestination?.route == screen.route

            val label = icons[screen]!!.label

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route)
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected)
                            icons[screen]!!.filledIcon
                        else
                            icons[screen]!!.outlinedIcon,
                        contentDescription = label
                    )
                },
                label = {
                    Text(label)
                }
            )
        }
    }
}