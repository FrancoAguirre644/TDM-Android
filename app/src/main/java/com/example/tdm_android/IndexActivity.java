package com.example.tdm_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.tdm_android.adapters.CharacterAdapter;
import com.example.tdm_android.models.Character;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends AppCompatActivity {

    private RecyclerView rvCharacters;
    private CharacterAdapter charactersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        setupAdapter();
    }

    private void setupAdapter() {
        rvCharacters = findViewById(R.id.listRecyclerView);
        charactersAdapter = new CharacterAdapter(getCharacters());
        rvCharacters.setLayoutManager(new GridLayoutManager(this, 2));
        rvCharacters.setAdapter(charactersAdapter);
    }

    private List<Character> getCharacters() {
        List<Character> listUsers = new ArrayList<>();
        listUsers.add(new Character("Franco", "Buenos Aires"));
        listUsers.add(new Character("Sergio", "Buenos Aires"));
        listUsers.add(new Character("Franco", "Buenos Aires"));
        listUsers.add(new Character("Sergio", "Buenos Aires"));
        listUsers.add(new Character("Franco", "Buenos Aires"));
        listUsers.add(new Character("Sergio", "Buenos Aires"));
        listUsers.add(new Character("Franco", "Buenos Aires"));
        listUsers.add(new Character("Sergio", "Buenos Aires"));

        return listUsers;
    }
}