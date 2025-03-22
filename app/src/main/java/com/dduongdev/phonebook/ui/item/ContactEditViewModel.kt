package com.dduongdev.phonebook.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dduongdev.phonebook.data.ContactRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ContactEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val contactRepository: ContactRepository
) : ViewModel() {
    var uiState by mutableStateOf(ContactEditUiState())
        private set
    var isLoadError by mutableStateOf(false)
        private set

    private val contactId: Int = checkNotNull(savedStateHandle[ContactEditDestination.contactIdArg])

    /**
     * [let] thực thi một khối lệnh trên một đối tượng không null.
     */
    private suspend fun loadContact() {
        contactRepository.getByIdAsStream(contactId).collectLatest { contact ->
            contact?.let {
                uiState = uiState.copy(contactFormState = it.toContactFromState())
            } ?: run {
                isLoadError = true
            }
        }
    }

    fun updateUiState(contactFormState: ContactFormState) {
        uiState = ContactEditUiState(
            contactFormState = contactFormState,
            isEntryValid = validateInput(contactFormState)
        )
    }

    private fun validateInput(contactFormState: ContactFormState = uiState.contactFormState): Boolean {
        return with(contactFormState) {
            name.isNotBlank() && phoneNumber.isNotBlank()
        }
    }

    suspend fun updateContact() {
        if (validateInput()) {
            contactRepository.update(uiState.contactFormState.toContact())
        }
    }

    init {
        viewModelScope.launch {
            loadContact()
        }
    }
}

data class ContactEditUiState(
    val contactFormState: ContactFormState = ContactFormState(),
    val isEntryValid: Boolean = false
)

