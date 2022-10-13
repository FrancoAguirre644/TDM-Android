package com.example.tdm_android.activities

import android.app.Dialog
import com.j256.ormlite.table.DatabaseTable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import androidx.recyclerview.widget.RecyclerView
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
import android.content.SharedPreferences
import com.example.tdm_android.activities.RegisterActivity
import android.preference.PreferenceManager
import android.view.View
import android.widget.*
import com.example.tdm_android.activities.IndexActivity
import com.example.tdm_android.constants.Constants
import com.example.tdm_android.managers.UserManager
import com.example.tdm_android.models.User
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    var tvCreateUser: TextView? = null
    var etUsername: EditText? = null
    var etPassword: EditText? = null
    var btnLogin: Button? = null
    var checkRememberUser: CheckBox? = null
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val strName = intent.getStringExtra(Constants.STR_KEY_USERNAME)
        if (strName != null) { //Si se acaba de registrar
            Toast.makeText(
                this,
                Constants.REGISTERED_SUCCESSFULLY_MESSAGE + strName,
                Toast.LENGTH_SHORT
            ).show()
        }
        initializeVariables()
        if (pref!!.getBoolean(
                Constants.STR_CHECK_REMEMBER_USER,
                false
            )
        ) { //Si había elegido recordar usuario
            redirectToFilterActivity()
        }
        tvCreateUser!!.setOnClickListener { view -> redirectToCreateUser(view) }
        btnLogin!!.setOnClickListener { view -> login(view) }
    }

    fun redirectToCreateUser(view: View?) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun login(view: View?) {
        val strUsername = etUsername!!.text.toString()
        val strPassword = etPassword!!.text.toString()
        val dialog = Dialog(this@LoginActivity)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.setCancelable(false)
        val tvDialogInfo = dialog.findViewById<View>(R.id.tvDialogInfo) as TextView
        val btnDialogOk = dialog.findViewById<View>(R.id.btnDialogOk) as Button
        btnDialogOk.setOnClickListener { dialog.dismiss() }
        if (strUsername.isEmpty() || strPassword.isEmpty()) {
            tvDialogInfo.text = Constants.COMPLETE_ALL_FIELD_MESSAGE
            dialog.show()
        } else if (isTheCredentialsAreValid(strUsername, strPassword)) {
            saveDataUser(checkRememberUser!!.isChecked)
            redirectToFilterActivity()
        } else {
            tvDialogInfo.text = Constants.INVALID_CREDENTIALS_MESSAGE
            dialog.show()
        }
    }

    fun redirectToFilterActivity() {
        val intent = Intent(this, FilterActivity::class.java)
        intent.putExtra(Constants.STR_KEY_USERNAME, etUsername!!.text.toString())
        startActivity(intent)
    }

    fun isTheCredentialsAreValid(strUsername: String?, strPassword: String): Boolean {
        try {
            val user: User? = UserManager.Companion.getInstance(this@LoginActivity)!!
                .getOneUserByField("username", strUsername)
            if (user != null && user.password == strPassword) {
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    private fun initializeVariables() {
        tvCreateUser = findViewById(R.id.createUser)
        btnLogin = findViewById(R.id.login)
        etUsername = findViewById(R.id.txtNameUser)
        etPassword = findViewById(R.id.txtPassword)
        checkRememberUser = findViewById(R.id.checkRememberUser)
        pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        editor = pref.edit()
    }

    fun saveDataUser(isRememberUser: Boolean) {
        editor!!.putString(Constants.STR_USERNAME, etUsername!!.text.toString())
        editor!!.putString(Constants.STR_PASSWORD, etPassword!!.text.toString())
        editor!!.putBoolean(Constants.STR_CHECK_REMEMBER_USER, isRememberUser)
        editor!!.apply()
    }
}