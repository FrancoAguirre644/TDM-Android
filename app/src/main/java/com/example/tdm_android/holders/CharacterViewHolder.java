package com.example.tdm_android.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tdm_android.R;

public class CharacterViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView city;

    public CharacterViewHolder(View itemUser) {
        super(itemUser);
        name = itemView.findViewById(R.id.nameTextView);
        city = itemView.findViewById(R.id.cityTextView);
    }

}