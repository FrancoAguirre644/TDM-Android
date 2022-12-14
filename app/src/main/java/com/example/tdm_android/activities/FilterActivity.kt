package com.example.tdm_android.activities

import com.example.tdm_android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle
import android.content.Intent
import android.view.MenuItem
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.example.tdm_android.functions.messageShort
import com.example.tdm_android.functions.triggerByChoosingNavigationMenuItem

class FilterActivity : AppCompatActivity() {

    private var items = arrayOf("5", "10", "25", "50", "100")
    private var pageSize: String = ""

    private lateinit var autoCompleteTxt: AutoCompleteTextView
    private var adapterItems: ArrayAdapter<String>? = null
    private lateinit var btnSubmit: Button

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        triggerByChoosingNavigationMenuItem(lifecycleScope, navigationView, drawerLayout)

        val strName = intent.getStringExtra("name_user")

        if (strName != null) { // Si se acaba de loguear
            messageShort("Welcome $strName!")
        }

        autoCompleteTxt = findViewById(R.id.auto_complete_txt)
        adapterItems = ArrayAdapter(this, R.layout.list_item, items)
        autoCompleteTxt.setAdapter(adapterItems)
        btnSubmit = findViewById(R.id.btn_filter)

        autoCompleteTxt.setOnItemClickListener { parent, _, position, _ ->
            pageSize = parent.getItemAtPosition(position).toString()
        }

        btnSubmit.setOnClickListener {
            Intent(this@FilterActivity, IndexActivity::class.java).also {
                it.putExtra("pageSize", pageSize)
                startActivity(it)
            }
        }
    }

}