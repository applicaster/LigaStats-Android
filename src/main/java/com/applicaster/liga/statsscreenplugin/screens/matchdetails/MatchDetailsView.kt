package com.applicaster.liga.statsscreenplugin.screens.matchdetails

import com.applicaster.liga.statsscreenplugin.data.model.MatchModel
import com.applicaster.liga.statsscreenplugin.screens.base.View

interface MatchDetailsView : View {
    fun getMatchDetailsSuccess(matchDetails: MatchModel.Match)
    fun getMatchDetailsFail(error: String?)
}