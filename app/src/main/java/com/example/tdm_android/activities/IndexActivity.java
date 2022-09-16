package com.example.tdm_android.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.example.tdm_android.R;
import com.example.tdm_android.adapters.CharacterAdapter;
import com.example.tdm_android.models.Character;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

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
        setContentView(R.layout.activity_index);

        setupAdapter();

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
                        break;
                }

                return true;
            }
        });

    }

    private void setupAdapter() {
        RecyclerView rvCharacters = findViewById(R.id.listRecyclerView);

        CharacterAdapter charactersAdapter = new CharacterAdapter(getCharacters(), new CharacterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Character character) {
                Intent intent = new Intent(IndexActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });

        rvCharacters.setLayoutManager(new GridLayoutManager(this, 2));
        rvCharacters.setAdapter(charactersAdapter);
    }

    private List<Character> getCharacters() {
        List<Character> listCharacters = new ArrayList<>();
        listCharacters.add(new Character("Arya Stark", "Northmen"));
        listCharacters.add(new Character("Arya Stark", "Northmen"));
        listCharacters.add(new Character("Arya Stark", "Northmen"));
        listCharacters.add(new Character("Arya Stark", "Northmen"));
        listCharacters.add(new Character("Arya Stark", "Northmen"));
        listCharacters.add(new Character("Arya Stark", "Northmen"));
        listCharacters.add(new Character("Arya Stark", "Northmen"));
        listCharacters.add(new Character("Arya Stark", "Northmen"));
        listCharacters.add(new Character("Arya Stark", "Northmen"));
        listCharacters.add(new Character("Arya Stark", "Northmen"));

        return listCharacters;
    }
}