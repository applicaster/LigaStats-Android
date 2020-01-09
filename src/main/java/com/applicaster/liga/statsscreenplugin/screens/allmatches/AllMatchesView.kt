package com.applicaster.liga.statsscreenplugin.screens.allmatches

import com.applicaster.liga.statsscreenplugin.data.model.MatchModel
import com.applicaster.liga.statsscreenplugin.screens.base.View

interface AllMatchesView : View {
    fun getAllMatchesSuccess(allMatches: List<MatchModel.Match>)
    fun getAllMatchesFail(error: String?)
}
