package com.dduongdev.phonebook.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dduongdev.phonebook.data.Contact
import com.dduongdev.phonebook.data.ContactRepository

class ContactEntryViewModel(
    private val contactRepository: ContactRepository
) : ViewModel() {
    var uiState by mutableStateOf(ContactEntryUiState())
        private set

    fun updateUiState(contactFormState: ContactFormState) {
        uiState = ContactEntryUiState(
            contactFormState = contactFormState,
            isEntryValid = validateInput(contactFormState)
        )
    }

    private fun validateInput(contactFormState: ContactFormState = uiState.contactFormState): Boolean {
        return with(contactFormState) {
            name.isNotBlank() && phoneNumber.isNotBlank()
        }
    }

    /**
     * Từ khóa [suspend] được sử dụng để đánh dấu một hàm là hàm bất đồng bộ.
     */
    suspend fun saveContact() {
        if (validateInput()) {
            contactRepository.save(uiState.contactFormState.toContact())
        }
    }
}

data class ContactEntryUiState(
    val contactFormState: ContactFormState = ContactFormState(),
    val isEntryValid: Boolean = false
)