package com.dduongdev.phonebook.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dduongdev.phonebook.PhoneBookApplication
import com.dduongdev.phonebook.ui.home.HomeViewModel
import com.dduongdev.phonebook.ui.item.ContactEditViewModel
import com.dduongdev.phonebook.ui.item.ContactEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(phoneBookApplication().container.contactRepository)
        }
        initializer {
            ContactEntryViewModel(
                phoneBookApplication().container.contactRepository
            )
        }
        initializer {
            ContactEditViewModel(
                this.createSavedStateHandle(),
                phoneBookApplication().container.contactRepository
            )
        }
    }
}

/**
 * [CreationExtras] là một map chứa thông tin bổ sung dùng để khởi tạo Model.
 * Khi một ViewModel cần Application, nó sẽ tìm trong [CreationExtras] với key mặc định.
 */
fun CreationExtras.phoneBookApplication(): PhoneBookApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PhoneBookApplication)