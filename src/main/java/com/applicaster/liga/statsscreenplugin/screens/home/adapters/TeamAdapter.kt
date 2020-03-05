package com.applicaster.liga.statsscreenplugin.screens.home.adapters

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
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
    : RecyclerView.Adapter<TeamViewHolder>() {

    var onTeamFlagClickListener: OnTeamFlagClickListener = listener

    private var firstYellowRankStatus = items.indexOfLast { it.rankStatus == items[0].rankStatus } + 1
    private var lastYellowRankStatus = items.indexOfFirst { it.rankStatus == null } - 1
    private var firstRedRankStatus = items.indexOfLast { it.rankStatus == null } + 1

    interface OnTeamFlagClickListener {
        fun onTeamFlagClicked(teamId: String)
    }

    private var expandCollapseTransition: Transition? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.liga_item_team_card, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        expandCollapseTransition = TransitionInflater.from(recyclerView.context)
                .inflateTransition(R.transition.card_expand_toggle)
                .apply { duration = 300 }
    }

    private fun fillProfile(rank: GroupModel.Ranking, team: TextView, ivFlag: ImageView,
                            played: TextView, won: TextView, drawn: TextView, lost: TextView,
                            pts: TextView) {
        team.text = rank.contestantName
        played.text = rank.matchesPlayed.toString()
        won.text = rank.matchesWon.toString()
        drawn.text = rank.matchesDrawn.toString()
        lost.text = rank.matchesLost.toString()
        pts.text = rank.points.toString()

        ivFlag.setOnClickListener { onTeamFlagClickListener.onTeamFlagClicked(rank.contestantId) }
        rank.contestantId?.let { Picasso.get().load(ModelUtils.getImageUrl(UrlType.Flag, it)).placeholder(R.drawable.unknow_flag).into(ivFlag) }
        ivFlag.setOnClickListener { onTeamFlagClickListener.onTeamFlagClicked(rank.contestantId) }
    }


    override fun onBindViewHolder(holderGroup: TeamViewHolder, position: Int) {
        holderGroup.tvOrderNumber.text = (position + 1).toString()
        val rank = items[position]
        fillProfile(rank,
                holderGroup.tvTeamName,
                holderGroup.ivFlag,
                holderGroup.tvPoints1,
                holderGroup.tvPoints2,
                holderGroup.tvPoints3,
                holderGroup.tvPoints4,
                holderGroup.tvPoints5)

        val indicatorColor = getIndicatorColor(position)
        holderGroup.ivIndicator.setBackgroundColor(indicatorColor)
        holderGroup.ivArrow.setOnClickListener {
            // only for devices with Android 5.0 and above
            // come on! if you have a device with KitKat facebook will not work
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                toggleView(holderGroup)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun toggleView(holderGroup: TeamViewHolder) {
        if (holderGroup.rlSecondContainer.visibility == View.GONE) {
            holderGroup.ivArrow.animate().rotation(180f).start()

            recyclerView?.let {
                TransitionManager.beginDelayedTransition(it, expandCollapseTransition)
            }
            holderGroup.rlSecondContainer.visibility = View.VISIBLE
            holderGroup.vDivider.visibility = View.VISIBLE
        } else {
            holderGroup.ivArrow.animate().rotation(0f).start()

            recyclerView?.let {
                TransitionManager.beginDelayedTransition(it, expandCollapseTransition)
            }
            holderGroup.rlSecondContainer.visibility = View.GONE
            holderGroup.vDivider.visibility = View.GONE
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
    var ivArrow = view.iv_arrow
    var vDivider = view.vDivider
    var ivIndicator = view.iv_indicator
    var tvOrderNumber = view.tv_order_number

    var tvPoints1 = view.tv_points_1
    var tvPoints2 = view.tv_points_2
    var tvPoints3 = view.tv_points_3
    var tvPoints4 = view.tv_points_4
    var tvPoints5 = view.tv_points_5

    var cvContainer = view.cv_card
    var rlSecondContainer = view.rl_second_container

}