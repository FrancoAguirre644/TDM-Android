package com.example.tdm_android.activities

import android.app.Dialog
import com.example.tdm_android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.tdm_android.constants.Constants
import com.example.tdm_android.functions.messageShort
import com.example.tdm_android.managers.UserManager
import com.example.tdm_android.models.User
import java.lang.Exception
import com.example.tdm_android.functions.restApiYesNoConsumptionLogin
import com.example.tdm_android.notifications.ServiceNotifications

class LoginActivity : AppCompatActivity() {

    private lateinit var tvCreateUser: TextView
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var checkRememberUser: CheckBox
    private lateinit var imagePrincipal : ImageView

    private lateinit var pref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val strName = intent.getStringExtra(Constants.STR_KEY_USERNAME)
        if (strName != null) { //Si se acaba de registrar
            messageShort(Constants.REGISTERED_SUCCESSFULLY_MESSAGE + strName)
        }

        initializeVariables()

        if ( pref.getBoolean(Constants.STR_CHECK_REMEMBER_USER, false) ) { //Si había elegido recordar usuario
            redirectToFilterActivity()
        } else{
            val origin = intent.getStringExtra("origin")
            if (origin == null){ //Solo cuando ingresa
                restApiYesNoConsumptionLogin(lifecycleScope, imagePrincipal, true)
            }
        }
        tvCreateUser.setOnClickListener { redirectToCreateUser() }
        btnLogin.setOnClickListener { login() }
    }

    private fun redirectToCreateUser() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun login() {
        val strUsername = etUsername.text.toString()
        val strPassword = etPassword.text.toString()
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
            saveDataUser(checkRememberUser.isChecked)
            redirectToFilterActivity()
        } else {
            tvDialogInfo.text = Constants.INVALID_CREDENTIALS_MESSAGE
            dialog.show()
        }
    }

    private fun redirectToFilterActivity() {
        val intent = Intent(this, FilterActivity::class.java)
        intent.putExtra(Constants.STR_KEY_USERNAME, etUsername.text.toString())
        startActivity(intent)
    }

    private fun isTheCredentialsAreValid(strUsername: String?, strPassword: String): Boolean {
        try {
            val user: User? = UserManager.getInstance(this@LoginActivity)!!
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
        imagePrincipal = findViewById(R.id.image_principal)
    }

    private fun saveDataUser(isRememberUser: Boolean) {
        editor.putString(Constants.STR_USERNAME, etUsername.text.toString())
        editor.putString(Constants.STR_PASSWORD, etPassword.text.toString())
        editor.putBoolean(Constants.STR_CHECK_REMEMBER_USER, isRememberUser)
        editor.apply()
        createNotification()
    }

    private fun createNotification(){
        val intent = Intent(this, ServiceNotifications::class.java)
        ContextCompat.startForegroundService(this, intent)
    }

}