package com.dduongdev.phonebook.data

import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun getAllAsStream(): Flow<List<Contact>>
    fun getByIdAsStream(id: Int): Flow<Contact?>
    suspend fun save(contact: Contact)
    suspend fun update(contact: Contact)
    suspend fun delete(contact: Contact)
}