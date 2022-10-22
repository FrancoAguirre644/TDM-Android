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
import androidx.lifecycle.lifecycleScope
import com.example.tdm_android.client.RetroFitClient
import com.example.tdm_android.constants.Constants
import com.example.tdm_android.functions.triggerByChoosingNavigationMenuItem
import com.example.tdm_android.models.Answer
import com.example.tdm_android.models.Character
import com.example.tdm_android.services.GOTService
import com.example.tdm_android.services.YesNoService
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetailActivity : AppCompatActivity() {

    lateinit var tvCharacterGender: TextView
    lateinit var tvCharacterName: TextView
    lateinit var ivImagen: ImageView
    lateinit var cgtvSeries: ChipGroup
    lateinit var cgAliases: ChipGroup

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

        tvCharacterGender = findViewById(R.id.character_gender)
        tvCharacterName = findViewById(R.id.character_name)
        cgtvSeries = findViewById(R.id.cgTvSeries)
        cgAliases = findViewById(R.id.cgAliases)

        Log.e("THREAD", Thread.currentThread().name+" (Log.e on line 67, DetailActivity)")

        restApiConsumption()

        triggerByChoosingNavigationMenuItem(lifecycleScope, navigationView, drawerLayout, Constants.STR_ORIGIN_DETAIL)
    }

    private fun restApiConsumption() {
        lifecycleScope.launch(Dispatchers.IO) {

            Log.e("THREAD", Thread.currentThread().name+" (Log.e on line 77, DetailActivity)")

            val idCharacter = intent.getStringExtra("id")!!

            val apiGOT = RetroFitClient.retrofit.create(GOTService::class.java)

            apiGOT.getCharacter(idCharacter).enqueue(object : Callback<Character> {
                override fun onResponse(call: Call<Character>, response: Response<Character>) {
                    val character = response.body() as Character

                    tvCharacterGender.text = character.gender
                    tvCharacterName.text = character.name + " - " + character.culture

                    character.tvSeries.forEach { tvSerie ->
                        if (tvSerie != "") {
                            val chip = Chip(this@DetailActivity)
                            chip.text = tvSerie
                            cgtvSeries.addView(chip)
                        }
                    }

                    character.aliases.forEach { alias ->
                        if (alias != "") {
                            val chip = Chip(this@DetailActivity)
                            chip.text = alias
                            cgAliases.addView(chip)
                        }
                    }

                    Toast.makeText(this@DetailActivity, "Its a toast! $character", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Character>, t: Throwable) {
                    Log.e("Error: ", t.message ?: " ")
                }

            })

        }
    }

}