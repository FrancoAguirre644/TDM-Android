package com.example.tdm_android.activities

import androidx.recyclerview.widget.RecyclerView
import com.example.tdm_android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import android.content.Intent
import com.example.tdm_android.adapters.CharacterAdapter
import androidx.recyclerview.widget.GridLayoutManager
import android.view.MenuItem
import android.widget.*
import com.example.tdm_android.models.Character
import java.util.ArrayList

class IndexActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    var toolbar: Toolbar? = null
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(
            item
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
        setupAdapter()
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
                    val intent = Intent(this@IndexActivity, FilterActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_favourites -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent1 = Intent(this@IndexActivity, FavouritesActivity::class.java)
                    startActivity(intent1)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent2 = Intent(this@IndexActivity, ProfileActivity::class.java)
                    startActivity(intent2)
                }
                R.id.nav_profile -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent2 = Intent(this@IndexActivity, ProfileActivity::class.java)
                    startActivity(intent2)
                }
                R.id.nav_logout -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent3 = Intent(this@IndexActivity, LoginActivity::class.java)
                    startActivity(intent3)
                }
            }
            true
        })
    }

    private fun setupAdapter() {
        val rvCharacters = findViewById<RecyclerView>(R.id.listRecyclerView)
        val charactersAdapter = CharacterAdapter(characters) {
            val intent = Intent(this@IndexActivity, DetailActivity::class.java)
            startActivity(intent)
        }
        rvCharacters.layoutManager = GridLayoutManager(this, 2)
        rvCharacters.adapter = charactersAdapter
    }

    private val characters: List<Character>
        private get() {
            val listCharacters: MutableList<Character> = ArrayList()
            listCharacters.add(Character("Arya Stark", "Northmen"))
            listCharacters.add(Character("Arya Stark", "Northmen"))
            listCharacters.add(Character("Arya Stark", "Northmen"))
            listCharacters.add(Character("Arya Stark", "Northmen"))
            listCharacters.add(Character("Arya Stark", "Northmen"))
            listCharacters.add(Character("Arya Stark", "Northmen"))
            listCharacters.add(Character("Arya Stark", "Northmen"))
            listCharacters.add(Character("Arya Stark", "Northmen"))
            listCharacters.add(Character("Arya Stark", "Northmen"))
            listCharacters.add(Character("Arya Stark", "Northmen"))
            return listCharacters
        }
}