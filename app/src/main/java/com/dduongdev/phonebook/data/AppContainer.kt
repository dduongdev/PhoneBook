package com.dduongdev.phonebook.data

import android.content.Context

interface AppContainer {
    val contactRepository : ContactRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val contactRepository : ContactRepository by lazy {
        OfflineContactRepository(PhoneBookDatabase.getDatabase(context).contactDao())
    }
}
