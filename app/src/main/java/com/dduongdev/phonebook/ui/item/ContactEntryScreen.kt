package com.dduongdev.phonebook.ui.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dduongdev.phonebook.PhoneBookTopAppBar
import com.dduongdev.phonebook.R
import com.dduongdev.phonebook.ui.AppViewModelProvider
import com.dduongdev.phonebook.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ContactEntryDestination : NavigationDestination {
    override val route: String = "contact_entry"
    override val titleRes: Int = R.string.contact_entry_title
}

@Composable
fun ContactEntryScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: ContactEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val contactEntries = viewModel.uiState.contactFormState

    Scaffold(
        modifier = modifier,
        topBar = {
            PhoneBookTopAppBar(
                title = stringResource(ContactEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        ) {
            OutlinedTextField(
                value = contactEntries.name,
                onValueChange = { viewModel.updateUiState(contactEntries.copy(name = it)) },
                label = { Text("Enter name") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = contactEntries.phoneNumber,
                onValueChange = { viewModel.updateUiState(contactEntries.copy(phoneNumber = it)) },
                label = { Text("Enter phone number") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Row {
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.saveContact()
                            navigateBack()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray
                    )
                ) {
                    Text(
                        text = stringResource(R.string.save_button)
                    )
                }
            }
        }
    }
}