package com.example.tdm_android.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.tdm_android.R;
import com.google.android.material.navigation.NavigationView;

public class LoginActivity extends AppCompatActivity {

    TextView tvCreateUser;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String strName = getIntent().getStringExtra("name_user");
        if(strName != null) { //Si se acaba de registrar
            Toast.makeText(this, "Registered successfully " + strName + ", please login!!!", Toast.LENGTH_SHORT).show();
        }

        tvCreateUser = findViewById(R.id.createUser);
        btnLogin = findViewById(R.id.logIn);

        tvCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToCreateUser(view);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn(view);
            }
        });

    }

    public void redirectToCreateUser(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void logIn(View view){
        EditText etUsername = findViewById(R.id.txtNameUser);
        EditText etPassword = findViewById(R.id.txtPassword);
        String strName = etUsername.getText().toString();
        String strPassword = etPassword.getText().toString();
        if ( strName.isEmpty() || strPassword.isEmpty()){
            Toast.makeText(this, "You must complete all fields", Toast.LENGTH_SHORT).show();
        }else if( strPassword.equals("1234") && (strName.equals("Sergio") || strName.equals("Franco")) ){
            Intent intent = new Intent(this, FilterActivity.class);
            intent.putExtra("name_user", etUsername.getText().toString());
            startActivity(intent);
        }else{
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }
}