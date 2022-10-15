package com.example.tdm_android.holders

import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.example.tdm_android.R
import android.view.View

class CharacterViewHolder(itemUser: View?) : RecyclerView.ViewHolder(
    itemUser!!
) {
    var name: TextView = itemView.findViewById(R.id.nameTextView)
    var culture: TextView = itemView.findViewById(R.id.cultureTextView)

}