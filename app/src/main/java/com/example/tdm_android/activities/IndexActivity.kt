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
import android.util.Log
import com.example.tdm_android.adapters.CharacterAdapter
import androidx.recyclerview.widget.GridLayoutManager
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.example.tdm_android.client.RetroFitClient
import com.example.tdm_android.models.Character
import com.example.tdm_android.services.GOTService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class IndexActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
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
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener { item ->
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
        }

    }

    private fun setupAdapter() {
        lifecycleScope.launch {

            Log.e("THREAD", Thread.currentThread().name+" (Log.e on line 86, IndexActivity)")

            withContext(Dispatchers.IO){
                val api = RetroFitClient.retrofit.create(GOTService::class.java)

                Log.e("THREAD", Thread.currentThread().name+" (Log.e on line 91, IndexActivity)")

                val pageSize = intent.getStringExtra("pageSize")!!

                api.getCharacters(pageSize, "2").enqueue(object : retrofit2.Callback<List<Character>> {
                    override fun onResponse(call: Call<List<Character>>, response: Response<List<Character>>) {
                        val characters = response.body() as List<Character>

                        val rvCharacters = findViewById<RecyclerView>(R.id.listRecyclerView)

                        val charactersAdapter = CharacterAdapter(characters) { character ->
                            Intent(this@IndexActivity, DetailActivity::class.java).also {
                                it.putExtra("id", character.url?.filter { it -> it.isDigit() })
                                startActivity(it)
                            }
                        }

                        rvCharacters.layoutManager = GridLayoutManager(applicationContext, 2)
                        rvCharacters.adapter = charactersAdapter

                    }

                    override fun onFailure(call: Call<List<Character>>, t: Throwable) {
                        Log.e("Error: ", t.message ?: " ")
                    }

                })
            }
        }
    }

}