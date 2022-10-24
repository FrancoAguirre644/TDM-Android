package com.example.tdm_android.models

import com.j256.ormlite.table.DatabaseTable
import com.j256.ormlite.field.DatabaseField

@DatabaseTable(tableName = "FavouriteCharacters")
class FavouriteCharacter {

    @DatabaseField(id = true)
    var id: Int? = null

    @DatabaseField(unique = true)
    var name: String? = null

    @DatabaseField(unique = true)
    var gender: String? = null

    constructor() {}

    constructor(id: Int?, name: String?, gender: String?) {
        this.id = id
        this.name = name
        this.gender = gender
    }

}