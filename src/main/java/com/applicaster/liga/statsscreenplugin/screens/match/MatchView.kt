package com.applicaster.liga.statsscreenplugin.screens.match

import com.applicaster.liga.statsscreenplugin.data.model.MatchModel
import com.applicaster.liga.statsscreenplugin.screens.base.View

interface MatchView : View {
    fun getMatchSuccess(matches: MatchModel.Match)
    fun getMatchFailed(error: String?)
}