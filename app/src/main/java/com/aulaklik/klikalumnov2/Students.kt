package com.aulaklik.klikalumnov2
import android.content.Context
import android.util.Log

class Students {
    private var name: String = ""
    private var id: Int = 0
    private var email: String = ""

    constructor(name: String, id: Int, email: String?) {
        this.id = id
        this.name = name
        this.email = email ?: ""
    }

    constructor() {

    }

    fun getName(): String {
        return name
    }

    fun getId(): Int {
        return id
    }

    fun getEmail(): String {
        return email
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun setEmail(email: String) {
        this.email = email
    }
}