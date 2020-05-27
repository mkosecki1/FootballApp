package com.footballapp.ui.standings.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.footballapp.R
import com.footballapp.ext.addDot
import com.footballapp.ext.loadImage
import com.footballapp.model.StandingsModel.Standing.Table
import kotlinx.android.synthetic.main.standings_item_layout.view.*

class StandingsRecyclerViewAdapter(
    private var table: ArrayList<Table>,
    private val activity: Activity
) :
    RecyclerView.Adapter<StandingsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StandingsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.standings_item_layout, parent, false)
    )

    override fun getItemCount() = table.size

    override fun onBindViewHolder(holder: StandingsViewHolder, position: Int) {
        holder.bind(table[position], activity)
    }

    fun updateTable(newTable: List<Table>) {
        table.clear()
        table.addAll(newTable)
        notifyDataSetChanged()
    }
}

class StandingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val position = view.standingsItemPosition
    private val imageView = view.standingsItemImage
    private val team = view.standingsItemTeam
    private val played = view.standingsItemPlayed
    private val won = view.standingsItemWon
    private val draws = view.standingsItemDraws
    private val lost = view.standingsItemLost
    private val points = view.standingsItemPoints

    fun bind(table: Table, activity: Activity) {
        position.addDot(table.position.toString())
        imageView.loadImage(activity, table.team.crestUrl)
        team.text = table.team.name
        played.text = table.playedGames.toString()
        won.text = table.won.toString()
        draws.text = table.draw.toString()
        lost.text = table.lost.toString()
        points.text = table.points.toString()
    }
}