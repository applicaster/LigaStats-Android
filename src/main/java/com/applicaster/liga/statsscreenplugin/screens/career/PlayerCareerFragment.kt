package com.applicaster.liga.statsscreenplugin.screens.career

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.applicaster.liga.statsscreenplugin.OptaStatsActivity.Companion.PLAYER_ID
import com.applicaster.liga.statsscreenplugin.R
import com.applicaster.liga.statsscreenplugin.data.model.PlayerCareerModel
import com.applicaster.liga.statsscreenplugin.plugin.PluginDataRepository
import com.applicaster.liga.statsscreenplugin.utils.ModelUtils
import com.applicaster.liga.statsscreenplugin.utils.UrlPrefix
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_player.*

class PlayerCareerFragment : Fragment(), PlayerCareerView {

    private var playerCareerPresenter: PlayerCareerPresenter =
            PlayerCareerPresenter(this, PlayerCareerInteractor())

    companion object {
        fun newInstance(playerId: String): Fragment {
            val args = Bundle()
            args.putString(PLAYER_ID, playerId)
            val fragment = PlayerCareerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val playerId = arguments?.getString(PLAYER_ID)
        playerId?.let {
            playerCareerPresenter.getPlayerCareer(playerId)
        }
    }

    override fun getPlayerCareerSuccess(playerCareer: PlayerCareerModel.PlayerCareer) {
        playerCareer.person[0].id.let { Picasso.get().load(ModelUtils.getImageUrl(UrlPrefix.person, it)).into(iv_player_img) }
        getPlayerFlagId(playerCareer)?.let { Picasso.get().load(ModelUtils.getImageUrl(UrlPrefix.flag, it)).placeholder(R.drawable.unknow_flag).into(iv_player_flag) }
        getPlayerTeamId(playerCareer)?.let { Picasso.get().load(ModelUtils.getImageUrl(UrlPrefix.shield, it)).into(iv_player_shield) }
        // tv_player_name.text = playerCareer.person[0].matchName
        tv_player_full_name.text = playerCareer.person[0].matchName // String.format("%s %s", playerCareer.person[0].firstName, playerCareer.person[0].lastName)
        tv_player_position.text = ModelUtils.getPlayerPosition(context, playerCareer.person[0].position)
        tv_player_pob.text = String.format("%s, %s", playerCareer.person[0].placeOfBirth, playerCareer.person[0].countryOfBirth)
        tv_player_dob.text = ModelUtils.getStandardFormatFromDate(playerCareer.person[0].dateOfBirth)

        tv_profesional_team.text = getProfessionalTeam(playerCareer)
        if (playerCareer.person[0].height != null)
            tv_player_height.text = String.format("%s cm", playerCareer.person[0].height)

        if (playerCareer.person[0].weight != null)
            tv_player_weight.text = String.format("%s kg", playerCareer.person[0].weight)

        // heavy loops that need to be executed in a separate thread
        synchronized(this) {
            tv_player_number.text = getNumber(playerCareer)
        }
        synchronized(this) {
            tv_americas_cup.text = getTournamentsQuantity(playerCareer)
        }
        synchronized(this) {
            tv_player_goals.text = getGoals(playerCareer)
        }
    }


    private fun getNumber(playerCareer: PlayerCareerModel.PlayerCareer): String? {
        for (membership in playerCareer.person[0].membership) {
            for (stat in membership.stat) {
                if (stat.competitionId == PluginDataRepository.INSTANCE.getCompetitionId()
                        && stat.tournamentCalendarId == PluginDataRepository.INSTANCE.getCalendarId()) {
                    return stat.shirtNumber.toString()
                }
            }
        }
        return "-"
    }

    private fun getPlayerFlagId(playerCareer: PlayerCareerModel.PlayerCareer): String? {
        for (membership in playerCareer.person[0].membership) {
            if (membership.contestantType == "national" && membership.active == "yes") {
                return membership.contestantId
            }
        }
        return null
    }

    private fun getPlayerTeamId(playerCareer: PlayerCareerModel.PlayerCareer): String? {
        for (membership in playerCareer.person[0].membership) {
            if (membership.contestantType == "national" && membership.active == "yes") {
                return membership.contestantId
            }
        }
        return null
    }

    private fun getProfessionalTeam(playerCareer: PlayerCareerModel.PlayerCareer): String {
        for (membership in playerCareer.person[0].membership) {
            if (membership.contestantType == "club") {
                if (membership.active == "yes") {
                    return membership.contestantName
                }
            }
        }
        return "-"
    }

    private fun getTournamentsQuantity(playerCareer: PlayerCareerModel.PlayerCareer): String {
        var counter = 0
        for (membership in playerCareer.person[0].membership) {
            for (stat in membership.stat) {
                if (stat.competitionId == PluginDataRepository.INSTANCE.getCompetitionId()) {
                    counter++
                }
            }
        }
        return counter.toString()
    }

    private fun getGoals(playerCareer: PlayerCareerModel.PlayerCareer): String {
        for (membership in playerCareer.person[0].membership) {
            for (stat in membership.stat) {
                if (stat.competitionId == PluginDataRepository.INSTANCE.getCompetitionId()
                        && stat.tournamentCalendarId == PluginDataRepository.INSTANCE.getCalendarId()) {
                    return stat.goals.toString()
                }
            }
        }
        return "0"
    }

    override fun getPlayerCareerFail(error: String?) {
        Log.e(this.javaClass.simpleName, error)
    }

    override fun showProgress() {// todo: implement this method
    }

    override fun hideProgress() {// todo: implement this method
    }

    override fun onDestroy() {
        super.onDestroy()
        playerCareerPresenter.onDestroy()
    }
}