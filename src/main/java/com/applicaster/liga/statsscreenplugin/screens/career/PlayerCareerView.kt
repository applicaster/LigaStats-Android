package com.applicaster.liga.statsscreenplugin.screens.career

import com.applicaster.liga.statsscreenplugin.data.model.PlayerCareerModel
import com.applicaster.liga.statsscreenplugin.screens.base.View

interface PlayerCareerView : View {
    fun getPlayerCareerSuccess(playerCareer: PlayerCareerModel.PlayerCareer)
    fun getPlayerCareerFail(error: String?)
}