package com.example.tdm_android.models

data class Character(
    var name: String? = null,
    var city: String? = null,
    var gender: String? = null,
    var culture: String? = null,
    var born: String? = null,
    var died: String? = null,
    var father: String? = null,
    var mother: String? = null,
    var spouse: String? = null,
    var url: String? = null,
    var aliases: Array<String> = emptyArray<String>(),
    var tvSeries: Array<String> = emptyArray<String>(),
    var titles:Array<String> = emptyArray<String>(),
    var allegiances: Array<String> = emptyArray<String>(),
    var books: Array<String> = emptyArray<String>(),
    var povBooks: Array<String> = emptyArray<String>(),
    var playedBy: Array<String> = emptyArray<String>(),
)