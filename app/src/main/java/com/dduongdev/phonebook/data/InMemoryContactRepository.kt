package com.dduongdev.phonebook.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class InMemoryContactRepository : ContactRepository {
    private val _contacts = MutableStateFlow(
        listOf(
            Contact(1, "Nguyễn Văn A", "0123456789"),
            Contact(2, "Trần Thị B", "0987654321"),
            Contact(3, "Lê Văn C", "0912345678")
        )
    )

    override fun getAllAsStream(): Flow<List<Contact>> = _contacts.asStateFlow()

    override fun getByIdAsStream(id: Int): Flow<Contact?> {
        return _contacts.map { contacts -> contacts.find { it.id == id } }
    }

    override fun save(contact: Contact) {
        _contacts.value = _contacts.value + contact
    }

    override fun update(contact: Contact) {
        _contacts.value = _contacts.value.map {
            if (it.id == contact.id) contact else it
        }
    }

    override fun delete(contact: Contact) {
        _contacts.value = _contacts.value.filterNot { it.id == contact.id }
    }
}
