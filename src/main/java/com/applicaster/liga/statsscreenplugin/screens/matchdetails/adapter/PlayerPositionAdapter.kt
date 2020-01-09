package com.applicaster.liga.statsscreenplugin.screens.matchdetails.adapter

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.applicaster.liga.statsscreenplugin.R
import kotlinx.android.synthetic.main.item_player.view.*

class PlayerPositionAdapter(private val items: List<PlayerPosition>?, val context: Context?)
    : RecyclerView.Adapter<PositionViewHolder>() {

    var resources: Resources? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        resources = context?.resources
        return PositionViewHolder(LayoutInflater.from(context).inflate(R.layout.item_player, parent,
                false))
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {
        items?.let {
            val item = it[position]

            holder.tvShirtNumber.text = item.shirtNumber

            resources?.let {
                // if is right team assign correspondent colors and circles
                val textColor = if (item.isRightTeam) it.getColor(R.color.white) else
                    it.getColor(R.color.black)
                val drawable = if (item.isRightTeam) R.drawable.gray_circle else R.drawable.white_circle

                holder.tvShirtNumber.setTextColor(textColor)
                holder.ivBackground.setImageResource(drawable)
            }
        }
    }

}

class PositionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var tvShirtNumber: TextView = view.tv_player_number
    var ivBackground: ImageView = view.iv_player_background
}

data class PlayerPosition(var shirtNumber: String, var isRightTeam: Boolean)