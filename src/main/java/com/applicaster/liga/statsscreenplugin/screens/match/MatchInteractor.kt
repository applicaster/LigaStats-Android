package com.applicaster.liga.statsscreenplugin.screens.match

import com.applicaster.liga.statsscreenplugin.data.model.MatchModel
import com.applicaster.liga.statsscreenplugin.plugin.PluginDataRepository
import com.applicaster.liga.statsscreenplugin.screens.base.Interactor
import com.applicaster.liga.statsscreenplugin.utils.ModelUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MatchInteractor : Interactor() {
    companion object {
        private val TAG: String = MatchInteractor::class.java.simpleName
    }

    interface OnFinishedListener {
        fun onResultSuccess(matches: MatchModel.Match)
        fun onResultFail(error: String?)
    }

    fun requestMatches(onFinishedListener: OnFinishedListener, matchId: String) {
        disposable = copaAmericaApiService.getMatches(PluginDataRepository.INSTANCE.getToken(), referer,
                "c", "json", matchId, "yes", ModelUtils.getLocalization())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> onFinishedListener.onResultSuccess(result) },
                        { error -> onFinishedListener.onResultFail(error.message) }
                )
    }

    fun onDestroy() {
        disposable?.dispose()
    }
}