package com.footballapp.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.footballapp.R
import com.footballapp.ext.stringConnector
import com.footballapp.net.ResponseCall
import com.footballapp.ui.standings.adapters.StandingsRecyclerViewAdapter
import kotlinx.android.synthetic.main.standings_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class StandingsFragment : Fragment() {

    private val standingsViewModel by viewModel<StandingsViewModel>()
    private lateinit var standingsAdapter: StandingsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.standings_fragment, container, false)

        standingsAdapter = StandingsRecyclerViewAdapter(arrayListOf(), requireActivity())
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
                        standingsAdapter.updateTable(it.data.standings[0].table)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewStandingsFragment.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = standingsAdapter
        }
    }
}