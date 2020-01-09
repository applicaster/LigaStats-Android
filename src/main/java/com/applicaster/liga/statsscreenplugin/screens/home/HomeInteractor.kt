package com.applicaster.liga.statsscreenplugin.screens.home

import com.applicaster.liga.statsscreenplugin.data.model.AllMatchesModel
import com.applicaster.liga.statsscreenplugin.data.model.GroupModel
import com.applicaster.liga.statsscreenplugin.plugin.PluginDataRepository
import com.applicaster.liga.statsscreenplugin.screens.base.Interactor
import com.applicaster.liga.statsscreenplugin.utils.ModelUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeInteractor : Interactor() {

    interface OnFinishedListener {
        fun onGetGroupsSuccess(groupCards: GroupModel.Group)
        fun onGetGroupsFail(error: String?)

        fun onAllMatchesFromDateSuccess(allMatchesFromDate: AllMatchesModel.AllMatches)
    }

    fun requestGroupCards(onFinishedListener: OnFinishedListener) {
        disposable = copaAmericaApiService.getGroupCards(PluginDataRepository.INSTANCE.getToken(), referer,
                "c", "json", calendarId, ModelUtils.getLocalization())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> onFinishedListener.onGetGroupsSuccess(result) },
                        { error -> onFinishedListener.onGetGroupsFail(error.message) }
                )
    }

    fun requestAllMatchesFromDate(onFinishedListener: OnFinishedListener) {
        disposable = copaAmericaApiService.getAllMatches(PluginDataRepository.INSTANCE.getToken(), referer,
                "c", "json", calendarId, ModelUtils.getLocalization())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> onFinishedListener.onAllMatchesFromDateSuccess(result) },
                        { error -> onFinishedListener.onGetGroupsFail(error.message) }
                )
    }

    fun onDestroy() {
        disposable?.dispose()
    }
}