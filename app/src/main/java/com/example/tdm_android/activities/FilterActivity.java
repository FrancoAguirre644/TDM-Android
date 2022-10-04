package com.example.tdm_android.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.tdm_android.R;
import com.example.tdm_android.constants.Constants;
import com.google.android.material.navigation.NavigationView;

public class FilterActivity extends AppCompatActivity {

    String[] items =  {"5","10","25","50","100"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    Button btnSubmit;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

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
        setContentView(R.layout.activity_filter);

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
                        Intent intent = new Intent(FilterActivity.this, FilterActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_favourites:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent1 = new Intent(FilterActivity.this, FavouritesActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_profile:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent2 = new Intent(FilterActivity.this, ProfileActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_logout:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        logoutUser();
                        Intent intent3 = new Intent(FilterActivity.this, LoginActivity.class);
                        startActivity(intent3);
                        break;
                }

                return true;
            }
        });

        String strName = getIntent().getStringExtra("name_user");
        if(strName != null) { //Si se acaba de loguear
            Toast.makeText(this, "Welcome " + strName + "!!!", Toast.LENGTH_SHORT).show();
        }

        autoCompleteTxt = findViewById(R.id.auto_complete_txt);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        btnSubmit = findViewById(R.id.btn_filter);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FilterActivity.this, IndexActivity.class);
                startActivity(intent);
            }
        });

    }

    public void logoutUser(){

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = pref.edit();

        editor.putString(Constants.STR_USERNAME, "");
        editor.putString(Constants.STR_PASSWORD, "");
        editor.putBoolean(Constants.STR_CHECK_REMEMBER_USER, false);
        editor.apply();
    }

}