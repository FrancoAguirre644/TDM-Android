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

class RegisterActivity : AppCompatActivity() {
    var tvLogin: TextView? = null
    var btnRegister: Button? = null
    var etUsername: EditText? = null
    var etEmail: EditText? = null
    var etPassword: EditText? = null
    var etConfirmPassword: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initializeVariables()
        btnRegister!!.setOnClickListener { view -> register(view) }
        tvLogin!!.setOnClickListener { view -> redirectToLogin(view, null, false) }
    }

    fun redirectToLogin(view: View?, strUsername: String?, sendUsername: Boolean) {
        val intent = Intent(this, LoginActivity::class.java)
        if (sendUsername) {
            intent.putExtra(Constants.STR_KEY_USERNAME, strUsername)
        }
        startActivity(intent)
    }

    fun register(view: View?) {
        val strUsername: String
        val strPassword: String
        val strEmail: String
        strUsername = etUsername!!.text.toString()
        strPassword = etPassword!!.text.toString()
        strEmail = etEmail!!.text.toString()
        if (isFieldsAreCorrect_or_returnsFalseAndMessage(strUsername, strPassword, strEmail)) {
            createUser(strUsername, strEmail, strPassword)
            redirectToLogin(view, strUsername, true)
        }
    }

    fun isFieldsAreCorrect_or_returnsFalseAndMessage(
        strUsername: String,
        strPassword: String,
        strEmail: String
    ): Boolean {
        val dialog = Dialog(this@RegisterActivity)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.setCancelable(false)
        val tvDialogInfo = dialog.findViewById<View>(R.id.tvDialogInfo) as TextView
        val btnDialogOk = dialog.findViewById<View>(R.id.btnDialogOk) as Button
        btnDialogOk.setOnClickListener { dialog.dismiss() }
        var isFieldsCorrect = false
        if (strUsername.isEmpty() || strPassword.isEmpty() || strEmail.isEmpty() || etConfirmPassword!!.text.toString()
                .isEmpty()
        ) {
            tvDialogInfo.text = Constants.COMPLETE_ALL_FIELD_MESSAGE
            dialog.show()
        } else {
            if (strPassword != etConfirmPassword!!.text.toString()) {
                tvDialogInfo.text = Constants.PASSWORD_CONFIRMATION_ERROR_MESSAGE
                dialog.show()
            } else {
                if (isTheValueInTheFieldExists("username", strUsername)) {
                    tvDialogInfo.text = Constants.USERNAME_ALREADY_REGISTERED_MESSAGE
                    dialog.show()
                } else if (isTheValueInTheFieldExists("email", strEmail)) {
                    tvDialogInfo.text = Constants.EMAIL_ALREADY_REGISTERED_MESSAGE
                    dialog.show()
                } else {
                    isFieldsCorrect = true
                }
            }
        }
        return isFieldsCorrect
    }

    fun createUser(strUsername: String?, strEmail: String?, strPassword: String?) {
        try {
            UserManager.Companion.getInstance(this@RegisterActivity)!!
                .createUser(User(strUsername, strEmail, strPassword))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isTheValueInTheFieldExists(nameField: String?, valueField: String?): Boolean {
        var user: User? = null
        var isExistValue = false
        try {
            user = UserManager.Companion.getInstance(this@RegisterActivity)!!
                .getOneUserByField(nameField, valueField)
            if (user != null) {
                isExistValue = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return isExistValue
    }

    private fun initializeVariables() {
        btnRegister = findViewById(R.id.register)
        tvLogin = findViewById(R.id.tvLogin)
        etUsername = findViewById(R.id.txtNameUser)
        etEmail = findViewById(R.id.txtEmail)
        etPassword = findViewById(R.id.txtPassword)
        etConfirmPassword = findViewById(R.id.txtConfirmPassword)
    }
}