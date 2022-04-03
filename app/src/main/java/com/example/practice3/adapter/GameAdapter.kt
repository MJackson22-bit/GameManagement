package com.example.practice3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practice3.model.Game
import com.example.practice3.R

class GameAdapter(
    private val gameList: MutableList<Game>,
    private val onClickListener: (Game) -> Unit
) :
    RecyclerView.Adapter<GameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GameViewHolder(layoutInflater.inflate(R.layout.item_game, parent, false))
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val item = gameList[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int {
        return gameList.size
    }
}