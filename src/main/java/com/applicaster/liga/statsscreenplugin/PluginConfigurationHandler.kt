package com.applicaster.liga.statsscreenplugin

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK

class PluginConfigurationHandler {
    fun handlePluginScheme(context: Context, data: Map<String, String>): Boolean {
        var verified = false

        if ("general".equals(data.get("type"))) {
            if ("stats_open_screen".equals(data.get("action"))) {
                val screenId = data.get("screen_id")
                if (screenId != null) {
                    val intent = getActivityByScreenId(screenId, context, data)
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                    verified = true
                }
            }
        }

        return verified
    }

    private fun getActivityByScreenId(screenId: String, context: Context, data: Map<String, String>): Intent {
        return when (screenId) {
            "match_details_screen" -> {
                OptaStatsActivity
                        .getCallingIntent(context, OptaStatsActivity.Companion.Screen.MATCH_DETAILS, data)
            }
            "player_screen" -> {
                OptaStatsActivity
                        .getCallingIntent(context, OptaStatsActivity.Companion.Screen.PLAYER_DETAILS, data)
            }
            "team_screen" -> {
                OptaStatsActivity
                        .getCallingIntent(context, OptaStatsActivity.Companion.Screen.TEAM, data)
            }
            "all_matches_screen" -> {
                OptaStatsActivity
                        .getCallingIntent(context, OptaStatsActivity.Companion.Screen.ALL_MATCHES, data)
            }
            else -> OptaStatsActivity
                    .getCallingIntent(context, OptaStatsActivity.Companion.Screen.ALL_MATCHES, data)
        }
    }
}