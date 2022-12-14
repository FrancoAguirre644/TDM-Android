package com.example.tdm_android.activities

import androidx.recyclerview.widget.RecyclerView
import com.example.tdm_android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle
import android.content.Intent
import android.util.Log
import com.example.tdm_android.adapters.CharacterAdapter
import androidx.recyclerview.widget.GridLayoutManager
import android.view.MenuItem
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.tdm_android.client.RetroFitClient
import com.example.tdm_android.constants.Constants
import com.example.tdm_android.functions.triggerByChoosingNavigationMenuItem
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
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var tvDescriptionScreenIndex:  TextView

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
        tvDescriptionScreenIndex = findViewById(R.id.tvDescriptionScreenIndex)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        tvDescriptionScreenIndex.text = "List of characters - " + intent.getStringExtra("pageSize")!!

        triggerByChoosingNavigationMenuItem(lifecycleScope, navigationView, drawerLayout)

    }

    private fun setupAdapter() {
        lifecycleScope.launch {

            Log.e("THREAD", Thread.currentThread().name+" (Log.e on line 60, IndexActivity)")

            withContext(Dispatchers.IO){
                val api = RetroFitClient.retrofit.create(GOTService::class.java)

                Log.e("THREAD", Thread.currentThread().name+" (Log.e on line 65, IndexActivity)")

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