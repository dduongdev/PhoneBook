package com.dduongdev.phonebook.data

import kotlinx.coroutines.flow.Flow

class OfflineContactRepository(private val contactDao: ContactDao) : ContactRepository {
    override fun getAllAsStream(): Flow<List<Contact>> = contactDao.getAllAsStream()

    override fun getByIdAsStream(id: Int): Flow<Contact?> = contactDao.getByIdAsStream(id)

    override suspend fun save(contact: Contact) = contactDao.insert(contact)

    override suspend fun update(contact: Contact) = contactDao.update(contact)

    override suspend fun delete(contact: Contact) = contactDao.delete(contact)

}