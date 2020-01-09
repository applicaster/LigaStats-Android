package com.applicaster.liga.statsscreenplugin.screens.career

import com.applicaster.liga.statsscreenplugin.data.model.PlayerCareerModel

class PlayerCareerPresenter(private var playerCareerView: PlayerCareerView?,
                            private val playerCareerInteractor: PlayerCareerInteractor) :
        PlayerCareerInteractor.OnFinishedListener {

    fun getPlayerCareer(personId: String) {
        playerCareerView?.showProgress()
        playerCareerInteractor.requestPlayerCareer(this, personId)
    }

    fun onDestroy() {
        playerCareerView = null
        playerCareerInteractor.onDestroy()
    }

    override fun onResultSuccess(playerCareer: PlayerCareerModel.PlayerCareer) {
        playerCareerView?.hideProgress()
        playerCareerView?.getPlayerCareerSuccess(playerCareer)
    }

    override fun onResultFail(error: String?) {
        playerCareerView?.hideProgress()
        playerCareerView?.getPlayerCareerFail(error)
    }
}