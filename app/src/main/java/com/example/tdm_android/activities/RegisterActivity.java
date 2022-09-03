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
            Toast.makeText(this, "You must complete all fields", Toast.LENGTH_SHORT).show();
        }else {
            if ( !etPassword.getText().toString().equals(etConfirmPassword.getText().toString()) ) {
                Toast.makeText(this, "Password and confirmation must match", Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("name_user", etUsername.getText().toString());
                startActivity(intent);
            }
        }
    }
}