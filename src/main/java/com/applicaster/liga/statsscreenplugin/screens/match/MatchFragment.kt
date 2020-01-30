package com.applicaster.liga.statsscreenplugin.screens.match

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.applicaster.liga.statsscreenplugin.R
import com.applicaster.liga.statsscreenplugin.data.model.MatchModel

/**
 * Test class for match
 */
class MatchFragment : Fragment(), MatchView {

    lateinit var matchesPresenter: MatchPresenter

    var textView: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView: View = inflater.inflate(R.layout.liga_fragment_matches, container, false)
        textView = fragmentView.findViewById(R.id.tv_poc)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matchesPresenter = MatchPresenter(this, MatchInteractor())
        matchesPresenter.getMatchDetails("889vbgboqb6p2isxmmqaelkh5")
    }

    override fun getMatchSuccess(matches: MatchModel.Match) {
        textView?.text = "we made it!"
    }

    override fun getMatchFailed(error: String?) {
        textView?.text = error
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

}