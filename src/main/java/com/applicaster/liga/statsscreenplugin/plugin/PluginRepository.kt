package com.applicaster.liga.statsscreenplugin.plugin

interface PluginRepository {
    fun getImageBaseUrl(): String

    fun setImageBaseUrl(imageBaseUrl: String)

    fun getToken(): String

    fun setToken(token: String)

    fun getReferer(): String

    fun setReferer(referer: String)

    fun getCompetitionId(): String

    fun setCompetitionId(tournamentId: String)

    fun getNumberOfMatches(): String

    fun setNumberOfMatches(numberOfMatches: String)

    fun getCalendarId(): String

    fun setCalendarId(calendarId: String)

    fun isShowTeam(): Boolean

    fun setShowTeam(showTeam: Any?)

    fun getLogoUrl(): String

    fun setLogoUrl(logoUrl: String)

    fun getBackButtonUrl(): String

    fun setBackButtonUrl(backButtonUrl: String)

    fun setAppId(appId: String?)

    fun getAppId(): String
}