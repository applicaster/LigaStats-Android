package com.applicaster.liga.statsscreenplugin.screens.home

import com.applicaster.liga.statsscreenplugin.data.model.AllMatchesModel
import com.applicaster.liga.statsscreenplugin.data.model.GroupModel
import com.applicaster.liga.statsscreenplugin.screens.base.View

interface HomeView : View {
    fun getGroupsSuccess(groupCards: GroupModel.Group)
    fun getGroupsFail(error: String?)
    fun getAllMatchesFromDateSuccess(allMatchesFromDate: AllMatchesModel.AllMatches)
}