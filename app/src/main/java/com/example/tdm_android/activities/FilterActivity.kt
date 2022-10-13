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

class FilterActivity : AppCompatActivity() {
    var items = arrayOf("5", "10", "25", "50", "100")
    lateinit var autoCompleteTxt: AutoCompleteTextView
    var adapterItems: ArrayAdapter<String>? = null
    lateinit var btnSubmit: Button
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    var toolbar: Toolbar? = null
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
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
                    val intent = Intent(this@FilterActivity, FilterActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_favourites -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent1 = Intent(this@FilterActivity, FavouritesActivity::class.java)
                    startActivity(intent1)
                }
                R.id.nav_profile -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent2 = Intent(this@FilterActivity, ProfileActivity::class.java)
                    startActivity(intent2)
                }
                R.id.nav_logout -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    logoutUser()
                    val intent3 = Intent(this@FilterActivity, LoginActivity::class.java)
                    startActivity(intent3)
                }
            }
            true
        })
        val strName = intent.getStringExtra("name_user")
        if (strName != null) { //Si se acaba de loguear
            Toast.makeText(this, "Welcome $strName!!!", Toast.LENGTH_SHORT).show()
        }
        autoCompleteTxt = findViewById(R.id.auto_complete_txt)
        adapterItems = ArrayAdapter(this, R.layout.list_item, items)
        autoCompleteTxt.setAdapter(adapterItems)
        btnSubmit = findViewById(R.id.btn_filter)
        autoCompleteTxt.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position).toString()
            Toast.makeText(applicationContext, "Item: $item", Toast.LENGTH_SHORT).show()
        })
        btnSubmit.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@FilterActivity, IndexActivity::class.java)
            startActivity(intent)
        })
    }

    fun logoutUser() {
        pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        editor = pref.edit()
        editor.putString(Constants.STR_USERNAME, "")
        editor.putString(Constants.STR_PASSWORD, "")
        editor.putBoolean(Constants.STR_CHECK_REMEMBER_USER, false)
        editor.apply()
    }
}