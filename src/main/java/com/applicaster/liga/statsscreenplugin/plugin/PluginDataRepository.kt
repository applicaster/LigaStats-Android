package com.applicaster.liga.statsscreenplugin.plugin

import com.applicaster.liga.statsscreenplugin.utils.Constants.DEFAULT_BACK_BUTTON_URL
import com.applicaster.liga.statsscreenplugin.utils.Constants.DEFAULT_LOGO_URL
import com.applicaster.liga.statsscreenplugin.utils.Constants.PARAM_BACK_BUTTON_URL
import com.applicaster.liga.statsscreenplugin.utils.Constants.PARAM_CALENDAR_ID
import com.applicaster.liga.statsscreenplugin.utils.Constants.PARAM_COMPETITION_ID
import com.applicaster.liga.statsscreenplugin.utils.Constants.PARAM_IMAGE_BASE_URL
import com.applicaster.liga.statsscreenplugin.utils.Constants.PARAM_LOGO_URL
import com.applicaster.liga.statsscreenplugin.utils.Constants.PARAM_NUMBER_OF_MATCHES
import com.applicaster.liga.statsscreenplugin.utils.Constants.PARAM_REFERER
import com.applicaster.liga.statsscreenplugin.utils.Constants.PARAM_SHOW_TEAM
import com.applicaster.liga.statsscreenplugin.utils.Constants.PARAM_TOKEN
import com.applicaster.util.PreferenceUtil

enum class PluginDataRepository : PluginRepository {
    INSTANCE;

    override fun getToken(): String {
        return PreferenceUtil.getInstance().getStringPref(PARAM_TOKEN, null)
    }

    override fun setToken(token: String) {
        PreferenceUtil.getInstance().setStringPref(PARAM_TOKEN, token)
    }

    override fun getReferer(): String {
        return PreferenceUtil.getInstance().getStringPref(PARAM_REFERER, null)
    }

    override fun setReferer(referer: String) {
        PreferenceUtil.getInstance().setStringPref(PARAM_REFERER, referer)
    }

    override fun getCompetitionId(): String {
        return PreferenceUtil.getInstance().getStringPref(PARAM_COMPETITION_ID, null)
    }

    override fun setCompetitionId(tournamentId: String) {
        PreferenceUtil.getInstance().setStringPref(PARAM_COMPETITION_ID, tournamentId)
    }

    override fun getCalendarId(): String {
        return PreferenceUtil.getInstance().getStringPref(PARAM_CALENDAR_ID, null)
    }

    override fun setCalendarId(calendarId: String) {
        PreferenceUtil.getInstance().setStringPref(PARAM_CALENDAR_ID, calendarId)
    }

    override fun getImageBaseUrl(): String {
        return PreferenceUtil.getInstance().getStringPref(PARAM_IMAGE_BASE_URL, null)
    }

    override fun setImageBaseUrl(imageBaseUrl: String) {
        PreferenceUtil.getInstance().setStringPref(PARAM_IMAGE_BASE_URL, imageBaseUrl)
    }

    override fun isShowTeam(): Boolean {
        return PreferenceUtil.getInstance().getBooleanPref(PARAM_SHOW_TEAM, false)
    }

    override fun setShowTeam(showTeam: Any?) {
        val showTeamBoolean = showTeam ?: "0"
        PreferenceUtil.getInstance().setBooleanPref(PARAM_SHOW_TEAM, (showTeamBoolean == "1" || showTeamBoolean == true))
    }

    override fun getNumberOfMatches(): String {
        return PreferenceUtil.getInstance().getStringPref(PARAM_NUMBER_OF_MATCHES, "3")
    }

    override fun setNumberOfMatches(numberOfMatches: String) {
        if (numberOfMatches.equals("null", true) || numberOfMatches.isEmpty()) {
            PreferenceUtil.getInstance().setStringPref(PARAM_NUMBER_OF_MATCHES, "3")
        } else {
            PreferenceUtil.getInstance().setStringPref(PARAM_NUMBER_OF_MATCHES, numberOfMatches)
        }
    }

    override fun getLogoUrl(): String {
        // todo: implement fallback
        return PreferenceUtil.getInstance().getStringPref(PARAM_LOGO_URL, DEFAULT_LOGO_URL)
    }

    override fun setLogoUrl(logoUrl: String) {
        PreferenceUtil.getInstance().setStringPref(PARAM_LOGO_URL, logoUrl)
    }

    override fun getBackButtonUrl(): String {
        return PreferenceUtil.getInstance().getStringPref(PARAM_BACK_BUTTON_URL, DEFAULT_BACK_BUTTON_URL)
    }

    override fun setBackButtonUrl(backButtonUrl: String) {
        PreferenceUtil.getInstance().setStringPref(PARAM_BACK_BUTTON_URL, backButtonUrl)
    }
}
