package com.example.tdm_android.activities

import androidx.recyclerview.widget.RecyclerView
import com.example.tdm_android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle
import android.content.Intent
import com.example.tdm_android.adapters.CharacterAdapter
import androidx.recyclerview.widget.GridLayoutManager
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.example.tdm_android.functions.triggerByChoosingNavigationMenuItem
import com.example.tdm_android.models.Character
import java.util.ArrayList

class FavouritesActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        triggerByChoosingNavigationMenuItem(lifecycleScope, navigationView, drawerLayout)

        setupAdapter()
    }

    private fun setupAdapter() {
        val rvCharacters = findViewById<RecyclerView>(R.id.listRecyclerView)
        val charactersAdapter = CharacterAdapter(characters) { character: Character? ->
            val intent = Intent(this@FavouritesActivity, DetailActivity::class.java)
            startActivity(intent)
        }
        rvCharacters.layoutManager = GridLayoutManager(this, 2)
        rvCharacters.adapter = charactersAdapter
    }

    private val characters: List<Character>
        get() {
            val listCharacters: MutableList<Character> = ArrayList()
            listCharacters.add(Character("Arya Stark", "Northmen"))
            listCharacters.add(Character("Arya Stark", "Northmen"))
            listCharacters.add(Character("Arya Stark", "Northmen"))
            listCharacters.add(Character("Arya Stark", "Northmen"))
            return listCharacters
        }
}