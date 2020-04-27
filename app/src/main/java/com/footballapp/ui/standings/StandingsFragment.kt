package com.footballapp.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.footballapp.R
import com.footballapp.ext.stringConnector
import com.footballapp.net.ResponseCall
import kotlinx.android.synthetic.main.standings_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class StandingsFragment : Fragment() {

    private val standingsViewModel by viewModel<StandingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.standings_fragment, container, false)
        standingsViewModel.loadStandings()

        standingsViewModel.standingsStatus.observe(
            viewLifecycleOwner, Observer {
                when (it) {
                    is ResponseCall.Success -> {
                        competitionStandingsFragment.stringConnector(
                            getString(R.string.competition_title_text),
                            it.data.competition.name,
                            null
                        )
                        seasonStandingsFragment.stringConnector(
                            getString(R.string.season_title_text),
                            it.data.season.startDate,
                            it.data.season.endDate
                        )
                    }

                    is ResponseCall.Error -> {
                    }

                    is ResponseCall.Exception -> {
                    }
                }
            }
        )

        return inflate
    }
}