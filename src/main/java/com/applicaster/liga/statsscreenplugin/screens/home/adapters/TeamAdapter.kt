package com.applicaster.liga.statsscreenplugin.screens.home.adapters

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.applicaster.liga.statsscreenplugin.R
import com.applicaster.liga.statsscreenplugin.data.model.GroupModel
import com.applicaster.liga.statsscreenplugin.utils.ModelUtils
import com.applicaster.liga.statsscreenplugin.utils.UrlType
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.liga_item_team_card.view.*

class TeamAdapter(private val items: List<GroupModel.Ranking>, val context: Context?, listener: OnTeamFlagClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onTeamFlagClickListener: OnTeamFlagClickListener = listener

    private var firstYellowRankStatus = items.indexOfLast { it.rankStatus == items[0].rankStatus } + 1
    private var lastYellowRankStatus = items.indexOfFirst { it.rankStatus == null } - 1
    private var firstRedRankStatus = items.indexOfLast { it.rankStatus == null } + 1

    interface OnTeamFlagClickListener {
        fun onTeamFlagClicked(teamId: String)
    }

    private var recyclerView: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> HeaderViewHolder(LayoutInflater.from(context).inflate(R.layout.liga_item_team_header_card, parent, false))
            else -> TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.liga_item_team_card, parent, false))
        }
    }

    override fun getItemCount(): Int = items.size + 1

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    private fun fillProfile(rank: GroupModel.Ranking, team: TextView, ivFlag: ImageView,
                            played: TextView, won: TextView, drawn: TextView, lost: TextView,
                            pts: TextView) {
        team.text = rank.contestantShortName
        played.text = rank.matchesPlayed.toString()
        won.text = rank.matchesWon.toString()
        drawn.text = rank.matchesDrawn.toString()
        lost.text = rank.matchesLost.toString()
        pts.text = rank.points.toString()

        ivFlag.setOnClickListener { onTeamFlagClickListener.onTeamFlagClicked(rank.contestantId) }
        rank.contestantId?.let { Picasso.get().load(ModelUtils.getImageUrl(UrlType.Flag, it)).placeholder(R.drawable.unknow_flag).into(ivFlag) }
        ivFlag.setOnClickListener { onTeamFlagClickListener.onTeamFlagClicked(rank.contestantId) }
    }

    override fun onBindViewHolder(holderGroup: RecyclerView.ViewHolder, position: Int) {
        if (position != 0 && holderGroup is TeamViewHolder) {
            val dataPosition = position - 1
            holderGroup.tvOrderNumber.text = (position).toString()
            val rank = items[dataPosition]
            fillProfile(rank,
                    holderGroup.tvTeamName,
                    holderGroup.ivFlag,
                    holderGroup.tvPoints1,
                    holderGroup.tvPoints2,
                    holderGroup.tvPoints3,
                    holderGroup.tvPoints4,
                    holderGroup.tvPoints5)

            val indicatorColor = getIndicatorColor(dataPosition)
            holderGroup.ivIndicator.setBackgroundColor(indicatorColor)
        }
    }

    private fun getIndicatorColor(position: Int): Int {
        return context?.let {
            when {
                position < firstYellowRankStatus -> ContextCompat.getColor(it, R.color.indicator_green)
                position in firstYellowRankStatus..lastYellowRankStatus -> ContextCompat.getColor(it, R.color.indicator_yellow)
                position >= firstRedRankStatus -> ContextCompat.getColor(it, R.color.indicator_red)
                else -> 0
            }
        } ?: 0
    }
}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var ivFlag = view.iv_flag
    var tvTeamName = view.tv_team_name

    var ivIndicator = view.iv_indicator
    var tvOrderNumber = view.tv_order_number

    var tvPoints1 = view.tv_points_1
    var tvPoints2 = view.tv_points_2
    var tvPoints3 = view.tv_points_3
    var tvPoints4 = view.tv_points_4
    var tvPoints5 = view.tv_points_5
}

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)