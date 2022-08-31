package com.example.tdm_android.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdm_android.R;
import com.example.tdm_android.holders.CharacterViewHolder;
import com.example.tdm_android.models.Character;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterViewHolder> {

    private List<Character> listCharacters;

    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Character character);
    }

    public CharacterAdapter(List<Character> listCharacters, OnItemClickListener onItemClickListener) {
        this.listCharacters = listCharacters;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemCharacter = (View) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_element, parent, false);
        return new CharacterViewHolder(itemCharacter);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        holder.name.setText(listCharacters.get(position).getName());
        holder.city.setText(listCharacters.get(position).getCity());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(listCharacters.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCharacters.size();
    }

}