package com.example.tdm_android.models

import com.j256.ormlite.table.DatabaseTable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.example.tdm_android.R
import com.example.tdm_android.holders.CharacterViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import com.j256.ormlite.dao.Dao
import kotlin.Throws
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.example.tdm_android.helpers.DBHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import android.content.Intent
import com.example.tdm_android.activities.FilterActivity
import com.example.tdm_android.activities.FavouritesActivity
import com.example.tdm_android.activities.ProfileActivity
import com.example.tdm_android.activities.LoginActivity
import com.example.tdm_android.adapters.CharacterAdapter
import com.example.tdm_android.activities.DetailActivity
import androidx.recyclerview.widget.GridLayoutManager
import android.widget.EditText
import android.widget.CheckBox
import android.content.SharedPreferences
import android.widget.Toast
import com.example.tdm_android.activities.RegisterActivity
import android.preference.PreferenceManager
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import android.widget.AdapterView
import com.example.tdm_android.activities.IndexActivity
import java.util.*

class Character {
    var name: String? = null
    var city: String? = null
    var gender: String? = null
    var culture: String? = null
    var born: String? = null
    var died: String? = null
    //var titles: Array<String>
    //var aliases: Array<String>
    var father: String? = null
    var mother: String? = null
    var spouse: String? = null
    //var allegiances: Array<String>
    //var books: Array<String>
    //var povBooks: Array<String>
    //var tvSeries: Array<String>
    //var playedBy: Array<String>

    constructor() {}
    constructor(name: String?, city: String?) {
        this.name = name
        this.city = city
    }

    constructor(
        name: String?,
        city: String?,
        gender: String?,
        culture: String?,
        born: String?,
        died: String?,
        //titles: Array<String>,
        //aliases: Array<String>,
        father: String?,
        mother: String?,
        spouse: String?, /*
        allegiances: Array<String>,
        books: Array<String>,
        povBooks: Array<String>,
        tvSeries: Array<String>,
        playedBy: Array<String> */
    ) {
        this.name = name
        this.city = city
        this.gender = gender
        this.culture = culture
        this.born = born
        this.died = died /*
        this.titles = titles
        this.aliases = aliases */
        this.father = father
        this.mother = mother
        this.spouse = spouse /*
        this.allegiances = allegiances
        this.books = books
        this.povBooks = povBooks
        this.tvSeries = tvSeries
        this.playedBy = playedBy */
    }

    override fun toString(): String {
        return "Character{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", gender='" + gender + '\'' +
                ", culture='" + culture + '\'' +
                ", born='" + born + '\'' +
                ", died='" + died + '\'' +
                ", father='" + father + '\'' +
                ", mother='" + mother + '\'' +
                ", spouse='" + spouse + '\'' +
                '}'
    }
}