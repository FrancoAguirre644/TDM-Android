package com.example.tdm_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tdm_android.R;

public class CreateUserActivity extends AppCompatActivity {
    Button ntnCancel;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        ntnCancel = findViewById(R.id.cancel);
        btnRegister= findViewById(R.id.register);

        ntnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelRegistration(view);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });

    }

    public void cancelRegistration(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("message", "Cancel registration");
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