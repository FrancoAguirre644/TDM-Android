package com.example.tdm_android.activities

import android.annotation.SuppressLint
import com.example.tdm_android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.example.tdm_android.constants.Constants
import com.example.tdm_android.functions.messageShort
import com.example.tdm_android.functions.triggerByChoosingNavigationMenuItem
import com.example.tdm_android.managers.UserManager
import com.example.tdm_android.models.User
import java.lang.Exception

class ProfileActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    lateinit var tvUsername: TextView
    lateinit var tvEmail: TextView
    lateinit var etUsername: EditText
    lateinit var etEmail: EditText
    lateinit var etFullname: EditText
    lateinit var btnUpdateProfile: Button
    lateinit var btnLogout: Button

    lateinit var userDetails: User
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initializeVariables()

        btnUpdateProfile.setOnClickListener { updateUser(userDetails) }
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        triggerByChoosingNavigationMenuItem(lifecycleScope, navigationView, drawerLayout)

        btnLogout.setOnClickListener {
            logoutUser()
            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    @SuppressLint("CutPasteId")
    private fun initializeVariables() {
        tvUsername = findViewById<View>(R.id.tvUsername) as TextView
        tvEmail = findViewById<View>(R.id.tvEmail) as TextView
        etUsername = findViewById<View>(R.id.etUsername) as EditText
        etEmail = findViewById<View>(R.id.etEmail) as EditText
        etFullname = findViewById<View>(R.id.etUsername) as EditText
        btnUpdateProfile = findViewById<View>(R.id.btnUpdateProfile) as Button
        btnLogout = findViewById<View>(R.id.btnLogout) as Button
        pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        editor = pref.edit()
        userDetails = getUserDetails(pref.getString(Constants.STR_USERNAME, Constants.STR_USERNAME))!!
        tvUsername.text = userDetails.username
        tvEmail.text = userDetails.email
        etUsername.setText(userDetails.username)
        etEmail.setText(userDetails.email)
        etFullname.setText(userDetails.username)
    }

    private fun getUserDetails(strUsername: String?): User? {
        try {
            return UserManager.getInstance(this@ProfileActivity)!!
                .getOneUserByField("username", strUsername)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun updateUser(user: User?) {

        userDetails.username = (etUsername.text.toString())

        messageShort(etUsername.text.toString())
        tvUsername.text = etUsername.text.toString()
        editor.putString(Constants.STR_USERNAME, etUsername.text.toString())
        editor.apply()

        try {
            UserManager.getInstance(this@ProfileActivity)!!
                .updateUser(user)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun logoutUser() {
        pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        editor = pref.edit()
        editor.putString(Constants.STR_USERNAME, "")
        editor.putString(Constants.STR_PASSWORD, "")
        editor.putBoolean(Constants.STR_CHECK_REMEMBER_USER, false)
        editor.apply()
    }

}