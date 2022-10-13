package com.example.tdm_android.adapters

import com.j256.ormlite.table.DatabaseTable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
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
import android.widget.EditText
import android.widget.CheckBox
import android.content.SharedPreferences
import android.widget.Toast
import com.example.tdm_android.activities.RegisterActivity
import android.preference.PreferenceManager
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import android.widget.AdapterView
import com.example.tdm_android.activities.IndexActivity
import com.example.tdm_android.models.Character

class CharacterAdapter(
    private val listCharacters: List<Character>,
    var onItemClickListener: (Character?) -> Unit
) : RecyclerView.Adapter<CharacterViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(character: Character?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemCharacter = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_element, parent, false) as View
        return CharacterViewHolder(itemCharacter)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.name.text = listCharacters[position].name
        holder.city.text = listCharacters[position].city /*
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(
                listCharacters[holder.adapterPosition]
            )
        } */
    }

    override fun getItemCount(): Int {
        return listCharacters.size
    }
}