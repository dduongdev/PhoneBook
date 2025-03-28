package com.dduongdev.phonebook.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getByIdAsStream(id: Int): Flow<Contact?>

    @Query("SELECT * FROM contacts ORDER BY name ASC")
    fun getAllAsStream(): Flow<List<Contact>>
}