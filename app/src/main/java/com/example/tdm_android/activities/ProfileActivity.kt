package com.example.tdm_android.activities

import com.j256.ormlite.table.DatabaseTable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import androidx.recyclerview.widget.RecyclerView
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
import android.content.SharedPreferences
import com.example.tdm_android.activities.RegisterActivity
import android.preference.PreferenceManager
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.tdm_android.activities.IndexActivity
import com.example.tdm_android.constants.Constants
import com.example.tdm_android.managers.UserManager
import com.example.tdm_android.models.User
import java.lang.Exception

class ProfileActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    var toolbar: Toolbar? = null
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    lateinit var tvUsername: TextView
    lateinit var tvEmail: TextView
    lateinit var etUsername: EditText
    lateinit var etEmail: EditText
    lateinit var etFullname: EditText
    lateinit var btnUpdateProfile: Button
    lateinit var userDetails: User
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initializeVariables()
        btnUpdateProfile!!.setOnClickListener { updateUser(userDetails) }
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent = Intent(this@ProfileActivity, FilterActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_favourites -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent1 = Intent(this@ProfileActivity, FavouritesActivity::class.java)
                    startActivity(intent1)
                }
                R.id.nav_profile -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent2 = Intent(this@ProfileActivity, ProfileActivity::class.java)
                    startActivity(intent2)
                }
                R.id.nav_logout -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent3 = Intent(this@ProfileActivity, LoginActivity::class.java)
                    startActivity(intent3)
                }
            }
            true
        })
    }

    private fun initializeVariables() {
        tvUsername = findViewById<View>(R.id.tvUsername) as TextView
        tvEmail = findViewById<View>(R.id.tvEmail) as TextView
        etUsername = findViewById<View>(R.id.etUsername) as EditText
        etEmail = findViewById<View>(R.id.etEmail) as EditText
        etFullname = findViewById<View>(R.id.etUsername) as EditText
        btnUpdateProfile = findViewById<View>(R.id.btnUpdateProfile) as Button
        pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        editor = pref.edit()
        userDetails = getUserDetails(pref.getString(Constants.STR_USERNAME, Constants.STR_USERNAME))!! /*
        tvUsername.setText(userDetails.getUsername())
        tvEmail.setText(userDetails.getEmail())
        etUsername.setText(userDetails.getUsername())
        etEmail.setText(userDetails.getEmail())
        etFullname.setText(userDetails.getUsername()) */
    }

    fun getUserDetails(strUsername: String?): User? {
        try {
            return UserManager.Companion.getInstance(this@ProfileActivity)!!
                .getOneUserByField("username", strUsername)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun updateUser(user: User?) {
        //userDetails.setUsername(etUsername!!.text.toString())
        tvUsername!!.text = etUsername!!.text.toString()
        editor!!.putString(Constants.STR_USERNAME, etUsername!!.text.toString())
        try {
            UserManager.Companion.getInstance(this@ProfileActivity)!!
                .updateUser(user)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}