package com.applicaster.liga.statsscreenplugin.screens.home

import com.applicaster.liga.statsscreenplugin.data.model.AllMatchesModel
import com.applicaster.liga.statsscreenplugin.data.model.GroupModel

class HomePresenter(private var homeView: HomeView?,
                    private val homeInteractor: HomeInteractor):
        HomeInteractor.OnFinishedListener {

    fun getGroups() {
        homeView?.showProgress()
        homeInteractor.requestGroupCards(this)
    }

    fun getAllMatchesFromDate() {
        homeView?.showProgress()
        homeInteractor.requestAllMatchesFromDate(this)
    }

    fun onDestroy() {
        homeView = null
        homeInteractor.onDestroy()
    }

    override fun onGetGroupsSuccess(groupCards: GroupModel.Group) {
        homeView?.hideProgress()
        homeView?.getGroupsSuccess(groupCards)
    }

    override fun onAllMatchesFromDateSuccess(allMatchesFromDate: AllMatchesModel.AllMatches) {
        homeView?.hideProgress()
        homeView?.getAllMatchesFromDateSuccess(allMatchesFromDate)
    }

    override fun onGetGroupsFail(error: String?) {
        homeView?.hideProgress()
        homeView?.getGroupsFail(error)
    }

}