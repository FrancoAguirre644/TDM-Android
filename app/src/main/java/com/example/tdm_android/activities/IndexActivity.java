package com.example.tdm_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.tdm_android.R;
import com.example.tdm_android.adapters.CharacterAdapter;
import com.example.tdm_android.models.Character;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        setupAdapter();
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