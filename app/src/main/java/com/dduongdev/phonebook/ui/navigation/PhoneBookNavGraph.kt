package com.dduongdev.phonebook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dduongdev.phonebook.ui.home.HomeDestination
import com.dduongdev.phonebook.ui.home.HomeScreen
import com.dduongdev.phonebook.ui.item.ContactEditDestination
import com.dduongdev.phonebook.ui.item.ContactEditScreen
import com.dduongdev.phonebook.ui.item.ContactEntryDestination
import com.dduongdev.phonebook.ui.item.ContactEntryScreen

@Composable
fun PhoneBookNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(
            route = HomeDestination.route
        ) {
            HomeScreen(
                navigateToContactEntry = { navController.navigate(ContactEntryDestination.route) },
                /**
                 * [it] đại diên cho đối số truyền vào, tức l nhận một contactId.
                 */
                navigateToContactEdit = { navController.navigate("${ContactEditDestination.route}/${it}") }
            )
        }

        composable(
            route = ContactEntryDestination.route
        ) {
            ContactEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(
            route = ContactEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ContactEditDestination.contactIdArg) {
                type = NavType.IntType
            })
        ) {
            ContactEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}