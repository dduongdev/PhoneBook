package com.dduongdev.phonebook

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dduongdev.phonebook.ui.navigation.PhoneBookNavHost

@Composable
fun PhoneBookApp(navController: NavHostController = rememberNavController()) {
    PhoneBookNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneBookTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(title) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = navigateUp
                ) {
                    Icon(Icons.Default.Close, contentDescription = stringResource(R.string.back_button))
                }
            }
        }
    )
}