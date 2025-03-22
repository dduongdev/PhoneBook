package com.dduongdev.phonebook.data

import android.content.Context
import androidx.lifecycle.viewmodel.CreationExtras

interface AppContainer {
    val contactRepository : ContactRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val contactRepository : ContactRepository by lazy {
        InMemoryContactRepository()
    }
}
