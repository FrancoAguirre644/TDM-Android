package com.example.tdm_android.constants

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

object Constants {
    const val DB_NAME = "DB_TDM"
    const val DB_VERSION = 1
    const val COMPLETE_ALL_FIELD_MESSAGE = "You must complete all fields"
    const val PASSWORD_CONFIRMATION_ERROR_MESSAGE = "Password and confirmation must match"
    const val EMAIL_ALREADY_REGISTERED_MESSAGE =
        "The email you are trying to enter is already registered"
    const val USERNAME_ALREADY_REGISTERED_MESSAGE =
        "The username you are trying to enter is already registered"
    const val STR_KEY_USERNAME = "key_username"
    const val INVALID_CREDENTIALS_MESSAGE = "Invalid credentials"
    const val REGISTERED_SUCCESSFULLY_MESSAGE = "Registered successfully, please login "
    const val STR_USERNAME = "USERNAME"
    const val STR_PASSWORD = "PASSWORD"
    const val STR_CHECK_REMEMBER_USER = "CHECK_REMEMBER_USER"
    const val STR_EMPTY = ""
}