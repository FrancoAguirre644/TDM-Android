package com.example.tdm_android.activities

import android.annotation.SuppressLint
import com.example.tdm_android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.example.tdm_android.client.RetroFitClient
import com.example.tdm_android.constants.Constants
import com.example.tdm_android.functions.messageShort
import com.example.tdm_android.functions.triggerByChoosingNavigationMenuItem
import com.example.tdm_android.managers.FavouriteCharacterManager
import com.example.tdm_android.models.Character
import com.example.tdm_android.models.FavouriteCharacter
import com.example.tdm_android.services.GOTService
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*

class DetailActivity : AppCompatActivity() {

    lateinit var tvCharacterGender: TextView
    lateinit var tvCharacterName: TextView
    lateinit var cgtvSeries: ChipGroup
    lateinit var cgAliases: ChipGroup
    private lateinit var btnAddFavourite: Button

    lateinit var character: Character
    private var isFavourite: Boolean = false

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
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
        btnAddFavourite = findViewById(R.id.btnAddFavourite)
        isFavourite = existsInFavourites(intent.getStringExtra("id")?.toIntOrNull()!!)

        Log.e("THREAD", Thread.currentThread().name+" (Log.e on line 67, DetailActivity)")

        restApiConsumption()

        if(isFavourite) btnAddFavourite.text = "Remove from favorites"

        btnAddFavourite.setOnClickListener {
            if(isFavourite) {

                try {
                    FavouriteCharacterManager.getInstance(this@DetailActivity)!!
                        .deleteFavouriteCharacter(intent.getStringExtra("id")?.toIntOrNull()!!)
                    isFavourite = false
                    btnAddFavourite.text = "Add to favorites"
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } else {

                try {
                    FavouriteCharacterManager.getInstance(this@DetailActivity)!!.createFavouriteCharacter(
                        FavouriteCharacter(intent.getStringExtra("id")?.toIntOrNull()!!, character.name, character.gender)
                    )
                    isFavourite = true
                    btnAddFavourite.text = "Remove from favourites"
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

        triggerByChoosingNavigationMenuItem(lifecycleScope, navigationView, drawerLayout, Constants.STR_ORIGIN_DETAIL)
    }

    private fun restApiConsumption() {
        lifecycleScope.launch(Dispatchers.IO) {

            Log.e("THREAD", Thread.currentThread().name+" (Log.e on line 71, DetailActivity)")

            val idCharacter = intent.getStringExtra("id")!!

            val apiGOT = RetroFitClient.retrofit.create(GOTService::class.java)

            apiGOT.getCharacter(idCharacter).enqueue(object : Callback<Character> {
                override fun onResponse(call: Call<Character>, response: Response<Character>) {
                    character = response.body() as Character

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

                }

                override fun onFailure(call: Call<Character>, t: Throwable) {
                    Log.e("Error: ", t.message ?: " ")
                }

            })

        }
    }

    private fun existsInFavourites(id: Int): Boolean {
        val favouriteCharacter: FavouriteCharacter?
        var isExistValue = false
        try {
            favouriteCharacter = FavouriteCharacterManager.getInstance(this@DetailActivity)!!.getById(id)
            if (favouriteCharacter != null) {
                isExistValue = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return isExistValue
    }

}