package com.applicaster.liga.statsscreenplugin.screens.home.adapters

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.widget.NestedScrollView
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
import kotlinx.android.synthetic.main.item_group_card.view.*

class GroupAdapter(val items: List<GroupModel.Division>, val context: Context?,
                   val nestedScrollView: NestedScrollView, val listener: OnTeamFlagClickListener)
    : RecyclerView.Adapter<GroupViewHolder>() {

    var onTeamFlagClickListener: OnTeamFlagClickListener = listener

    interface OnTeamFlagClickListener {
        fun onTeamFlagClicked(teamId: String)
    }

    private var expandCollapseTransition: Transition? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(LayoutInflater.from(context).inflate(R.layout.item_group_card, parent, false))
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


    override fun onBindViewHolder(holderGroup: GroupViewHolder, position: Int) {
        holderGroup.tvGroupName.text = items[position].groupName.toUpperCase()
        for (rank in items[position].ranking) {
            when (rank.rank) {
                1 -> {
                    fillProfile(rank, holderGroup.tvName1, holderGroup.tvPoints1, holderGroup.ivTeam1,
                            holderGroup.tvPlayed1, holderGroup.tvWon1, holderGroup.tvLost1, holderGroup.tvDrawn1,
                            holderGroup.tvGF1, holderGroup.tvGC1, holderGroup.tvPL1, holderGroup.tvPTS1,
                            holderGroup.ivFlag1)
                }

                2 -> {
                    fillProfile(rank, holderGroup.tvName2, holderGroup.tvPoints2, holderGroup.ivTeam2,
                            holderGroup.tvPlayed2, holderGroup.tvWon2, holderGroup.tvLost2, holderGroup.tvDrawn2,
                            holderGroup.tvGF2, holderGroup.tvGC2, holderGroup.tvPL2, holderGroup.tvPTS2,
                            holderGroup.ivFlag2)
                }

                3 -> {
                    fillProfile(rank, holderGroup.tvName3, holderGroup.tvPoints3, holderGroup.ivTeam3,
                            holderGroup.tvPlayed3, holderGroup.tvWon3, holderGroup.tvLost3, holderGroup.tvDrawn3,
                            holderGroup.tvGF3, holderGroup.tvGC3, holderGroup.tvPL3, holderGroup.tvPTS3,
                            holderGroup.ivFlag3)
                }

                4 -> {
                    fillProfile(rank, holderGroup.tvName4, holderGroup.tvPoints4, holderGroup.ivTeam4,
                            holderGroup.tvPlayed4, holderGroup.tvWon4, holderGroup.tvLost4, holderGroup.tvDrawn4,
                            holderGroup.tvGF4, holderGroup.tvGC4, holderGroup.tvPL4, holderGroup.tvPTS4,
                            holderGroup.ivFlag4)
                }
            }
        }

        holderGroup.ivArrow.setOnClickListener {
            // only for devices with Android 5.0 and above
            // come on! if you have a device with KitKat facebook will not work
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                toggleView(holderGroup)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun toggleView(holderGroup: GroupViewHolder) {
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

    fun fillProfile(rank: GroupModel.Ranking, name: TextView, points: TextView, ivMiniFlag: ImageView,
                    played: TextView, won: TextView, lost: TextView, drawn: TextView,
                    gf: TextView, gc: TextView, plusLess: TextView, pts: TextView,
                    ivFlag: ImageView) {
        name.text = rank.contestantCode
        points.text = rank.points.toString()
        played.text = rank.matchesPlayed.toString()
        won.text = rank.matchesWon.toString()
        lost.text = rank.matchesLost.toString()
        drawn.text = rank.matchesDrawn.toString()
        gf.text = rank.goalsFor.toString()
        gc.text = rank.goalsAgainst.toString()
        plusLess.text = rank.goaldifference
        pts.text = rank.points.toString()

        ivMiniFlag.setImageResource(ModelUtils.getImageResource(ivMiniFlag, String.format("flag_%s", rank.contestantId)))
        ivMiniFlag.setOnClickListener {
            onTeamFlagClickListener.onTeamFlagClicked(rank.contestantId)
        }

        ivFlag.setImageResource(ModelUtils.getImageResource(ivFlag, String.format("flag_%s", rank.contestantId)))
        ivFlag.setOnClickListener {
            onTeamFlagClickListener.onTeamFlagClicked(rank.contestantId)
        }
    }
}

class GroupViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var tvGroupName = view.tv_group_name
    var ivFlag1 = view.iv_flag_1
    var tvName1 = view.tv_name_1
    var tvPoints1 = view.tv_points_1
    var ivTeam1 = view.team_1
    var tvPlayed1 = view.played_1
    var tvWon1 = view.won_1
    var tvLost1 = view.lost_1
    var tvDrawn1 = view.drawn_1
    var tvGF1 = view.gf_1
    var tvGC1 = view.gc_1
    var tvPL1 = view.plus_less_1
    var tvPTS1 = view.pts_1

    var ivFlag2 = view.iv_flag_2
    var tvName2 = view.tv_name_2
    var tvPoints2 = view.tv_points_2
    var ivTeam2 = view.team_2
    var tvPlayed2 = view.played_2
    var tvWon2 = view.won_2
    var tvLost2 = view.lost_2
    var tvDrawn2 = view.drawn_2
    var tvGF2 = view.gf_2
    var tvGC2 = view.gc_2
    var tvPL2 = view.plus_less_2
    var tvPTS2 = view.pts_2

    var ivFlag3 = view.iv_flag_3
    var tvName3 = view.tv_name_3
    var tvPoints3 = view.tv_points_3
    var ivTeam3 = view.team_3
    var tvPlayed3 = view.played_3
    var tvWon3 = view.won_3
    var tvLost3 = view.lost_3
    var tvDrawn3 = view.drawn_3
    var tvGF3 = view.gf_3
    var tvGC3 = view.gc_3
    var tvPL3 = view.plus_less_3
    var tvPTS3 = view.pts_3

    var ivFlag4 = view.iv_flag_4
    var tvName4 = view.tv_name_4
    var tvPoints4 = view.tv_points_4
    var ivTeam4 = view.team_4
    var tvPlayed4 = view.played_4
    var tvWon4 = view.won_4
    var tvLost4 = view.lost_4
    var tvDrawn4 = view.drawn_4
    var tvGF4 = view.gf_4
    var tvGC4 = view.gc_4
    var tvPL4 = view.plus_less_4
    var tvPTS4 = view.pts_4

    var ivArrow = view.iv_arrow

    var vDivider = view.vDivider

    var cvContainer = view.cv_card
    var rlSecondContainer = view.rl_second_container

}