package com.dduongdev.phonebook.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class PhoneBookDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var Instance: PhoneBookDatabase? = null

        /**
         * [context] xác định đường dẫn của tệp cơ sở dữ liệu trên thiết bị.
         * Một số thao tác với database yêu cầu quyền truy cập vào hệ thống tệp tin, điều này được thực hiện thông qua [context].
         */
        fun getDatabase(context: Context): PhoneBookDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, PhoneBookDatabase::class.java, "contact_database")
                    .fallbackToDestructiveMigration().build().also { Instance = it }
            }
        }
    }
}