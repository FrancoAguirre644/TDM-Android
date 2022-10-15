package com.example.tdm_android.activities

import com.example.tdm_android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import android.content.Intent
import android.util.Log
import android.view.MenuItem
import android.widget.*
import com.example.tdm_android.client.RetroFitClient
import com.example.tdm_android.models.Character
import com.example.tdm_android.services.GOTService
import retrofit2.Call
import retrofit2.Response
import java.util.*

class DetailActivity : AppCompatActivity() {

    lateinit var tvCharacterGender: TextView
    lateinit var tvCharacterName: TextView

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
        setContentView(R.layout.activity_detail)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)

        tvCharacterGender = findViewById<TextView>(R.id.character_gender)
        tvCharacterName = findViewById<TextView>(R.id.character_name)

        val id = intent.getStringExtra("id")!!

        Toast.makeText(this@DetailActivity, "Its a toast! $id", Toast.LENGTH_SHORT).show()

        val api = RetroFitClient.retrofit.create(GOTService::class.java)

        api.getCharacter("583").enqueue(object : retrofit2.Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                val character = response.body() as Character
                tvCharacterGender.text = character.gender
                tvCharacterName.text = character.name
                Toast.makeText(this@DetailActivity, "Its a toast! $character", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                Log.e("Error: ", t.message ?: " ")
            }

        })

        navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_home -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent = Intent(this@DetailActivity, FilterActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_favourites -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent1 = Intent(this@DetailActivity, FavouritesActivity::class.java)
                    startActivity(intent1)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent2 = Intent(this@DetailActivity, ProfileActivity::class.java)
                    startActivity(intent2)
                }
                R.id.nav_profile -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent2 = Intent(this@DetailActivity, ProfileActivity::class.java)
                    startActivity(intent2)
                }
                R.id.nav_logout -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent3 = Intent(this@DetailActivity, LoginActivity::class.java)
                    startActivity(intent3)
                }
            }
            true
        }
    }
}