package com.example.tdm_android.activities

import android.app.Dialog
import com.example.tdm_android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.*
import com.example.tdm_android.constants.Constants
import com.example.tdm_android.managers.UserManager
import com.example.tdm_android.models.User
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {

    lateinit var tvLogin: TextView
    lateinit var btnRegister: Button
    lateinit var etUsername: EditText
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initializeVariables()
        btnRegister.setOnClickListener { register() }
        tvLogin.setOnClickListener { redirectToLogin(null, false) }
    }

    private fun redirectToLogin(strUsername: String?, sendUsername: Boolean) {
        val intent = Intent(this, LoginActivity::class.java)
        if (sendUsername) {
            intent.putExtra(Constants.STR_KEY_USERNAME, strUsername)
        }
        startActivity(intent)
    }

    private fun register() {
        val strUsername: String = etUsername.text.toString()
        val strPassword: String = etPassword.text.toString()
        val strEmail: String = etEmail.text.toString()
        if (isFieldsAreCorrect_or_returnsFalseAndMessage(strUsername, strPassword, strEmail)) {
            createUser(strUsername, strEmail, strPassword)
            redirectToLogin(strUsername, true)
        }
    }

    private fun isFieldsAreCorrect_or_returnsFalseAndMessage(
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
        if (strUsername.isEmpty() || strPassword.isEmpty() || strEmail.isEmpty() || etConfirmPassword.text.toString()
                .isEmpty()
        ) {
            tvDialogInfo.text = Constants.COMPLETE_ALL_FIELD_MESSAGE
            dialog.show()
        } else {
            if (strPassword != etConfirmPassword.text.toString()) {
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

    private fun createUser(strUsername: String?, strEmail: String?, strPassword: String?) {
        try {
            UserManager.getInstance(this@RegisterActivity)!!
                .createUser(User(strUsername, strEmail, strPassword))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isTheValueInTheFieldExists(nameField: String?, valueField: String?): Boolean {
        val user: User?
        var isExistValue = false
        try {
            user = UserManager.getInstance(this@RegisterActivity)!!
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