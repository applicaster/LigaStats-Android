package com.applicaster.liga.statsscreenplugin.screens.team

import com.applicaster.liga.statsscreenplugin.data.model.PlayerSquadModel
import com.applicaster.liga.statsscreenplugin.data.model.TeamModel
import com.applicaster.liga.statsscreenplugin.screens.base.View

interface TeamView : View {
    fun getTeamStatsSuccess(team: TeamModel.Team)
    fun getTeamStatsFailed(error: String?)
    fun getTeamSquadSuccess(teamSquad: PlayerSquadModel.PlayerSquad)
    fun getTeamSquadFail(error: String?)
    fun getTrophiesSuccess(counter: Int)
    fun getTrophiesFailed(error: String?)
}