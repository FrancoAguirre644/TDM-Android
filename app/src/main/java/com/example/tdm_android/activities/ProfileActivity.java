package com.example.tdm_android.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.tdm_android.R;
import com.example.tdm_android.constants.Constants;
import com.example.tdm_android.managers.UserManager;
import com.example.tdm_android.models.User;
import com.google.android.material.navigation.NavigationView;

public class ProfileActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    TextView tvUsername;
    TextView tvEmail;
    EditText etUsername;
    EditText etEmail;
    EditText etFullname;
    Button btnUpdateProfile;

    User userDetails;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initializeVariables();

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser(userDetails);
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent = new Intent(ProfileActivity.this, FilterActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_favourites:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent1 = new Intent(ProfileActivity.this, FavouritesActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_profile:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent2 = new Intent(ProfileActivity.this, ProfileActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_logout:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent3 = new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivity(intent3);
                        break;
                }

                return true;
            }
        });

    }

    private void initializeVariables() {

        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etFullname = (EditText) findViewById(R.id.etUsername);
        btnUpdateProfile = (Button) findViewById(R.id.btnUpdateProfile);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = pref.edit();

        userDetails = getUserDetails(this.pref.getString(Constants.STR_USERNAME, Constants.STR_USERNAME));

        tvUsername.setText(userDetails.getUsername());
        tvEmail.setText(userDetails.getEmail());
        etUsername.setText(userDetails.getUsername());
        etEmail.setText(userDetails.getEmail());
        etFullname.setText(userDetails.getUsername());

    }

    public User getUserDetails(String strUsername){
        try {
            User user = UserManager.getInstance(ProfileActivity.this).getOneUserByField("username", strUsername);
            return user;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(User user){

        userDetails.setUsername(String.valueOf(etUsername.getText()));
        tvUsername.setText(String.valueOf(etUsername.getText()));
        editor.putString(Constants.STR_USERNAME, etUsername.getText().toString());

        try {
            UserManager.getInstance(ProfileActivity.this).updateUser(user);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}