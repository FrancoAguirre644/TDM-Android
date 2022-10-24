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
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.tdm_android.functions.triggerByChoosingNavigationMenuItem
import com.example.tdm_android.managers.FavouriteCharacterManager
import com.example.tdm_android.models.Character

class FavouritesActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var tvDescriptionScreenFavourites: TextView

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
        tvDescriptionScreenFavourites = findViewById(R.id.tvDescriptionScreenFavourites)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        triggerByChoosingNavigationMenuItem(lifecycleScope, navigationView, drawerLayout)

        setupAdapter()
    }

    private fun setupAdapter() {
        val rvCharacters = findViewById<RecyclerView>(R.id.listRecyclerView)

        val listCharacters: MutableList<Character> = ArrayList()

        FavouriteCharacterManager.getInstance(this@FavouritesActivity)?.favouriteCharacters?.forEach { favouriteCharacter ->
            if (favouriteCharacter != null) {
                listCharacters.add(Character(favouriteCharacter.name, favouriteCharacter.gender, url = favouriteCharacter.id.toString()))
            }
        }

        if(listCharacters.size === 0) tvDescriptionScreenFavourites.text = "Favourites - Empty"

        val charactersAdapter = CharacterAdapter(listCharacters) { character: Character? ->
            Intent(this@FavouritesActivity, DetailActivity::class.java).also {
                if (character != null) {
                    it.putExtra("id", character.url?.filter { it -> it.isDigit() })
                }
                startActivity(it)
            }
        }
        rvCharacters.layoutManager = GridLayoutManager(this, 2)
        rvCharacters.adapter = charactersAdapter
    }

}