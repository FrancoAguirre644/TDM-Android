package com.example.tdm_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tdm_android.R;
import com.example.tdm_android.constants.Constants;
import com.example.tdm_android.managers.UserManager;
import com.example.tdm_android.models.User;

public class RegisterActivity extends AppCompatActivity {

    TextView tvLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister= findViewById(R.id.register);
        tvLogin= findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToLogin(view);
            }
        });

    }

    public void redirectToLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void register(View view){
        EditText etUsername = findViewById(R.id.txtNameUser);
        EditText etEmail = findViewById(R.id.txtEmail);
        EditText etPassword = findViewById(R.id.txtPassword);
        EditText etConfirmPassword = findViewById(R.id.txtConfirmPassword);
        if (etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty() ||
                etEmail.getText().toString().isEmpty() || etConfirmPassword.getText().toString().isEmpty()){
            Toast.makeText(this, Constants.MESSAGE_COMPLETE_ALL_FIELD, Toast.LENGTH_SHORT).show();
        }else {
            if ( !etPassword.getText().toString().equals(etConfirmPassword.getText().toString()) ) {
                Toast.makeText(this, Constants.MESSAGE_PASSWORD_CONFIRMATION_ERROR, Toast.LENGTH_SHORT).show();
            }else{
                if(isTheValueInTheFieldExists("username", etUsername.getText().toString())){
                    Toast.makeText(this, Constants.MESSAGE_USERNAME_ALREADY_REGISTERED, Toast.LENGTH_SHORT).show();
                } else if(isTheValueInTheFieldExists("email", etEmail.getText().toString())){
                    Toast.makeText(this, Constants.MESSAGE_EMAIL_ALREADY_REGISTERED, Toast.LENGTH_SHORT).show();
                } else {
                    createUser(etUsername.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("name_user", etUsername.getText().toString());
                    startActivity(intent);
                }
            }
        }
    }

    public void createUser(String strUsername, String strEmail, String strPassword) {
        try{
            UserManager.getInstance(RegisterActivity.this).createUser(new User(strUsername, strEmail, strPassword));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isTheValueInTheFieldExists(String nameField, String valueField){
        User user = null;
        try {
            user = UserManager.getInstance(RegisterActivity.this).getOneUserByField(nameField, valueField);
            if ( user != null) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}