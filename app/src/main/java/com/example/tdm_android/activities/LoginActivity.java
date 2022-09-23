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
import com.example.tdm_android.constants.Constants;
import com.example.tdm_android.managers.UserManager;
import com.example.tdm_android.models.User;
import com.google.android.material.navigation.NavigationView;

public class LoginActivity extends AppCompatActivity {

    TextView tvCreateUser;
    EditText etUsername, etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String strName = getIntent().getStringExtra(Constants.STR_KEY_USERNAME);
        if(strName != null) { //Si se acaba de registrar
            Toast.makeText(this, Constants.REGISTERED_SUCCESSFULLY_MESSAGE + strName, Toast.LENGTH_SHORT).show();
        }
        
        initializeVariables();

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
        String strUsername = etUsername.getText().toString();
        String strPassword = etPassword.getText().toString();
        if ( strUsername.isEmpty() || strPassword.isEmpty()){
            Toast.makeText(this, Constants.COMPLETE_ALL_FIELD_MESSAGE, Toast.LENGTH_SHORT).show();
        }else if( isTheCredentialsAreValid(strUsername, strPassword) ){
            Intent intent = new Intent(this, FilterActivity.class);
            intent.putExtra(Constants.STR_KEY_USERNAME, etUsername.getText().toString());
            startActivity(intent);
        }else{
            Toast.makeText(this, Constants.INVALID_CREDENTIALS_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isTheCredentialsAreValid(String strUsername, String strPassword){
        try {
            User user = UserManager.getInstance(LoginActivity.this).getOneUserByField("username", strUsername);
            if (user != null && user.getPassword().equals(strPassword)) {return true;}
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void initializeVariables() {
        tvCreateUser = findViewById(R.id.createUser);
        btnLogin = findViewById(R.id.logIn);
        etUsername = findViewById(R.id.txtNameUser);
        etPassword = findViewById(R.id.txtPassword);
    }

}