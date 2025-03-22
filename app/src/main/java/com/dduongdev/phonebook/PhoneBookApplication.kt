package com.dduongdev.phonebook

import android.app.Application
import com.dduongdev.phonebook.data.AppContainer
import com.dduongdev.phonebook.data.AppDataContainer

class PhoneBookApplication : Application() {
    /**
     * [lateinit] cho biết biến sẽ được khởi tạo sau chứ không phải ngay khi khai báo.
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}