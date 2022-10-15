package com.example.tdm_android.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.tdm_android.R
import com.example.tdm_android.holders.CharacterViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.tdm_android.models.Character

class CharacterAdapter(
    private val listCharacters: List<Character>,
    var onItemClickListener: (Character) -> Unit
) : RecyclerView.Adapter<CharacterViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(character: Character)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemCharacter = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_element, parent, false) as View
        return CharacterViewHolder(itemCharacter)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.name.text = listCharacters[position].name
        holder.culture.text = listCharacters[position].gender

        holder.itemView.setOnClickListener {
            onItemClickListener.invoke(listCharacters[position])
        }
    }

    override fun getItemCount(): Int {
        return listCharacters.size
    }
}
