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

        /**
         * Khi [NavHostController] điều hướng đến [ContactEditScreen], nó sẽ truyền contactId dưới dạng
         * arguments, và SavedStateHandle sẽ tự động lưu tữ giá trị này.
         */
        composable(
            route = ContactEditDestination.routeWithArgs,
            /**
             * Định nghĩa các tham số động mà Composable nhận được khi điều hướng.
             */
            arguments = listOf(navArgument(ContactEditDestination.contactIdArg) {
                type = NavType.IntType
            })
        ) {
            ContactEditScreen(
                navigateBack = { navController.popBackStack() },
                /**
                 * Nếu hệ thống sử dụng nested navigation, tự động tìm Composable là cha của Composable hiện tại trong hệ thống phân cấp để quay lại.
                 */
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}