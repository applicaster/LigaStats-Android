package com.applicaster.liga.statsscreenplugin.utils

import android.content.Context
import android.util.Log
import com.applicaster.app.CustomApplication
import com.applicaster.liga.statsscreenplugin.R
import com.applicaster.liga.statsscreenplugin.data.model.AllMatchesModel
import com.applicaster.liga.statsscreenplugin.data.model.GroupModel
import com.applicaster.liga.statsscreenplugin.data.model.MatchModel
import com.applicaster.liga.statsscreenplugin.data.model.TeamModel
import com.applicaster.liga.statsscreenplugin.plugin.PluginDataRepository
import com.applicaster.liga.statsscreenplugin.utils.Constants.UTC_DATE_FORMAT
import com.applicaster.lesscodeutils.date.DateUtils.Companion.getCurrentDate
import com.applicaster.liga.statsscreenplugin.utils.UrlType.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ModelUtils {
    companion object {
        // english
        var countryCodesEn: MutableMap<String, String> =
                hashMapOf("Argentina" to "arg",
                        "Bolivia" to "bol",
                        "Brazil" to "bra",
                        "Chile" to "chl",
                        "Colombia" to "col",
                        "Ecuador" to "ecu",
                        "Japan" to "jpn",
                        "Peru" to "per",
                        "Paraguay" to "pry",
                        "Qatar" to "qat",
                        "Uruguay" to "uru",
                        "Venezuela" to "ven")
        // spanish
        var countryCodesEs: MutableMap<String, String> =
                hashMapOf("Argentina" to "arg",
                        "Bolivia" to "bol",
                        "Brasil" to "bra",
                        "Chile" to "chl",
                        "Colombia" to "col",
                        "Ecuador" to "ecu",
                        "Jap�n" to "jpn",
                        "Per�" to "per",
                        "Paraguay" to "pry",
                        "Qatar" to "qat",
                        "Uruguay" to "uru",
                        "Venezuela" to "ven")
        // portuguese
        var countryCodesPt: MutableMap<String, String> =
                hashMapOf("Argentina" to "arg",
                        "Bol�via" to "bol",
                        "Brasil" to "bra",
                        "Chile" to "chl",
                        "Col�mbia" to "col",
                        "Equador" to "ecu",
                        "Jap�o" to "jpn",
                        "Peru" to "per",
                        "Paraguay" to "pry",
                        "Qatar" to "qat",
                        "Uruguai" to "uru",
                        "Venezuela" to "ven")

        fun getDivisionWithTypeTotal(groups: GroupModel.Group): List<GroupModel.Division> {
            var divisions: ArrayList<GroupModel.Division> = ArrayList()
            groups.stage[0].division.firstOrNull { it.type == "total" }?.let { divisions.add(it) }
            return divisions
        }

        fun getRakesWithTypeTotal(groups: GroupModel.Group): List<GroupModel.Ranking> {
            var result: List<GroupModel.Ranking> = mutableListOf()
            groups.stage[0].division.firstOrNull { it.type == "total" }?.apply { result = ranking }
            return result
        }

        fun getNextThreeMatches(matches: AllMatchesModel.AllMatches, date: String):
                List<AllMatchesModel.Match> {
            var matchesFromDate: MutableList<AllMatchesModel.Match> = ArrayList()

            for (matchDate in matches.matchDate) {
                for (match in matchDate.match) {
                    if (match.date >= date) {
                        matchesFromDate.add(match)

                        if (matchesFromDate.count() == PluginDataRepository.INSTANCE.getNumberOfMatches().toInt()) {
                            return matchesFromDate
                        }
                    }
                }
            }

            return matchesFromDate
        }

        fun getLastThreeMatches(matches: AllMatchesModel.AllMatches):
                List<AllMatchesModel.Match> {
            var matchesFromDate: MutableList<AllMatchesModel.Match> = ArrayList()

            for (matchDate in matches.matchDate.asReversed()) {
                for (match in matchDate.match.asReversed()) {
                    matchesFromDate.add(match)
                    if (matchesFromDate.count() == 3) {
                        return matchesFromDate
                    }
                }
            }

            return matchesFromDate
        }

        fun getReadableDateFromString(dateString: String): String {
            try {
                val formatter = SimpleDateFormat("yyyy-MM-dd'Z'HH:mm:ss'Z'", Locale.getDefault())
                formatter.timeZone = TimeZone.getTimeZone("UTC")
                val value = formatter.parse(dateString)

                val dateFormatter = SimpleDateFormat("EEEE, dd MMMM - HH:mm'H'", Locale.getDefault())
                dateFormatter.timeZone = TimeZone.getDefault()

                return dateFormatter.format(value)
            } catch (e: ParseException) {
                Log.e(ModelUtils::class.java.simpleName, "Make sure the date format is \"yyyy-MM-dd'Z'HH:mm:ss'Z'\" Actual :: $dateString")
            }

            return ""
        }

        fun getStandardFormatFromDate(dateString: String): String {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val value = formatter.parse(dateString)

            val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateFormatter.timeZone = TimeZone.getDefault()

            return dateFormatter.format(value)
        }

        fun getImageUrl(type: UrlType, id: String): String {
            return when (type) {
                Flag -> PluginDataRepository.INSTANCE.getFlagImageBaseUrl()
                Person -> PluginDataRepository.INSTANCE.getPersonImageBaseUrl()
                Shield -> PluginDataRepository.INSTANCE.getShieldImageBaseUrl()
                Shirt -> PluginDataRepository.INSTANCE.getShirtImageBaseUrl()
                Partidos -> PluginDataRepository.INSTANCE.getPartidosImageBaseUrl()
            }.plus(id)
        }

        fun getCodeFromCountryName(contestantName: String): String? {
            return when {
                CustomApplication.getDefaultDeviceLocale().language == "en" -> countryCodesEn[contestantName]
                CustomApplication.getDefaultDeviceLocale().language == "es" -> countryCodesEs[contestantName]
                CustomApplication.getDefaultDeviceLocale().language == "pt" -> countryCodesPt[contestantName]
                // default language
                else -> countryCodesEn[contestantName]
            }
        }

        fun getLocalization(): String {
            return when {
                CustomApplication.getDefaultDeviceLocale().language == "en" -> "en-en"
                CustomApplication.getDefaultDeviceLocale().language == "es" -> "es-es"
                CustomApplication.getDefaultDeviceLocale().language == "pt" -> "pt-br"
                // default language
                else -> "en-en"
            }
        }

        fun getTeamStatistic(stats: List<TeamModel.Stat>, statName: String): String? {
            for (stat in stats) {
                if (stat.name == statName) {
                    return stat.value
                }
            }

            return null
        }

        fun getMatchStatistic(stats: List<MatchModel.Stat>, statName: String): String? {
            for (stat in stats) {
                if (stat.type == statName) {
                    return stat.value
                }
            }

            return "0"
        }

        fun getLineUpFromContestant(liveData: MatchModel.LiveData?, homeContestant: Boolean):
                MatchModel.LineUp? {
            if (liveData?.lineUp != null) {
                val contestantId = if (homeContestant) liveData.lineUp!![0].contestantId
                else liveData.lineUp!![1].contestantId

                for (lineUp in liveData.lineUp!!) {
                    if (lineUp.contestantId == contestantId) {
                        return lineUp
                    }
                }
            }

            return null
        }

        fun getShirtNumberOf(formationPlace: Int, lineUp: MatchModel.LineUp): String {
            for (player in lineUp.player) {
                if (getMatchStatistic(player.stat, "formationPlace") == formationPlace.toString()) {
                    return player.shirtNumber.toString()
                }
            }

            return ""
        }

        fun addOneDay(date: String): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'Z'")
            val c = Calendar.getInstance()
            c.time = sdf.parse(date)
            c.add(Calendar.DATE, 1)  // number of days to add
            return sdf.format(c.time)  // dt is now the new date
        }

        fun getFieldPlayers(players: List<MatchModel.Player>): List<MatchModel.Player>? {
            val fieldPlayers = ArrayList<MatchModel.Player>()
            for (player in players) {
                if (getMatchStatistic(player.stat, "formationPlace") != "0") {
                    fieldPlayers.add(player)
                }
            }

            return fieldPlayers
        }

        fun getReservePlayers(players: List<MatchModel.Player>): List<MatchModel.Player>? {
            val reservePlayers = ArrayList<MatchModel.Player>()
            for (player in players) {
                if (getMatchStatistic(player.stat, "formationPlace") == "0") {
                    reservePlayers
                            .add(player)
                }
            }

            return reservePlayers
        }

        fun formatFormation(formation: String?): String? {
            var formationString = ""
            var index = 0
            formation?.let {
                for (number in 1..it.length) {
                    formationString +=
                            if (index == it.length - 1) {
                                it.substring(index, index + 1)
                            } else {
                                String.format("%s-", it.substring(index, index + 1))
                            }
                    index += 1
                }
            }

            return formationString
        }

        fun getPositionOfTodayMatch(allMatches: List<MatchModel.Match>): Int {
            var counter = 0
            for (match in allMatches) {
                // todo: apply logic when there is no match going to the next date til the end date
                if (match.matchInfo?.date == getCurrentDate(UTC_DATE_FORMAT)) {
                    return counter
                }
                counter += 1
            }

            return 0
        }

        fun getPlayerPosition(context: Context?, position: String?): CharSequence? {
            return when (position) {
                Constants.GOALKEEPER -> context?.getText(R.string.goalkeeper)
                Constants.MIDFIELDER -> context?.getText(R.string.midfielder)
                Constants.DEFENDER -> context?.getText(R.string.defender)
                Constants.DEFENSIVE_MIDFIELDER -> context?.getText(R.string.defensive_midfielder)
                Constants.ATTACKING_MIDFIELDER -> context?.getText(R.string.attacking_midfielder)
                Constants.ATTACKER -> context?.getText(R.string.attacker)
                Constants.SUBSTITUTE -> context?.getText(R.string.substitute)
                Constants.STRIKER -> context?.getText(R.string.striker)
                Constants.UNKNOWN -> context?.getText(R.string.unknown)
                else -> position
            }
        }

        fun getGameState(context: Context?, matchStatus: String): CharSequence? {
            return when (matchStatus) {
                Constants.FIXTURE -> ""
                Constants.PLAYING -> context?.getText(R.string.playing)
                Constants.PLAYED -> context?.getText(R.string.played)
                else -> matchStatus
            }
        }
    }
}

enum class UrlType {
    Person, Flag, Shield, Shirt, Partidos
}
