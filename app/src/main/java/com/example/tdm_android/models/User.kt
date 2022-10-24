package com.example.tdm_android.models

import com.j256.ormlite.table.DatabaseTable
import com.j256.ormlite.field.DatabaseField

@DatabaseTable(tableName = "Users")
class User {
    @DatabaseField(id = true)
    val id: Int? = null

    @DatabaseField(unique = true)
    var username: String? = null

    @DatabaseField(unique = true)
    var email: String? = null

    @DatabaseField
    var password: String? = null

    constructor() {}

    constructor(username: String?, email: String?, password: String?) {
        this.username = username
        this.email = email
        this.password = password
    }

}