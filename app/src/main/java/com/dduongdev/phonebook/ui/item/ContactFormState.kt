package com.dduongdev.phonebook.ui.item

import com.dduongdev.phonebook.data.Contact

data class ContactFormState(
    val id: Int = 0,
    val name: String = "",
    val phoneNumber: String = ""
)

fun ContactFormState.toContact(): Contact = Contact(
    id = id,
    name = name,
    phoneNumber = phoneNumber
)

fun Contact.toContactFromState(): ContactFormState = ContactFormState(
    id = id,
    name = name,
    phoneNumber = phoneNumber
)