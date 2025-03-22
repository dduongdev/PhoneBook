package com.dduongdev.phonebook.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dduongdev.phonebook.data.Contact
import com.dduongdev.phonebook.data.ContactRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(private val contactRepository: ContactRepository) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState(listOf()))
        private set

    init {
        loadContacts()
    }

    private fun loadContacts() {
        viewModelScope.launch {
            contactRepository.getAllAsStream().collectLatest { contacts ->
                uiState = uiState.copy(contacts = contacts)
            }
        }
    }

    suspend fun deleteContact(contact: Contact) {
        contactRepository.delete(contact)
    }
}

data class HomeUiState(
    val contacts: List<Contact>
)