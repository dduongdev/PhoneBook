package com.dduongdev.phonebook.data

import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun getAllAsStream(): Flow<List<Contact>>
    fun getByIdAsStream(id: Int): Flow<Contact?>
    fun save(contact: Contact)
    fun update(contact: Contact)
    fun delete(contact: Contact)
}