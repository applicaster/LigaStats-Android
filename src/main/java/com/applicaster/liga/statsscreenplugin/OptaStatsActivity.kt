package com.applicaster.liga.statsscreenplugin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.applicaster.liga.statsscreenplugin.plugin.PluginDataRepository
import com.applicaster.liga.statsscreenplugin.screens.allmatches.AllMatchesFragment
import com.applicaster.liga.statsscreenplugin.screens.career.PlayerCareerFragment
import com.applicaster.liga.statsscreenplugin.screens.matchdetails.MatchDetailsFragment
import com.applicaster.liga.statsscreenplugin.screens.team.TeamFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_copa_america_stats.*

class OptaStatsActivity : AppCompatActivity() {

    companion object {
        enum class Screen {
            ALL_MATCHES, MATCH_DETAILS, PLAYER_DETAILS, TEAM
        }

        const val SCREEN_EXTRA = "screen_extra"
        const val MATCH_ID = "match_id"
        const val PUSH = "push"
        const val TRUE = "true"
        const val FALSE = "false"
        const val PLAYER_ID = "player_id"
        const val TEAM_ID = "team_id"

        fun getCallingIntent(@NonNull context: Context, screen: Screen, data: Map<String, String>): Intent {
            val intent = Intent(context, OptaStatsActivity::class.java)
            when (screen) {
                Screen.ALL_MATCHES -> {
                    intent.putExtra(TEAM_ID, data[TEAM_ID])
                    intent.putExtra(SCREEN_EXTRA, Screen.ALL_MATCHES)
                }
                Screen.MATCH_DETAILS -> {
                    intent.putExtra(MATCH_ID, data[MATCH_ID])
                    intent.putExtra(PUSH, data[PUSH])
                    intent.putExtra(SCREEN_EXTRA, Screen.MATCH_DETAILS)
                }
                Screen.PLAYER_DETAILS -> {
                    intent.putExtra(PLAYER_ID, data[PLAYER_ID])
                    intent.putExtra(SCREEN_EXTRA, Screen.PLAYER_DETAILS)
                }
                Screen.TEAM -> {
                    intent.putExtra(TEAM_ID, data[TEAM_ID])
                    intent.putExtra(SCREEN_EXTRA, Screen.TEAM)
                }
            }
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_copa_america_stats)

        Picasso.get().load(PluginDataRepository.INSTANCE.getBackButtonUrl())
                .into(iv_back)
        Picasso.get().load(PluginDataRepository.INSTANCE.getLogoUrl())
                .into(iv_logo)

        iv_back.setOnClickListener {
            onBackPressed()
        }

        if (savedInstanceState == null) {
            when (intent.extras[SCREEN_EXTRA]) {
                Screen.ALL_MATCHES -> {
                    intent.extras[TEAM_ID]?.let {
                        addFragment(R.id.fragment_container, AllMatchesFragment
                                .newInstance(intent.extras[TEAM_ID].toString()), false)
                        return
                    }
                    
                    addFragment(R.id.fragment_container, AllMatchesFragment(), false)
                }
                Screen.MATCH_DETAILS -> {
                    addFragment(R.id.fragment_container, MatchDetailsFragment
                            .newInstance(intent.extras[MATCH_ID].toString(), intent.extras[PUSH] as String?), false)
                }
                Screen.PLAYER_DETAILS -> {
                    addFragment(R.id.fragment_container, PlayerCareerFragment
                            .newInstance(intent.extras[PLAYER_ID].toString()), false)
                }
                Screen.TEAM -> {
                    addFragment(R.id.fragment_container, TeamFragment
                            .newInstance(intent.extras[TEAM_ID].toString()), false)
                }
            }
        }
    }

    fun addFragment(containerViewId: Int, fragment: Fragment, isFirstFragment: Boolean) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(containerViewId, fragment)
        if (!isFirstFragment) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}